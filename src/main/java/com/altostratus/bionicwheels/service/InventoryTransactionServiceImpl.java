package com.altostratus.bionicwheels.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.bionicwheels.repository.jpa.InventoryTransactionRepository;

@Service("inventoryTransactionService")
@Transactional
public class InventoryTransactionServiceImpl implements
		InventoryTransactionService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	InventoryTransactionRepository inventoryTransactionRepository;

	@Override
	@Transactional
	public InventoryTransaction saveInventoryTransaction(
			InventoryTransaction inventoryTransaction) {
		// TODO Auto-generated method stub
		return inventoryTransactionRepository.save(inventoryTransaction);
	}

	@Override
	public List<InventoryTransaction> getAllInventoryTransactions() {
		// TODO Auto-generated method stub
		return inventoryTransactionRepository.findAll();
	}

	@Override
	public InventoryTransaction getInventoryTransaction(Long id) {
		// TODO Auto-generated method stub
		return inventoryTransactionRepository.findOne(id);
	}

	@Override
	public void removeInventoryTransaction(Long id) {
		// TODO Auto-generated method stub
		inventoryTransactionRepository.delete(id);
	}

	@Override
	public List<InventoryTransaction> getAllInventoryTransactionsWithinDate(
			Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return inventoryTransactionRepository.findByDateCreatedBetween(
				startDate, endDate);
	}
}
