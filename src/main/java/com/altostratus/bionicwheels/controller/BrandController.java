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

@Controller("brandController")
@RequestMapping(value = "/admin")
public class BrandController {

	@Autowired
	BrandService brandService;

	@Autowired
	SupplierService supplierService;

	private Logger logger = LoggerFactory.getLogger(BrandController.class);

	@RequestMapping(value = "/brand", method = RequestMethod.GET)
	public ModelAndView brandIndex(HttpServletRequest request) {
		logger.info("entering brand index");
		ModelAndView mnv = new ModelAndView("admin.brand.index");
		mnv.addObject("brand", new Brand());
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		mnv.addObject("brands", brandService.getAllBrands());
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

		// logger.info("Failed to save category");
		brand.setSupplier(supplierService.getSupplier(brand.getSupplierId()));
		brandService.saveBrand(brand);
		ModelAndView mnv = new ModelAndView("redirect:/admin/brand");
		return mnv;
	}

}
