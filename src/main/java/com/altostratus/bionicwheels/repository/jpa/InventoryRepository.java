package com.altostratus.bionicwheels.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.Inventory;
import com.altostratus.bionicwheels.model.Product;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	public List<Inventory> findByProduct(Product product);

}
