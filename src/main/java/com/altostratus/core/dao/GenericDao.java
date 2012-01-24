package com.altostratus.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.search.jpa.FullTextEntityManager;

public interface GenericDao <T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> getAll();

    /**
     * Gets all records without duplicates.
     * <p>Note that if you use this method, it is imperative that your model
     * classes correctly implement the hashcode/equals methods</p>
     * @return List of populated objects
     */
    List<T> getAllDistinct();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the persisted object
     */
    T save(T object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);
    
    /**
     * Find a list of records by using a named query
     * @param queryName query name of the named query
     * @return a list of the records found
     */
    List<T> findByNamedQuery(String queryName);
    
    /**
     * Find a list of records by using a named query
     * @param queryName query name of the named query
     * @param queryParams a map of the query names and the values
     * @return a list of the records found
     */
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

    /**
     * Find a list of records by using a named query
     * @param queryName query name of the named query
     * @param queryParams a map of the query names and the values
     * @param offset  
     * @param length
     * @return a list of the records found
     */
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams, int offset, int length);

    /**
     * Find by HQL 
     * 
     * @param queryString
     * @return
     */
	List<T> findByQuery(String queryString);
			
    /**
     * Find by HQL 
     * 
     * @param queryString
     * @param parameters
     * @return
     */
	List<T> findByQuery(String queryString, Map<String, Object> parameters);

    /**
     * Find by HQL
     * 
     * @param queryName
     * @param queryParams
     * @param offset
     * @param length
     * @return
     */
    List<T> findByQuery(String queryName, Map<String, Object> queryParams, int offset, int length);
 
    /**
     * 
     * @return
     */
    FullTextEntityManager getFullTextEntityManager();
    
    /**
     * 
     * @return
     */
	EntityManager getEntityManager();
	
	/**
	 * 
	 * @param searchString
	 * @param sortField
	 * @param sortOrder
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<T> search(String searchString, String sortField, String sortOrder, Integer offset, Integer limit);
	
	/**
	 * 
	 * @param queryString
	 * @return
	 */
	Object findObjectByQuery(String queryString);
	
}