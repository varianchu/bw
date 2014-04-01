package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.ServiceTransaction;
import com.altostratus.bionicwheels.model.ServiceTransactionItem;
import com.altostratus.bionicwheels.repository.jpa.ServiceTransactionItemRepository;

@Service("serviceTransactionItemService")
@Transactional
public class ServiceTransactionItemServiceImpl implements
		ServiceTransactionItemService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	ServiceTransactionItemRepository serviceTransactionItemRepository;

	@Override
	public ServiceTransactionItem getServiceTransactionItem(Long id) {
		// TODO Auto-generated method stub
		return serviceTransactionItemRepository.findOne(id);
	}

	@Override
	public void removeServiceTransactionItem(Long id) {
		// TODO Auto-generated method stub
		serviceTransactionItemRepository.delete(id);
	}

	@Override
	public ServiceTransactionItem saveServiceTransactionItem(
			ServiceTransactionItem serviceTransactionItem) {
		// TODO Auto-generated method stub
		return serviceTransactionItemRepository.save(serviceTransactionItem);
	}
	
	@Override
	public List<ServiceTransactionItem> getServiceTransactionItemsByServiceTransaction(ServiceTransaction serviceTransaction) {
		// TODO Auto-generated method stub
		return serviceTransactionItemRepository.findByServiceTransaction(serviceTransaction);
	}

}
