package com.altostratus.bionicwheels.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.DummyProduct;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.ProductService;

@Component
public class ProductValidator implements Validator {

	@Autowired
	ProductService productService;

	@Autowired
	BrandService brandService;

	private Logger logger = LoggerFactory.getLogger(ProductValidator.class);

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

		if (dummyProduct.getSrp() < dummyProduct.getCost()) {
			logger.info("SRP IS LESS THAN COST");
			errors.rejectValue("srp", "field.required",
					"SRP should be greater than Cost.");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qty",
				"field.required", "Please specify current quantity");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "srp",
				"field.required", "Please specify SRP");

		Boolean checker = null;
		if (dummyProduct.getId() == null) {
			checker = true;
		} else {
			checker = false;
		}

		if (productService.getProductsByCode(dummyProduct.getCode()).size() > 0 && checker) {
			errors.rejectValue("code", null, "Product code must be unique");
		}

		logger.info("SRP: " + dummyProduct.getSrp().toString());

		// for (Product p : productService.getAllProducts()) {
		// if (dummyProduct.getId() != p.getId()) {
		// if (dummyProduct.getCode().equals(p.getCode())) {
		// errors.rejectValue("code", "field.required",
		// "Product code should be unique.");
		// break;
		// }
		// }
		// }
	}
}
