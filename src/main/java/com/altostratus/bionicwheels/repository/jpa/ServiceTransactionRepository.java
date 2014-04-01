package com.altostratus.bionicwheels.repository.jpa;

import java.util.Date;	
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.ServiceTransaction;
import com.altostratus.core.model.User;

public interface ServiceTransactionRepository extends JpaRepository<ServiceTransaction, Long> {
	
	List<ServiceTransaction> findByServiceDateBetween(Date startDate, Date endDate);
	List<ServiceTransaction> findByUserAndServiceDateBetween(User user, Date startDate, Date endDate);
	List<ServiceTransaction> findByMechanicTireMan(String mechanicTireMan);
	List<ServiceTransaction> findByMechanicTireManAndServiceDateBetween(String mechanicTireMan, Date startDate, Date endDate);
}
