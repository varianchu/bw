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

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.SupplierService;
import com.altostratus.bionicwheels.validator.BrandValidator;

@Controller("brandController")
@RequestMapping(value = "/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER','TIRE_MAGS_PERSON')")
public class BrandController {

	@Autowired
	BrandService brandService;

	@Autowired
	SupplierService supplierService;

	@Autowired
	BrandValidator brandValidator;

	private Logger logger = LoggerFactory.getLogger(BrandController.class);

	@RequestMapping(value = "/brand/{message}", method = RequestMethod.GET)
	public ModelAndView brandIndex(HttpServletRequest request,
			@PathVariable("message") String message, Principal principal) {
		logger.info(principal.getName() + " is entering brand index.");
		ModelAndView mnv = new ModelAndView("admin.brand.index");
		mnv.addObject("brand", new Brand());
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		mnv.addObject("brands", brandService.getAllBrands());
		if (message.equalsIgnoreCase("SUCCESS")) {
			mnv.addObject("SUCCESS_MESSAGE", "Successfully saved Brand");
		}
		return mnv;
	}

	@RequestMapping(value = "/brand/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editBrand(@PathVariable("id") Long brandId,
			HttpServletRequest request, Principal principal) {
		logger.info(principal.getName() + " is trying to edit brand id: "
				+ brandId.toString());
		ModelAndView mnv = new ModelAndView("admin.brand.index");
		mnv.addObject("brand", brandService.getBrand(brandId));
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		mnv.addObject("brands", brandService.getAllBrands());
		return mnv;
	}

	@RequestMapping(value = "/brand/remove/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView removeBrand(@PathVariable("id") Long brandId,
			HttpServletRequest request, Principal principal) {

		logger.info(principal.getName() + " tries to remove brand id: "
				+ brandId.toString());
		ModelAndView mnv = new ModelAndView("admin.brand.index");
		try {
			brandService.removeBrand(brandId);
			mnv.addObject("brand", new Brand());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("SUCCESS_MESSAGE", "Successfully removed Brand");
			logger.info(principal.getName()
					+ " has successfully removed the brand.");
			return mnv;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			logger.info("remove unsuccessful (Brand) by " + principal.getName());

			mnv.addObject("brand", new Brand());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("ERROR_MESSAGE", "Unsuccessfully removed Brand");
			return mnv;
		}
	}

	@RequestMapping(value = "/brand", method = RequestMethod.POST)
	public ModelAndView saveBrand(HttpServletRequest request,
			@ModelAttribute("brand") Brand brand, BindingResult result,
			Principal principal) {

		logger.info(principal.getName() + " tries to save Brand.");

		brandValidator.validate(brand, result);

		if (result.hasErrors()) {
			logger.info("Failed to save brand by " + principal.getName());
			ModelAndView mnv = new ModelAndView("admin.brand.index");
			mnv.addObject("brand", new Brand());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("ERROR_MESSAGE",
					"Brand not saved. Kindly check inputted fields.");
			return mnv;
		}

		// logger.info("Failed to save category");
		brand.setSupplier(supplierService.getSupplier(brand.getSupplierId()));
		brand.setBrandName(brand.getBrandName().toUpperCase());
		brandService.saveBrand(brand);

		logger.info("Successfully saved brand by " + principal.getName());

		ModelAndView mnv = new ModelAndView("redirect:/admin/brand/SUCCESS");
		return mnv;
	}

}
