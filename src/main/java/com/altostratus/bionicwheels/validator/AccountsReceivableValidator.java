package com.altostratus.bionicwheels.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.AccountsReceivable;
import com.altostratus.bionicwheels.model.DummyProduct;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.service.AccountsReceivableService;

@Component
public class AccountsReceivableValidator implements Validator {

	@Autowired
	AccountsReceivableService accountsReceivableService;

	@Override
	public boolean supports(Class accountsReceivable) {
		// TODO Auto-generated method stub
		return AccountsReceivable.class.equals(accountsReceivable);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
		AccountsReceivable accountsReceivable = (AccountsReceivable) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "receiptNumber", "field.required", "Receipt number is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "field.required", "Amount is required.");
//		ValidationUtils.rejectIfEmpty(errors, "customer", "field.required", "Customer is required.");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customer", "field.required", "Customer is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expectedDateReceivableValue", "field.required", "Terms is required.");
		
//		for (AccountsReceivable ar : accountsReceivableService.getAllAccountsReceivable()) {	
//			if (accountsReceivable.getId() != ar.getId()) {
//				if (accountsReceivable.getReceiptNumber().equals(ar.getReceiptNumber())) {
//					errors.rejectValue("receiptNumber", "field.required",
//					"Receipt Number/Reference Number should be unique.");
//					break;
//				}
//			}
//		}
		
		if (accountsReceivableService.getAccountsReceivableByReceiptNumber(accountsReceivable.getReceiptNumber())!=null && accountsReceivable.isNew()) {
			errors.rejectValue("receiptNumber", "field.required", "Receipt Number/Reference Number should be unique.");
		}
	}
}
