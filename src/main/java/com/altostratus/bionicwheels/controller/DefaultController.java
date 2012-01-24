package com.altostratus.bionicwheels.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.core.service.UserManagementService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value={""})
public class DefaultController
{
	@Autowired
	UserManagementService userManagementService;

	private Logger logger = LoggerFactory.getLogger(DefaultController.class);

	@RequestMapping(value={"", "/", "/dashboard"}, method=RequestMethod.GET)
	public ModelAndView dashboard(HttpServletRequest request) {
		logger.info("Entering dashbaord");

		ModelAndView mnv = new ModelAndView("index");

		return mnv;
	}
}
