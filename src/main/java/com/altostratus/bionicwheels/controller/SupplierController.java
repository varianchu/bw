package com.altostratus.bionicwheels.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = "/supplier", method = RequestMethod.GET)
	public ModelAndView supplierIndex(HttpServletRequest request) {
		logger.info("entering supplier index");
		ModelAndView mnv = new ModelAndView("admin.supplier.index");
		mnv.addObject("supplier", new Supplier());
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		return mnv;
	}

	@RequestMapping(value = "/supplier/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editSupplier(@PathVariable("id") Long supplierId,
			HttpServletRequest request) {
		logger.info("Editing supplier id: " + supplierId.toString());
		ModelAndView mnv = new ModelAndView("admin.supplier.index");
		mnv.addObject("supplier", supplierService.getSupplier(supplierId));
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		return mnv;
	}

	@RequestMapping(value = "/supplier/remove/{id}", method = RequestMethod.GET)
	public ModelAndView removeSupplier(@PathVariable("id") Long supplierId,
			HttpServletRequest request) {
		logger.info("Removing supplier id: " + supplierId.toString());
		supplierService.removeSupplier(supplierId);
		ModelAndView mnv = new ModelAndView("redirect:/admin/supplier");
		return mnv;
	}

	@RequestMapping(value = "/supplier", method = RequestMethod.POST)
	public ModelAndView saveSupplier(HttpServletRequest request,
			@ModelAttribute("supplier") Supplier supplier, BindingResult result) {
		logger.info("Saving Supplier");

		supplierValidator.validate(supplier, result);

		if (result.hasErrors()) {
			logger.info("Failed to save supplier.");
			ModelAndView mnv = new ModelAndView("admin.supplier.index");
			mnv.addObject("supplier", new Supplier());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			return mnv;
		}

		supplierService.saveSupplier(supplier);
		ModelAndView mnv = new ModelAndView("redirect:/admin/supplier");
		return mnv;
	}
}
