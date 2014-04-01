package com.altostratus.bionicwheels.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.ServiceTransaction;
import com.altostratus.bionicwheels.repository.jpa.ServiceTransactionRepository;
import com.altostratus.core.model.User;

@Service("serviceTransactionService")
@Transactional
public class ServiceTransactionServiceImpl implements ServiceTransactionService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	ServiceTransactionRepository serviceTransactionRepository;

	@Override
	public List<ServiceTransaction> getAllServiceTransactions() {
		// TODO Auto-generated method stub
		return serviceTransactionRepository.findAll();
	}

	@Override
	public ServiceTransaction getServiceTransaction(Long id) {
		// TODO Auto-generated method stub
		return serviceTransactionRepository.findOne(id);
	}

	@Override
	public ServiceTransaction saveServiceTransaction(
			ServiceTransaction serviceTransaction) {
		// TODO Auto-generated method stub
		return serviceTransactionRepository.save(serviceTransaction);
	}
	
	@Override
	public List<ServiceTransaction> getServiceTransactionByMechanicTireManAndDateBetween(String mechanicTireMan, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return serviceTransactionRepository.findByMechanicTireManAndServiceDateBetween(mechanicTireMan, startDate, endDate);
	}
	
	@Override
	public List<ServiceTransaction> getServiceTransactionsByDateBetween(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return serviceTransactionRepository.findByServiceDateBetween(startDate, endDate);
	}
	
	@Override
	public List<ServiceTransaction> getServiceTransactionsByUserAndDateBetween(User user, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return serviceTransactionRepository.findByUserAndServiceDateBetween(user, startDate, endDate);
	}
}
