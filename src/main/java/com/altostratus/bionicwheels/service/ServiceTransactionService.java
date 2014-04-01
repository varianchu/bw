package com.altostratus.bionicwheels.service;

import java.util.Date;
import java.util.List;

import com.altostratus.bionicwheels.model.ServiceTransaction;
import com.altostratus.core.model.User;

public interface ServiceTransactionService {

	public ServiceTransaction saveServiceTransaction(ServiceTransaction serviceTransaction);
	
	public List<ServiceTransaction> getAllServiceTransactions();
	
	public ServiceTransaction getServiceTransaction(Long id);
	
	public List<ServiceTransaction> getServiceTransactionsByDateBetween(Date startDate, Date endDate);
	
	public List<ServiceTransaction> getServiceTransactionsByUserAndDateBetween(User user, Date startDate, Date endDate);
	
	public List<ServiceTransaction> getServiceTransactionByMechanicTireManAndDateBetween(String mechanicTireMan, Date startDate, Date endDate);
}
