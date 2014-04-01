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

	@RequestMapping(value = "/category/{message}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER')")
	public ModelAndView categoryIndex(HttpServletRequest request, @PathVariable("message") String message, Principal principal) {
		logger.info(principal.getName() + " is entering category index");
		ModelAndView mnv = new ModelAndView("admin.category.index");
		mnv.addObject("category", new Category());
		mnv.addObject("categories", categoryService.getAllCategories());
		if(message.equalsIgnoreCase("SUCCESS")){
			mnv.addObject("SUCCESS_MESSAGE", "Successfully saved Category");
		}
		return mnv;
	}

	@RequestMapping(value = "/category/edit/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER')")
	public ModelAndView editCategory(@PathVariable("id") Long categoryId, HttpServletRequest request, Principal principal) {
		logger.info(principal.getName() + " is editing category id: " + categoryId.toString());
		ModelAndView mnv = new ModelAndView("admin.category.index");
		mnv.addObject("category", categoryService.getCategory(categoryId));
		mnv.addObject("categories", categoryService.getAllCategories());
		return mnv;
	}

	@RequestMapping(value = "/category/remove/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView removeCategory(@PathVariable("id") Long categoryId, HttpServletRequest request, Principal principal) {
		logger.info(principal.getName() + " tries o remove category id: " + categoryId.toString());
		ModelAndView mnv = new ModelAndView("admin.category.index");
		try {
			categoryService.removeCategory(categoryId);
			logger.info("successfully removed category by " + principal.getName());
			mnv.addObject("category", new Category());
			mnv.addObject("categories", categoryService.getAllCategories());
			mnv.addObject("SUCCESS_MESSAGE", "Successfully removed Category");
			return mnv;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			logger.info("remove unsuccessful (Category).");
			
			mnv.addObject("category", new Category());
			mnv.addObject("categories", categoryService.getAllCategories());
			mnv.addObject("ERROR_MESSAGE", "Remove Unsuccessful.");
			return mnv;
		}
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER')")
	public ModelAndView saveCategory(HttpServletRequest request, @ModelAttribute("category") Category category, BindingResult result, Principal principal) {
		
		logger.info(principal.getName() + " is trying to save a category");

		categoryValidator.validate(category, result);

		if (result.hasErrors()) {
			logger.info(result.toString());
			logger.info(principal.getName() + " failed to save category");
			ModelAndView mnv = new ModelAndView("admin.category.index");
			mnv.addObject("category", new Category());
			mnv.addObject("categories", categoryService.getAllCategories());
			mnv.addObject("ERROR_MESSAGE", "Category not saved. Kindly check inputted fields.");
			return mnv;
		}
		// add success message
		category.setCategoryName(category.getCategoryName().toUpperCase());
		category.setCode(category.getCode().toUpperCase());
		categoryService.saveCategory(category);
		
		logger.info("Successfully saved category by " + principal.getName());
		
		ModelAndView mnv = new ModelAndView("redirect:/admin/category/SUCCESS");
		return mnv;
	}
}
