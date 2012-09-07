package com.altostratus.bionicwheels.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.Settings;
import com.altostratus.bionicwheels.service.SettingsService;

@Controller("settingsController")
@RequestMapping("admin")
public class SettingsController {

	@Autowired
	SettingsService settingsService;

	private Logger logger = LoggerFactory.getLogger(SettingsController.class);

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public ModelAndView settingsIndex(HttpServletRequest request) {

		logger.info("entering settings index");

		ModelAndView mnv = new ModelAndView("admin.settings.index");
		Long id = (long) 1;
		if (settingsService.getSettings(id) == null) {
			mnv.addObject("setting", new Settings());
			mnv.addObject("stock", Settings.STOCK_PROCESS.values());
			return mnv;
		} else {
			mnv.addObject("setting", settingsService.getSettings(id));
			mnv.addObject("stock", Settings.STOCK_PROCESS.values());
			return mnv;
		}
	}

	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public ModelAndView saveSettings(HttpServletRequest request,
			@ModelAttribute("setting") Settings setting, BindingResult result) {
		setting = settingsService.saveSettings(setting);
		logger.info("settings saved!");
		ModelAndView mnv = new ModelAndView("redirect:/admin/settings");
		return mnv;
	}

}
