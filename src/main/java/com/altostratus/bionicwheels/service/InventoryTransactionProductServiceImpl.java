package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.InventoryTransactionProduct;
import com.altostratus.bionicwheels.repository.jpa.InventoryTransactionProductRepository;

@Service("inventoryTransactionProductService")
@Transactional
public class InventoryTransactionProductServiceImpl implements
		InventoryTransactionProductService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	InventoryTransactionProductRepository inventoryTransactionProductRepository;

	@Override
	@Transactional
	public InventoryTransactionProduct saveInventoryTransactionProduct(
			InventoryTransactionProduct inventoryTransactionProduct) {
		// TODO Auto-generated method stub
		return inventoryTransactionProductRepository
				.save(inventoryTransactionProduct);
	}

	@Override
	public List<InventoryTransactionProduct> getAllInventoryTransactionProducts() {
		// TODO Auto-generated method stub
		return inventoryTransactionProductRepository.findAll();
	}

	@Override
	public InventoryTransactionProduct getInventoryTransactionProduct(Long id) {
		// TODO Auto-generated method stub
		return inventoryTransactionProductRepository.findOne(id);
	}

	@Override
	public List<InventoryTransactionProduct> getAllProductsWithTransactionInventoryId(
			Long id) {
		// TODO Auto-generated method stub
		return inventoryTransactionProductRepository
				.findByInventoryTransaction_Id(id);
	}

	@Override
	public void removeInventoryTransactionProduct(Long id) {
		// TODO Auto-generated method stub
		inventoryTransactionProductRepository.delete(id);
	}
}
