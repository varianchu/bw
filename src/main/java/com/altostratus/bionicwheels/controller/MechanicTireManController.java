package com.altostratus.bionicwheels.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.MechanicTireMan;
import com.altostratus.bionicwheels.service.MechanicTireManService;
import com.altostratus.bionicwheels.validator.MechanicTireManValidator;

@Controller
@RequestMapping(value = "/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SYSTEM_MANAGER', 'CASHIER')")
public class MechanicTireManController {

	@Autowired
	MechanicTireManService mechanicTireManService;

	@Autowired
	MechanicTireManValidator mechanicTireManValidator;

	private Logger logger = LoggerFactory
			.getLogger(MechanicTireManController.class);

	@RequestMapping(value = "/mechanic-tireman/{message}", method = RequestMethod.GET)
	public ModelAndView mechanicTireManIndex(HttpServletRequest request, @PathVariable("message") String message, Principal principal) {
		logger.info("entering mechanic tireman index by " + principal.getName());
		String successMessage = request.getHeader("successMessage");
		logger.info("RESPONSE HEADER: " + successMessage);
		ModelAndView mnv = new ModelAndView("admin.mechanictireman.index");
		mnv.addObject("mechanicTireMan", new MechanicTireMan());
		mnv.addObject("mechanicTireManAll", mechanicTireManService.getAllMechanicTireMan());
		mnv.addObject("jobDescription", MechanicTireMan.JOB_DESCRIPTION.values());
		if(message.equalsIgnoreCase("SUCCESS")){
			mnv.addObject("SUCCESS_MESSAGE", "Successfully saved Mechanic/Tire Man");
			logger.info("successfully saved worker by " + principal.getName());
		}
		return mnv;
	}

	@RequestMapping(value = "/mechanic-tireman/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editMechanicTireMan(@PathVariable("id") Long mechanicTireManId, HttpServletRequest request, Principal principal) {
		logger.info("Editing mechanic tireman id: " + mechanicTireManId.toString() + " viewed by " + principal.getName());
		ModelAndView mnv = new ModelAndView("admin.mechanictireman.index");
		mnv.addObject("mechanicTireMan", mechanicTireManService.getMechanicTireMan(mechanicTireManId));
		mnv.addObject("mechanicTireManAll", mechanicTireManService.getAllMechanicTireMan());
		mnv.addObject("jobDescription", MechanicTireMan.JOB_DESCRIPTION.values());
		return mnv;
	}

	@RequestMapping(value = "/mechanic-tireman/remove/{id}", method = RequestMethod.GET)
	public ModelAndView removeMechanicTireMan(@PathVariable("id") Long mechanicTireManId, HttpServletRequest request, Principal principal) {
		logger.info("Removing mechanic tireman id: " + mechanicTireManId.toString());
		
		ModelAndView mnv = new ModelAndView("admin.mechanictireman.index");
		try {
			mechanicTireManService.removeMechanicTireMan(mechanicTireManId);
			mnv.addObject("mechanicTireMan", new MechanicTireMan());
			mnv.addObject("mechanicTireManAll", mechanicTireManService.getAllMechanicTireMan());
			mnv.addObject("SUCCESS_MESSAGE", "Successfully deleted tireman/mechanic");
			mnv.addObject("jobDescription", MechanicTireMan.JOB_DESCRIPTION.values());
			return mnv;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			logger.info("remove unsuccessful (Mechanic Tireman).");
			mnv.addObject("mechanicTireMan", new MechanicTireMan());
			mnv.addObject("mechanicTireManAll", mechanicTireManService.getAllMechanicTireMan());
			mnv.addObject("jobDescription", MechanicTireMan.JOB_DESCRIPTION.values());
			mnv.addObject("ERROR_MESSAGE", "Delete Unsuccessful");
			logger.info("successfully removed worker by " + principal.getName());
			return mnv;
		}
	}

	@RequestMapping(value = "/mechanic-tireman", method = RequestMethod.POST)
	public ModelAndView saveMechanicTireMan(HttpServletRequest request, @ModelAttribute("mechanicTireMan") MechanicTireMan mechanicTireMan, BindingResult result, HttpServletResponse response, Principal principal) {

		logger.info("Saving Mechanic/Tire Man by " + principal.getName());

		mechanicTireManValidator.validate(mechanicTireMan, result);

		if (result.hasErrors()) {
			logger.info(result.toString());
			logger.info("Failed to save Mechanic/Tire Man");

			ModelAndView mnv = new ModelAndView("admin.mechanictireman.index");
			mnv.addObject("mechanicTireMan", new MechanicTireMan());
			mnv.addObject("mechanicTireManAll", mechanicTireManService.getAllMechanicTireMan());
			mnv.addObject("jobDescription", MechanicTireMan.JOB_DESCRIPTION.values());
			mnv.addObject("ERROR_MESSAGE", "Mechanic/Tire Man not saved. Kindly check inputted fields.");
			return mnv;
		}
		// add success message
		mechanicTireMan.setMechanicTireManName(mechanicTireMan.getMechanicTireManName().toUpperCase());
		mechanicTireManService.saveMechanicTireMan(mechanicTireMan);
		
		ModelAndView mnv = new ModelAndView("redirect:/admin/mechanic-tireman/SUCCESS");
		return mnv;
	}
}
