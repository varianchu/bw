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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.Settings;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.CategoryService;
import com.altostratus.bionicwheels.service.SettingsService;
import com.altostratus.bionicwheels.service.SupplierService;

@Controller("settingsController")
@RequestMapping("admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class SettingsController {

	@Autowired
	SettingsService settingsService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	BrandService brandService;

	@Autowired
	SupplierService supplierService;

	private Logger logger = LoggerFactory.getLogger(SettingsController.class);

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public ModelAndView settingsIndex(HttpServletRequest request,
			Principal principal) {

		logger.info("entering settings index viewed by " + principal.getName());

		ModelAndView mnv = new ModelAndView("admin.settings.index");
		Long id = (long) 1;
		if (settingsService.getSettings(id) == null) {
			mnv.addObject("setting", new Settings());
			mnv.addObject("stock", Settings.STOCK_PROCESS.values());
			mnv.addObject("filter", Settings.FILTER_BY.values());
			mnv.addObject("categories", categoryService.getAllCategories());
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			return mnv;
		} else {
			mnv.addObject("setting", settingsService.getSettings(id));
			mnv.addObject("stock", Settings.STOCK_PROCESS.values());
			mnv.addObject("filter", Settings.FILTER_BY.values());
			mnv.addObject("categories", categoryService.getAllCategories());
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			return mnv;
		}
	}

	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public ModelAndView saveSettings(HttpServletRequest request,
			@ModelAttribute("setting") Settings setting, BindingResult result,
			Principal principal) {

		setting = settingsService.saveSettings(setting);
		logger.info("settings saved! by " + principal.getName());
		ModelAndView mnv = new ModelAndView("redirect:/admin/settings");
		return mnv;
	}

}
