package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.InventoryTransactionProduct;

public interface InventoryTransactionProductService {
	public InventoryTransactionProduct saveInventoryTransactionProduct(
			InventoryTransactionProduct inventoryTransactionProduct);

	public InventoryTransactionProduct getInventoryTransactionProduct(Long id);

	public List<InventoryTransactionProduct> getAllInventoryTransactionProducts();

	public void removeInventoryTransactionProduct(Long id);

	public List<InventoryTransactionProduct> getAllProductsWithTransactionInventoryId(
			Long id);

}
