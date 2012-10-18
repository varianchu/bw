package com.altostratus.bionicwheels.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.service.BrandService;

@Component
public class BrandValidator implements Validator {

	@Autowired
	BrandService brandService;

	@Override
	public boolean supports(Class brand) {
		// TODO Auto-generated method stub
		return Brand.class.equals(brand);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Brand brand = (Brand) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brandName",
				"field.required", "Please specify the brand name.");
		if (brand.getSupplierId() == null) {
			errors.rejectValue("supplier", "field.required",
					"Please select a supplier.");
		}

		for (Brand b : brandService.getAllBrands()) {
			if (brand.getId() != b.getId()) {
				if (b.getBrandName().equalsIgnoreCase(brand.getBrandName())) {
					errors.rejectValue("brandName", "field.required",
							"Brand is already in the database.");
					break;
				}
			}
		}
	}

}
