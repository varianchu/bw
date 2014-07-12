package com.altostratus.bionicwheels.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.ServiceRendered;
import com.altostratus.bionicwheels.service.ServiceRenderedService;

@Component
public class ServiceRenderedValidator implements Validator {
	
	@Autowired
	ServiceRenderedService serviceRenderedService;
	
	@Override
	public boolean supports(Class serviceRendered) {
		// TODO Auto-generated method stub
		return ServiceRendered.class.equals(serviceRendered);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ServiceRendered serviceRendered = (ServiceRendered) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceMadePart", "field.required", "Service Rendered is required.");
		
		Boolean checker = null;
		if (serviceRendered.getId() == null) {
			checker = true;
		} else {
			checker = false;
		}

		if (serviceRenderedService.getServiceRenderedName(serviceRendered.getServiceMadePart()).size() > 0 && checker) {
			errors.rejectValue("serviceMadePart", null, "Service Made Part must be unique");
		}
	}
}
