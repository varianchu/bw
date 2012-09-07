package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Inventory;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.repository.jpa.InventoryRepository;

@Service("inventoryService")
@Transactional
public class InventoryServiceImpl implements InventoryService {
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	InventoryRepository inventoryRepository;

	@Override
	public Inventory getInventory(Long id) {
		// TODO Auto-generated method stub
		return inventoryRepository.findOne(id);
	}

	@Override
	public List<Inventory> getInventoryByProduct(Product product) {
		// TODO Auto-generated method stub
		return inventoryRepository.findByProduct(product);
	}

	@Override
	public void removeInventory(Long id) {
		// TODO Auto-generated method stub
		inventoryRepository.delete(id);
	}

	@Override
	public Inventory saveInventory(Inventory inventory) {
		// TODO Auto-generated method stub
		return inventoryRepository.save(inventory);
	}

}
