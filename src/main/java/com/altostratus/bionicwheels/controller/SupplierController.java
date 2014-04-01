package com.altostratus.bionicwheels.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

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

import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.service.SupplierService;
import com.altostratus.bionicwheels.validator.SupplierValidator;

@Controller
@RequestMapping(value = "/admin")
public class SupplierController {

	@Autowired
	SupplierService supplierService;

	@Autowired
	SupplierValidator supplierValidator;

	private Logger logger = LoggerFactory.getLogger(SupplierController.class);

	@RequestMapping(value = "/supplier/{message}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER')")
	public ModelAndView supplierIndex(HttpServletRequest request, @PathVariable("message") String message, Principal principal) {
		logger.info(principal.getName() + " is entering supplier index");
		ModelAndView mnv = new ModelAndView("admin.supplier.index");
		mnv.addObject("supplier", new Supplier());
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		if (message.equalsIgnoreCase("SUCCESS")) {
			mnv.addObject("SUCCESS_MESSAGE", "Successfully saved Supplier");
		}
		return mnv;
	}

	@RequestMapping(value = "/supplier/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER')")
	public ModelAndView editSupplier(@PathVariable("id") Long supplierId, HttpServletRequest request, Principal principal) {
		logger.info(principal.getName() + " tries to edit supplier id: " + supplierId.toString());
		ModelAndView mnv = new ModelAndView("admin.supplier.index");
		mnv.addObject("supplier", supplierService.getSupplier(supplierId));
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		return mnv;
	}

	@RequestMapping(value = "/supplier/remove/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView removeSupplier(@PathVariable("id") Long supplierId, HttpServletRequest request, Principal principal) {
		logger.info(principal.getName() + " tries to remove supplier id: " + supplierId.toString());
		
		ModelAndView mnv = new ModelAndView("admin.supplier.index");
		try {
			supplierService.removeSupplier(supplierId);
			mnv.addObject("supplier", new Supplier());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			logger.info(principal.getName() + " has successfully removed supplier");
			mnv.addObject("SUCCESS_MESSAGE", "Successfully removed Supplier");
			return mnv;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			logger.info(principal.getName() + " remove unsuccessful (SUPPLIER).");

			mnv.addObject("supplier", new Supplier());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("ERROR_MESSAGE", "Unsuccessfully removed Supplier");
			return mnv;
		}
	}

	@RequestMapping(value = "/supplier", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER')")
	public ModelAndView saveSupplier(HttpServletRequest request, @ModelAttribute("supplier") Supplier supplier, BindingResult result, Principal principal) {
		logger.info(principal.getName() + " tries to save a supplier");

		supplierValidator.validate(supplier, result);

		if (result.hasErrors()) {
			logger.info(principal.getName() + " failed to save supplier.");
			ModelAndView mnv = new ModelAndView("admin.supplier.index");
			mnv.addObject("supplier", new Supplier());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("ERROR_MESSAGE", "Supplier not saved.");
			return mnv;
		}

		supplier.setSupplierName(supplier.getSupplierName().toUpperCase());
		supplier.setAddress(supplier.getAddress().toUpperCase());
		supplierService.saveSupplier(supplier);

		logger.info(principal.getName() + " has successfully saved a supplier.");
		
		ModelAndView mnv = new ModelAndView("redirect:/admin/supplier/SUCCESS");
		return mnv;
	}
}
