package com.altostratus.bionicwheels.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.ServiceTransactionItem;

@Component
public class ServiceTransactionItemValidator implements Validator{
	
	@Override
	public boolean supports(Class serviceTransactionItem) {
		// TODO Auto-generated method stub
		return ServiceTransactionItem.class.equals(serviceTransactionItem);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ServiceTransactionItem sti = (ServiceTransactionItem) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceMadePart", "field.required", "Please specify the service");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "servicePrice", "field.required", "Please specify the price");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceProfit", "field.required", "Please specify the profit");
		
		try{
			Double price = Double.parseDouble(sti.getServicePrice().toString());
			Double profit = Double.parseDouble(sti.getServiceProfit().toString());
			System.out.println("service price: " + price + " service profit: " + profit);
		}
		catch (Exception e) {
			// TODO: handle exception
			errors.rejectValue("servicePrice", null, "Invalid Price");
			errors.rejectValue("serviceProfit", null, "Invalid Profit");
		}
	}
}
