package com.altostratus.bionicwheels.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.Customer;
import com.altostratus.bionicwheels.service.CustomerCarService;

@Component
public class CustomerValidator implements Validator {

	@Autowired
	CustomerCarService customerCarService;

	@Override
	public boolean supports(Class customer) {
		// TODO Auto-generated method stub
		return Customer.class.equals(customer);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "field.required", "Customer's First Name and Last Name are required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNumber", "field.required", "Contact Number is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cityLocation", "field.required", "City Location is required.");
	}
}
