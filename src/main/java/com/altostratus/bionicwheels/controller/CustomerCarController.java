package com.altostratus.bionicwheels.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.Customer;
import com.altostratus.bionicwheels.service.CustomerCarService;
import com.altostratus.core.util.CarBrands;

@Controller("customerCarController")
public class CustomerCarController {

	@Autowired
	CustomerCarService customerCarService;

	@Autowired
	CarBrands carBrands;

	Logger logger = LoggerFactory.getLogger(CustomerCarController.class);

	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ModelAndView customerIndex(HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView("customer.index");
		mnv.addObject("customer", new Customer());
		mnv.addObject("customers", customerCarService.getAllCustomers());
		return mnv;
	}

	@RequestMapping(value = "/customer/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editCustomer(HttpServletRequest request,
			@PathVariable("id") Long id) {
		ModelAndView mnv = new ModelAndView("customer.index");
		mnv.addObject("customer", customerCarService.getCustomerById(id));
		mnv.addObject("customers", customerCarService.getAllCustomers());
		return mnv;
	}

	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public ModelAndView removeCustomer(HttpServletRequest request,
			@ModelAttribute("customer") Customer customer, BindingResult result) {
		customer = customerCarService.saveCustomer(customer);
		return new ModelAndView("redirect:/customer");
	}

	@RequestMapping(value = "/customers", method = RequestMethod.GET)
	public ModelAndView customersIndex(HttpServletRequest request) {
		return new ModelAndView("customers.index", "customers",
				customerCarService.getAllCars());
	}
}
