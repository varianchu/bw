package com.altostratus.bionicwheels.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.altostratus.core.model.Role;
import com.altostratus.core.model.User;
import com.altostratus.core.service.UserManagementService;

@Controller
@RequestMapping(value = "/admin")
public class UserAccountController {
	private Logger logger = LoggerFactory
			.getLogger(UserAccountController.class);

	@Autowired
	UserManagementService userManagementService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView viewUsers(HttpServletRequest request) {
		logger.info("Entering user account details index. ");
		logger.info("Entering admin user index");
		User user = userManagementService.getUserByUsername(request
				.getUserPrincipal().getName());
		logger.info("User logged in as: " + user.getUsername());
		logger.info("ROLES:");
		for (Role r : user.getRoles()) {
			logger.info(r.toString());
		}
		List<User> users = userManagementService.getBasicUsers();
		List<User> userList = new ArrayList<User>();
		for (User u : users) {
			u.setRole(u.getRoles().get(0));
			userList.add(u);
		}
		return new ModelAndView("usersettings.index", "users", userList);
	}

	@RequestMapping(value = { "/user/create" }, method = RequestMethod.GET)
	public ModelAndView userNew(HttpServletRequest request) {
		logger.info("Entering admin user new user");
		ModelAndView mnv = new ModelAndView("usersettings.form");
		mnv.addObject("user", new User());
		mnv.addObject("roles", userManagementService.getAllRoles());

		return mnv;
	}

	@RequestMapping(value = { "/user" }, method = RequestMethod.POST)
	public ModelAndView userSave(@ModelAttribute("user") User user,
			BindingResult result, HttpServletRequest request) {
		logger.info("Saving user");
		Role r = userManagementService.getRole(user.getRoleId());
		logger.info("Role of saved user: " + r.getName());
		List<Role> roles = new ArrayList<Role>();
		roles.add(r);
		user.setRoles(roles);
		user.setUserType("USER");

		logger.info("roles and user type are set! usertype is "
				+ user.getUserType() + " and role is " + user.getRoles());

		// franchiseeValidator.validate(franchisee, result);
		//
		// if(result.hasErrors()){
		// return new ModelAndView("admin.franchisee.form");
		// }

		logger.info("USER ID: " + user.getId());
		logger.info("USER EMAIL: " + user.getEmail());
		logger.info("USER FIRST NAME: " + user.getFirstName());
		logger.info("USER LAST NAME: " + user.getLastName());
		logger.info("USER PASSWORD: " + user.getPassword());

		ModelAndView mnv = new ModelAndView("usersettings.index",
				"SUCCESS_MESSAGE", "Successfully saved User "
						+ user.getUsername());
		try {
			user = (User) userManagementService.saveUser(user);
			logger.info("Saved user!");
			List<User> users = userManagementService.getBasicUsers();
			List<User> userList = new ArrayList<User>();
			for (User u : users) {
				u.setRole(u.getRoles().get(0));
				userList.add(u);
			}
			mnv.addObject("users", userList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			mnv = new ModelAndView("usersettings.form", "ERROR_MESSAGE",
					"Error: " + e.getMessage());
			mnv.addObject("user", user);
			mnv.addObject("roles", userManagementService.getAllRoles());
		}

		return mnv;
	}

	@RequestMapping(value = { "/user/edit/{id}" }, method = RequestMethod.GET)
	public ModelAndView userEdit(@PathVariable("id") Long id,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView("usersettings.form");
		User user = userManagementService.getUser(id);
		user.setRoleId(user.getRoles().get(0).getId());
		mnv.addObject("user", user);
		mnv.addObject("roles", userManagementService.getAllRoles());

		logger.info("EDIT LOGGER MAN: " + id);
		logger.info("EMAIL: " + user.getEmail());
		logger.info("FIRST NAME: " + user.getFirstName());
		logger.info("LAST NAME: " + user.getLastName());
		logger.info("USERNAME: " + user.getUsername());
		logger.info("PASSWORD: " + user.getPassword());
		return mnv;
	}

	/**
	 * Remove a franchisee (manual way)
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/user/remove/{id}" }, method = RequestMethod.GET)
	public ModelAndView userRemove(@PathVariable("id") Long id,
			HttpServletRequest request) {
		logger.info("Removing user with id " + id);
		User user = userManagementService.getUserByUsername(request
				.getUserPrincipal().getName());
		Role r = userManagementService.getRole(userManagementService
				.getRoleByName("ROLE_ADMIN").getId());

		if (user.getId() != id) {
			userManagementService.removeUser(id);
			ModelAndView mnv = new ModelAndView("usersettings.index",
					"SUCCESS_MESSAGE", "Successfully deleted User");
			List<User> users = userManagementService.getBasicUsers();
			List<User> userList = new ArrayList<User>();
			for (User u : users) {
				u.setRole(u.getRoles().get(0));
				userList.add(u);
			}
			mnv.addObject("users", userList);
			return mnv;
		} else {
			ModelAndView mnv = new ModelAndView("usersettings.index",
					"ERROR_MESSAGE",
					"Unable to delete User since you are using this account");
			List<User> users = userManagementService.getBasicUsers();
			List<User> userList = new ArrayList<User>();
			for (User u : users) {
				u.setRole(u.getRoles().get(0));
				userList.add(u);
			}
			mnv.addObject("users", userList);
			return mnv;
		}
	}

}
