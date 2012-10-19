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
public class ProductMagWheelsValidator implements Validator {
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
		// DummyProduct dummyProduct = (DummyProduct) target;
		DummyProduct dummyProduct = (DummyProduct) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "size",
				"field.required", "Please specify the size of the Mag Wheels.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pcd",
				"field.required", "Please specify the PCD of the Mag Wheels.");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName",
				"field.required", "Product name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code",
				"field.required", "Product code is required.");
		if (dummyProduct.getSupplierId() == null) {
			errors.rejectValue("supplierId", "field.required",
					"Please select a supplier.");
		}
		if (dummyProduct.getCategoryId() == null) {
			errors.rejectValue("categoryId", "field.required",
					"Please select a category.");
		}
		if (dummyProduct.getBrandName() == null
				|| dummyProduct.getBrandName().equalsIgnoreCase("0")) {
			errors.rejectValue("brandName", "field.required",
					"Please select a Band.");
		}

		if (dummyProduct.getCost() > dummyProduct.getSrp()) {
			errors.rejectValue("srp", "field.required",
					"SRP should be greater than or equal to Cost of Good");
		}

		for (Product p : productService.getAllProducts()) {
			if (dummyProduct.getId() != p.getId()) {
				if (dummyProduct.getCode().equalsIgnoreCase(p.getCode())) {
					errors.rejectValue("code", "field.required",
							"Product code should be unique.");
					break;
				}
				if (dummyProduct.getProductName().equalsIgnoreCase(
						p.getProductName())) {
					errors.rejectValue("productName", "field.required",
							"Product Name is already in the database.");
					break;
				}
				// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "",
				// errorCode,
				// errorArgs)
			}
		}

	}

}
