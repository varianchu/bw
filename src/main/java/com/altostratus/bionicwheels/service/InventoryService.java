package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.Inventory;
import com.altostratus.bionicwheels.model.Product;

public interface InventoryService {
	public Inventory saveInventory(Inventory inventory);

	public void removeInventory(Long id);

	public Inventory getInventory(Long id);

	public List<Inventory> getInventoryByProduct(Product product);

}
