package com.altostratus.bionicwheels.repository.jpa;

import java.util.List;	

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.ServiceTransaction;
import com.altostratus.bionicwheels.model.ServiceTransactionItem;

public interface ServiceTransactionItemRepository extends JpaRepository<ServiceTransactionItem, Long>{
	List<ServiceTransactionItem> findByServiceTransaction(ServiceTransaction serviceTransaction);
}
