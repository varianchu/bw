package com.altostratus.bionicwheels.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.ServiceTransaction;

@Component
public class ServiceTransactionValidator implements Validator{
	
	@Override
	public boolean supports(Class serviceTransaction) {
		// TODO Auto-generated method stub
		return ServiceTransaction.class.equals(serviceTransaction);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "field.required", "Please specify the customer");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "car", "field.required", "Please specify the car");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mechanicTireMan", "field.required", "Please specify the mechanic/tire man");
	}
}
