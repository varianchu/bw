package com.altostratus.bionicwheels.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Car;
import com.altostratus.bionicwheels.model.Customer;
import com.altostratus.bionicwheels.repository.jpa.CarRepository;
import com.altostratus.bionicwheels.repository.jpa.CustomerRepository;

@Service("customerCarService")
@Transactional
public class CustomerCarServiceImpl implements CustomerCarService {
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CarRepository carRepository;

	@Override
	public Customer getCustomerById(Long id) {
		// TODO Auto-generated method stub
		return customerRepository.findOne(id);
	}

	@Override
	public List<Customer> getCustomerByName(String name) {
		// TODO Auto-generated method stub
		return customerRepository.findByCustomerName(name);
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAllCustomersByLocation(String location) {
		// TODO Auto-generated method stub
		return customerRepository.findByCityLocation(location);
	}

	@Override
	public List<Customer> getAllCustomersWithCarMake(String carMake) {
		// to prevent multiple occurrences of the same customer with cars that
		// have the same car make.
		Set<Customer> customersSet = new HashSet<Customer>();
		List<Customer> customersFromRepo = customerRepository
				.findByCars_CarMake(carMake);
		for (Customer c : customersFromRepo) {
			customersSet.add(c);
		}
		List<Customer> customers = new ArrayList<Customer>(customersSet);
		return customers;
	}

	@Override
	public Car getCar(Long id) {
		// TODO Auto-generated method stub
		return carRepository.findOne(id);
	}

	@Override
	public Car saveCar(Car car) {
		// TODO Auto-generated method stub
		return carRepository.save(car);
	}

	@Override
	public void removeCar(Long id) {
		// TODO Auto-generated method stub
		carRepository.delete(id);
	}

	@Override
	public List<Car> getAllCars() {
		// TODO Auto-generated method stub
		return carRepository.findAll();
	}

	@Override
	public List<Car> getAllCarModelsWithCarMake(String carMake) {
		// TODO Auto-generated method stub
		return carRepository.findByCarMake(carMake);
	}

	@Override
	public List<Car> getAllCarsWithNextChangeOil(Date startDate, Date endDate) {
		return carRepository.findByNextChangeOilBetween(startDate, endDate);
	}

}
