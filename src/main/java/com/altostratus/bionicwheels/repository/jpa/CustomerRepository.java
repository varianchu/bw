package com.altostratus.bionicwheels.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByCustomerName(String customerName);

	List<Customer> findByCityLocation(String location);

	List<Customer> findByCars_CarMake(String carMake);

}
