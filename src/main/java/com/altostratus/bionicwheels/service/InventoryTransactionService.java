package com.altostratus.bionicwheels.service;

import java.util.Date;
import java.util.List;

import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.bionicwheels.model.InventoryTransactionProduct;
import com.altostratus.core.model.User;

public interface InventoryTransactionService {
	public InventoryTransaction saveInventoryTransaction(
			InventoryTransaction inventoryTransaction);

	public InventoryTransaction getInventoryTransaction(Long id);

	public List<InventoryTransaction> getAllInventoryTransactions();

	public void removeInventoryTransaction(Long id);

	public List<InventoryTransaction> getAllInventoryTransactionsWithinDate(
			Date startDate, Date endDate);
	
	public List<InventoryTransactionProduct> getAllInventoryTransactionProductsWithinDateByCategory(Date startDate, Date endDate, Long id);

//	public List<InventoryTransaction> getAllInventoryTransactionsWithinDateByUserAndTransactionType(
//			User user, Date startDate, Date endDate);

	public List<InventoryTransaction> getAllInventoryTransactionsWithinDateByUser(
			User user, Date startDate, Date endDate);
	
	public List<InventoryTransactionProduct> getAllInventoryTransactionProductsWithinDateByCategoryInventoryIn(Date startDate, Date endDate, Long id);
	
	public List<InventoryTransactionProduct> getAllInventoryTransactionProductsWithinDateByCategoryInventoryOut(Date startDate, Date endDate, Long id);
}
