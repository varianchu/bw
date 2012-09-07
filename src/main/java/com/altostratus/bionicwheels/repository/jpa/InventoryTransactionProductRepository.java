package com.altostratus.bionicwheels.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.InventoryTransactionProduct;

public interface InventoryTransactionProductRepository extends
		JpaRepository<InventoryTransactionProduct, Long> {

	// need to test this if the same product occur multiple times.
	List<InventoryTransactionProduct> findByInventoryTransaction_Id(Long id);
}
