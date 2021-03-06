package com.altostratus.bionicwheels.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.bionicwheels.model.InventoryTransactionProduct;
import com.altostratus.bionicwheels.repository.jpa.CategoryRepository;
import com.altostratus.bionicwheels.repository.jpa.InventoryTransactionRepository;
import com.altostratus.core.model.User;

@Service("inventoryTransactionService")
@Transactional
public class InventoryTransactionServiceImpl implements
		InventoryTransactionService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	InventoryTransactionRepository inventoryTransactionRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

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

//	@Override
//	public List<InventoryTransaction> getAllInventoryTransactionsWithinDateByUserAndTransactionType(
//			User user, Date startDate, Date endDate) {
//		// TODO Auto-generated method stub
//		List<InventoryTransaction> inventoryTransactions =  inventoryTransactionRepository.findByUserAndDateCreatedBetween(user, startDate, endDate);
//		
//		System.out.println("transactions size: " + inventoryTransactions.size());
//		
////		for(InventoryTransaction inventoryTransaction : inventoryTransactions){
////			if(inventoryTransaction.getTransactionType().equalsIgnoreCase(InventoryTransaction.TRANSACTION_TYPE.INVENTORY_IN.toString())){
////				inventoryTransactions.remove(inventoryTransaction);
////			}
////		}
//		
//		return inventoryTransactions;
//		
//	}
	
	@Override
	public List<InventoryTransaction> getAllInventoryTransactionsWithinDateByUser(
			User user, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return inventoryTransactionRepository.findByUserAndDateCreatedBetween(user, startDate, endDate);
		
	}
	
	@Override
	public List<InventoryTransactionProduct> getAllInventoryTransactionProductsWithinDateByCategoryInventoryIn(Date startDate, Date endDate, Long id){
		
		Category category = categoryRepository.findOne(id);
		
		List<InventoryTransaction> inventoryTransactions = inventoryTransactionRepository.findByTransactionTypeAndDateCreatedBetween(InventoryTransaction.TRANSACTION_TYPE.INVENTORY_IN.toString(),startDate, endDate);
		
		List<InventoryTransactionProduct> inventoryTransactionProducts = new ArrayList<InventoryTransactionProduct>();
		
		List<InventoryTransactionProduct> inventoryTransactionProductsByCategory = new ArrayList<InventoryTransactionProduct>();
		
		for(InventoryTransaction inventoryTransaction : inventoryTransactions){
			inventoryTransactionProducts.addAll(inventoryTransaction.getInventoryTransactionProducts());
		}
		
		for(InventoryTransactionProduct inventoryTransactionProduct : inventoryTransactionProducts){
			if(inventoryTransactionProduct.getCategory().equals(category)){
				inventoryTransactionProductsByCategory.add(inventoryTransactionProduct);
			}
		}
		
		return inventoryTransactionProductsByCategory;
	}
	
	@Override
	public List<InventoryTransactionProduct> getAllInventoryTransactionProductsWithinDateByCategoryInventoryOut(Date startDate, Date endDate, Long id){
		
		Category category = categoryRepository.findOne(id);
		
		List<InventoryTransaction> inventoryTransactions = inventoryTransactionRepository.findByTransactionTypeAndDateCreatedBetween(InventoryTransaction.TRANSACTION_TYPE.INVENTORY_OUT.toString(),startDate, endDate);
		
		List<InventoryTransactionProduct> inventoryTransactionProducts = new ArrayList<InventoryTransactionProduct>();
		
		List<InventoryTransactionProduct> inventoryTransactionProductsByCategory = new ArrayList<InventoryTransactionProduct>();
		
		for(InventoryTransaction inventoryTransaction : inventoryTransactions){
			inventoryTransactionProducts.addAll(inventoryTransaction.getInventoryTransactionProducts());
		}
		
		for(InventoryTransactionProduct inventoryTransactionProduct : inventoryTransactionProducts){
			if(inventoryTransactionProduct.getCategory().equals(category)){
				inventoryTransactionProductsByCategory.add(inventoryTransactionProduct);
			}
		}
		
		return inventoryTransactionProductsByCategory;
	}
	
	@Override
	public List<InventoryTransactionProduct> getAllInventoryTransactionProductsWithinDateByCategory(Date startDate, Date endDate, Long id){
		
		Category category = categoryRepository.findOne(id);
		
		List<InventoryTransaction> inventoryTransactions = inventoryTransactionRepository.findByDateCreatedBetween(startDate, endDate);
		List<InventoryTransactionProduct> inventoryTransactionProducts = new ArrayList<InventoryTransactionProduct>();
		List<InventoryTransactionProduct> inventoryTransactionProductsByCategory = new ArrayList<InventoryTransactionProduct>();
		
		for(InventoryTransaction inventoryTransaction : inventoryTransactions){
			inventoryTransactionProducts.addAll(inventoryTransaction.getInventoryTransactionProducts());
		}
		
		for(InventoryTransactionProduct inventoryTransactionProduct : inventoryTransactionProducts){
			if(inventoryTransactionProduct.getCategory().equals(category)){
				inventoryTransactionProductsByCategory.add(inventoryTransactionProduct);
			}
		}
		
		return inventoryTransactionProductsByCategory;
	}
}
