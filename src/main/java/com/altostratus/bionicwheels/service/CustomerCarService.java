package com.altostratus.bionicwheels.service;

import java.util.Date;
import java.util.List;

import com.altostratus.bionicwheels.model.Car;
import com.altostratus.bionicwheels.model.Customer;

public interface CustomerCarService {

	public Customer getCustomerById(Long id);

	public List<Customer> getCustomerByName(String name);

	public List<Customer> getAllCustomers();

	public Customer saveCustomer(Customer customer);

	public List<Customer> getAllCustomersByLocation(String location);

	public List<Customer> getAllCustomersWithCarMake(String carMake);

	public Car getCar(Long id);

	public Car saveCar(Car car);

	public void removeCar(Long id);

	public List<Car> getAllCars();

	public List<Car> getAllCarModelsWithCarMake(String carMake);

	public List<Car> getAllCarsWithNextChangeOil(Date startDate, Date endDate);

}
