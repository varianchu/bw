package com.altostratus.bionicwheels.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.DummyProduct;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.service.ProductService;

@Component
public class ProductValidator implements Validator {

	@Autowired
	ProductService productService;

	@Override
	public boolean supports(Class dummyProduct) {
		// TODO Auto-generated method stub
		return DummyProduct.class.equals(dummyProduct);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		DummyProduct dummyProduct = (DummyProduct) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName",
				"field.required", "Product name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code",
				"field.required", "Product code is required.");

		for (Product p : productService.getAllProducts()) {
			if (dummyProduct.getId() != p.getId()) {
				if (dummyProduct.getCode().equalsIgnoreCase(p.getCode())) {
					errors.rejectValue("code", "field.required",
							"Product code should be unique.");
					break;
				}
				// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "",
				// errorCode,
				// errorArgs)
			}
		}
	}
}
