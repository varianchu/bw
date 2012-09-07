package com.altostratus.bionicwheels.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.service.CategoryService;

@Component
public class CategoryValidator implements Validator {

	@Autowired
	CategoryService cs;

	@Override
	public boolean supports(Class category) {
		return Category.class.equals(category);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Category category = (Category) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code",
				"field.required", "Please specify the Category Code");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoryName",
				"field.required", "Please specify the Category Name");

		for (Category c : cs.getAllCategories()) {
			if (category.getId() != c.getId()) {
				if (c.getCode().equalsIgnoreCase(category.getCode())) {
					errors.rejectValue("code", "field.required",
							"Category code should be unique.");
					break;
				}
			}
		}
	}

}
