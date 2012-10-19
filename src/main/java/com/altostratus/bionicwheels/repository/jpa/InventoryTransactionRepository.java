package com.altostratus.bionicwheels.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.core.model.User;

public interface InventoryTransactionRepository extends
		JpaRepository<InventoryTransaction, Long> {

	List<InventoryTransaction> findByDateCreatedBetween(Date startDate,
			Date endDate);

	List<InventoryTransaction> findByUserAndDateCreatedBetween(User user,
			Date startDate, Date endDate);
}
