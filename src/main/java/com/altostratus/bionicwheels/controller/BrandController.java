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

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.SupplierService;
import com.altostratus.bionicwheels.validator.BrandValidator;

@Controller("brandController")
@RequestMapping(value = "/admin")
public class BrandController {

	@Autowired
	BrandService brandService;

	@Autowired
	SupplierService supplierService;

	@Autowired
	BrandValidator brandValidator;

	private String successMessage = null;
	private String errorMessage = null;

	private Logger logger = LoggerFactory.getLogger(BrandController.class);

	@RequestMapping(value = "/brand", method = RequestMethod.GET)
	public ModelAndView brandIndex(HttpServletRequest request) {
		logger.info("entering brand index");
		ModelAndView mnv = new ModelAndView("admin.brand.index");
		mnv.addObject("brand", new Brand());
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		mnv.addObject("brands", brandService.getAllBrands());
		mnv.addObject("SUCCESS_MESSAGE", successMessage);
		mnv.addObject("ERROR_MESSAGE", errorMessage);
		successMessage = null;
		errorMessage = null;
		return mnv;
	}

	@RequestMapping(value = "/brand/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editBrand(@PathVariable("id") Long brandId,
			HttpServletRequest request) {
		logger.info("Editing brand id: " + brandId.toString());
		ModelAndView mnv = new ModelAndView("admin.brand.index");
		mnv.addObject("brand", brandService.getBrand(brandId));
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		mnv.addObject("brands", brandService.getAllBrands());
		return mnv;
	}

	@RequestMapping(value = "/brand/remove/{id}", method = RequestMethod.GET)
	public ModelAndView removeBrand(@PathVariable("id") Long brandId,
			HttpServletRequest request) {
		logger.info("Removing brand id: " + brandId.toString());
		brandService.removeBrand(brandId);
		ModelAndView mnv = new ModelAndView("redirect:/admin/brand");
		return mnv;
	}

	@RequestMapping(value = "/brand", method = RequestMethod.POST)
	public ModelAndView saveBrand(HttpServletRequest request,
			@ModelAttribute("brand") Brand brand, BindingResult result) {

		logger.info("Saving Brand");

		brandValidator.validate(brand, result);

		if (result.hasErrors()) {
			logger.info("Failed to save brand.");
			ModelAndView mnv = new ModelAndView("admin.brand.index");
			mnv.addObject("brand", new Brand());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("ERROR_MESSAGE",
					"Brand not saved. Kindly check inputted fields.");
			successMessage = null;
			return mnv;
		}

		// logger.info("Failed to save category");
		brand.setSupplier(supplierService.getSupplier(brand.getSupplierId()));
		brandService.saveBrand(brand);
		successMessage = "Successfully saved brand.";
		errorMessage = null;
		ModelAndView mnv = new ModelAndView("redirect:/admin/brand");
		return mnv;
	}

}
