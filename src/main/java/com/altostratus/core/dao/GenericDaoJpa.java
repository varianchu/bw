package com.altostratus.core.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.altostratus.core.util.QueryUtil;

/**
 *
 * @author AppFuse, Vinni Canos
 *
 * @param <T>
 * @param <PK>
 */
public class GenericDaoJpa<T, PK extends Serializable> implements GenericDao<T, PK> {
	/**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String PERSISTENCE_UNIT_NAME = "persistenceUnit";

    /**
     * Entity manager, injected by Spring using @PersistenceContext annotation on setEntityManager()
     */
    @PersistenceContext(unitName=PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    private final Class<T> persistentClass;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing or using dependency injection.
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoJpa(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing or using dependency injection.
     * @param persistentClass the class type you'd like to persist
     * @param entityManager the configured EntityManager for JPA implementation.
     */
    public GenericDaoJpa(final Class<T> persistentClass, final EntityManager entityManager) {
        this.persistentClass = persistentClass;
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return this.entityManager.createQuery(
                "select obj from " + this.persistentClass.getName() + " obj")
                .getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
    	return new ArrayList(new LinkedHashSet(getAll()));
//        return new ArrayList(new LinkedHashSet(getAll()));
    }

    /**
     * {@inheritDoc}
     */
    public T get(final PK id) {
        final T entity = this.entityManager.find(this.persistentClass, id);

        if (entity == null) {
            final String msg = "Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...";
            log.warn(msg);
            throw new EntityNotFoundException(msg);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(final PK id) {
        final T entity = this.entityManager.find(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    public T save(final T object) {
    	final T o = this.entityManager.merge(object);
    	entityManager.flush();
    	return o;
    }

    /**
     * {@inheritDoc}
     */
    public void remove(final PK id) {
        this.entityManager.remove(this.get(id));
    	entityManager.flush();
    }

	public List<T> findByNamedQuery(final String queryName) {
		return findByNamedQuery(queryName, QueryUtil.emptyParams());
	}

	public List<T> findByNamedQuery(final String queryName,
			final Map<String, Object> queryParams) {
		return findByNamedQuery(queryName, queryParams, 0, -1);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(final String queryName,
			final Map<String, Object> queryParams, final int offset, final int length) {
		final Query query = entityManager.createNamedQuery(queryName);
		for (final Entry<String, Object> entry: queryParams.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
        if (offset != 0) {
			query.setFirstResult(offset);
		}
        if (length != -1) {
			query.setMaxResults(length);
		}

		return query.getResultList();
	}

	public List<T> findByQuery(final String queryString) {
		return findByQuery(queryString, QueryUtil.emptyParams());
	}

	public List<T> findByQuery(final String queryString,
			final Map<String, Object> queryParams) {
		return findByQuery(queryString,
				queryParams, 0, -1);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByQuery(final String queryString,
			final Map<String, Object> queryParams, final int offset, final int length) {
		final Query query = entityManager.createQuery(queryString);
		for (final Entry<String, Object> entry: queryParams.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
        if (offset != 0) {
			query.setFirstResult(offset);
		}
        if (length != -1) {
			query.setMaxResults(length);
		}

        return query.getResultList();
	}

	public Query getNamedQuery(final String queryName) {
		return entityManager.createNamedQuery(queryName);
	}

	public FullTextEntityManager getFullTextEntityManager() {
        return Search.getFullTextEntityManager(entityManager);
    }

	@SuppressWarnings("unchecked")
	public List<T> search(String searchString, String sortField, String sortOrder, Integer offset, Integer limit)
	{
		FullTextEntityManager fullTextEntityManager = this.getFullTextEntityManager();

		/*
		 * TODO: next block reindexes all class related records. dito muna until updateSearchIndex function under MaintenanceController.class is done
		 */
		Session session = (Session)fullTextEntityManager.getDelegate();
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
		fullTextSession.setFlushMode(FlushMode.MANUAL);
		fullTextSession.setCacheMode(CacheMode.IGNORE);
//		transaction = fullTextSession.beginTransaction();
		//Scrollable results will avoid loading too many objects in memory
		ScrollableResults results = fullTextSession.createCriteria(this.persistentClass)
		    .setFetchSize(50)
		    .scroll( ScrollMode.FORWARD_ONLY );
		int index = 0;
		while( results.next() ) {
		    index++;
		    fullTextSession.index( results.get(0) ); //index each element
		    if (index % 50 == 0) {
		        fullTextSession.flushToIndexes(); //apply changes to indexes
		        fullTextSession.clear(); //free memory since the queue is processed
		    }
		}
//		transaction.commit();

//		try {
//			fullTextEntityManager.createIndexer(this.persistentClass).startAndWait();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		SearchFactory searchFactory = fullTextEntityManager.getSearchFactory();
		QueryBuilder queryBuilder = searchFactory.buildQueryBuilder().forEntity(this.persistentClass).get();

		org.apache.lucene.search.Query query = queryBuilder
		  .keyword()
		  .wildcard()
		  .onField(sortField)
		  .ignoreFieldBridge()
		  .matching("*"+searchString+"*")
		  .createQuery();

		FullTextQuery fullTextQuery  = fullTextEntityManager.createFullTextQuery(query, this.persistentClass);

		Boolean isReversed = false;
		if(sortOrder.equals("desc")) {
			isReversed = true;
		}

		fullTextQuery.setSort(new Sort(new SortField(sortField, SortField.STRING, isReversed)));
	    fullTextQuery.setFirstResult(offset);
	    fullTextQuery.setMaxResults(limit);

	    return fullTextQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	public Object findObjectByQuery(String queryString){
		return entityManager.createQuery(queryString).getResultList();
	}

}

