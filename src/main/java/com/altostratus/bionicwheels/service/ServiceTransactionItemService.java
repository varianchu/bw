package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.ServiceTransaction;
import com.altostratus.bionicwheels.model.ServiceTransactionItem;

public interface ServiceTransactionItemService {

	public ServiceTransactionItem getServiceTransactionItem(Long id);
	
	public ServiceTransactionItem saveServiceTransactionItem(ServiceTransactionItem serviceTransactionItem);
	
	public void removeServiceTransactionItem(Long id);
	
	public List<ServiceTransactionItem> getServiceTransactionItemsByServiceTransaction(ServiceTransaction serviceTransaction);
}
