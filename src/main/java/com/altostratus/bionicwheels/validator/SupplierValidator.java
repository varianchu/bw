package com.altostratus.bionicwheels.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.service.SupplierService;

@Component
public class SupplierValidator implements Validator {
	@Autowired
	SupplierService supplierService;

	@Override
	public boolean supports(Class<?> supplier) {
		// TODO Auto-generated method stub
		return Supplier.class.equals(supplier);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Supplier supplier = (Supplier) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supplierName",
				"field.required", "Supplier Name is required");
		for (Supplier s : supplierService.getAllSuppliers()) {
			if (supplier.getId() != s.getId()) {
				if (s.getSupplierName().equalsIgnoreCase(
						supplier.getSupplierName())) {
					errors.rejectValue("supplierName", "field.required",
							"Supplier Name is already in the database.");
					break;
				}
			}
		}
	}
}
