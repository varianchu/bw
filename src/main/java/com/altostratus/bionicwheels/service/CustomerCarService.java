package com.altostratus.bionicwheels.service;

import com.altostratus.bionicwheels.model.Car;
import com.altostratus.bionicwheels.model.Customer;

public interface CustomerCarService {

	public Customer getCustomerById(Long id);

	public Customer getCustomerByName(String name);

	public List<Customer> getAllCustomers();

	public Customer saveCustomer(Customer customer);

	public List<Customer> getAllCustomersWithNextChangeOilByDateRange(
			Date startDate, Date endDate);

	public List<Customer> getAllCustomersByLocation(String location);

	public List<Customer> getllCustomersWithCarMake(String carMake);

	public Car getCar(Long id);

	public Car saveCar(Car car);

	public void removeCar(Long id);

	public List<Car> getAllCars();

	public List<Car> getAllCarModelsWithCarMake(String carMake);

}
