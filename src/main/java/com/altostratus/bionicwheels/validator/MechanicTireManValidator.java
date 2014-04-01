package com.altostratus.bionicwheels.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.altostratus.bionicwheels.model.MechanicTireMan;
import com.altostratus.bionicwheels.service.MechanicTireManService;

@Component
public class MechanicTireManValidator implements Validator{

	@Autowired
	MechanicTireManService mechanicTireManService;
	
	@Override
	public boolean supports(Class mechanicTireMan) {
		// TODO Auto-generated method stub
		return MechanicTireMan.class.equals(mechanicTireMan);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		MechanicTireMan mechanicTireMan = (MechanicTireMan) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mechanicTireManName", "field.required", "Please specify the staff name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobDescription", "field.required", "Please specify the job description.");
		
		for(MechanicTireMan mt : mechanicTireManService.getAllMechanicTireMan()){
			if(mt.getId() != mechanicTireMan.getId()){
				if(mt.getMechanicTireManName().equalsIgnoreCase(mechanicTireMan.getMechanicTireManName())){
					errors.rejectValue("mechanicTireManName", "field.required", "Mechanic/Tire man is already in the database.");
					break;
				}
			}
		}
	}
}
