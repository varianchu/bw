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

import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.service.CategoryService;
import com.altostratus.bionicwheels.validator.CategoryValidator;

@Controller
@RequestMapping(value = "/admin")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CategoryValidator categoryValidator;

	private Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ModelAndView categoryIndex(HttpServletRequest request) {
		logger.info("entering category index");
		ModelAndView mnv = new ModelAndView("admin.category.index");
		mnv.addObject("category", new Category());
		mnv.addObject("categories", categoryService.getAllCategories());
		return mnv;
	}

	@RequestMapping(value = "/category/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editCategory(@PathVariable("id") Long categoryId,
			HttpServletRequest request) {
		logger.info("Editing category id: " + categoryId.toString());
		ModelAndView mnv = new ModelAndView("admin.category.index");
		mnv.addObject("category", categoryService.getCategory(categoryId));
		mnv.addObject("categories", categoryService.getAllCategories());
		return mnv;
	}

	@RequestMapping(value = "/category/remove/{id}", method = RequestMethod.GET)
	public ModelAndView removeCategory(@PathVariable("id") Long categoryId,
			HttpServletRequest request) {
		logger.info("Removing category id: " + categoryId.toString());
		categoryService.removeCategory(categoryId);
		ModelAndView mnv = new ModelAndView("redirect:/admin/category");
		return mnv;
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public ModelAndView saveCategory(HttpServletRequest request,
			@ModelAttribute("category") Category category, BindingResult result) {
		logger.info("Saving Category");

		categoryValidator.validate(category, result);

		if (result.hasErrors()) {
			logger.info(result.toString());
			// mnv.addObject("category", new Category());
			// mnv.addObject("categories", categoryService.getAllCategories());
			logger.info("Failed to save category");
			ModelAndView mnv = new ModelAndView("admin.category.index");
			mnv.addObject("category", new Category());
			mnv.addObject("categories", categoryService.getAllCategories());
			return mnv;
		}

		categoryService.saveCategory(category);
		ModelAndView mnv = new ModelAndView("redirect:/admin/category");
		return mnv;
	}
}
