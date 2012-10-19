package com.altostratus.bionicwheels.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.altostratus.bionicwheels.model.TransactionsWithinDate;
import com.altostratus.bionicwheels.model.TransactionsWithinDateUser;
import com.altostratus.bionicwheels.service.InventoryTransactionProductService;
import com.altostratus.bionicwheels.service.InventoryTransactionService;
import com.altostratus.core.model.User;
import com.altostratus.core.service.UserManagementService;

@Controller("viewTransactions")
@RequestMapping("/admin")
public class ViewTransactionsController {

	private Logger logger = LoggerFactory
			.getLogger(ViewTransactionsController.class);

	@Autowired
	InventoryTransactionService inventoryTransactionService;

	@Autowired
	InventoryTransactionProductService inventoryTransactionProductService;

	@Autowired
	UserManagementService userManagementService;

	@RequestMapping(value = "/view_transactions", method = RequestMethod.GET)
	public ModelAndView viewTransactionsIndex(HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView("admin.viewtransactions.index");
		mnv.addObject("transactionDates", new TransactionsWithinDate());
		return mnv;
	}

	@RequestMapping(value = "/view_transactions_user", method = RequestMethod.GET)
	public ModelAndView viewTransactionsByUserIndex(HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView("admin.viewtransactionsuser.index");
		mnv.addObject("transactionDatesUser", new TransactionsWithinDateUser());
		mnv.addObject("users", userManagementService.getBasicUsers());
		return mnv;
	}

	@RequestMapping(value = "/view_transactions_user", method = RequestMethod.POST)
	public ModelAndView viewTransactionsByUser(
			HttpServletRequest request,
			@ModelAttribute("transactionDatesUser") TransactionsWithinDateUser transactionsWithinDateUser,
			BindingResult result) {
		ModelAndView mnv = new ModelAndView("admin.viewtransactionsuser.index");
		String date1 = transactionsWithinDateUser.getDate1();
		String date2 = transactionsWithinDateUser.getDate2();

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);
			User user = userManagementService
					.getUser(transactionsWithinDateUser.getUserId());
			mnv.addObject("transactions", inventoryTransactionService
					.getAllInventoryTransactionsWithinDateByUser(user,
							startDate, endDate));
			mnv.addObject("users", userManagementService.getBasicUsers());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("ERROR_MESSAGE",
					"There were no transactions found with the specified dates or user.");
			return mnv;
		}
		return mnv;

	}

	@RequestMapping(value = "/view_transactions", method = RequestMethod.POST)
	public ModelAndView viewTransactions(
			HttpServletRequest request,
			@ModelAttribute("transactionDates") TransactionsWithinDate transactionsWithinDate,
			BindingResult result) {
		ModelAndView mnv = new ModelAndView("admin.viewtransactions.index");

		String date1 = transactionsWithinDate.getDate1();
		String date2 = transactionsWithinDate.getDate2();

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);
			mnv.addObject("transactions", inventoryTransactionService
					.getAllInventoryTransactionsWithinDate(startDate, endDate));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("ERROR_MESSAGE",
					"There were no transactions found with the specified dates.");
			return mnv;
		}
		return mnv;
	}

	@RequestMapping(value = "/view_transaction/{id}", method = RequestMethod.GET)
	public ModelAndView viewTransaction(HttpServletRequest request,
			@PathVariable("id") Long id) {
		ModelAndView mnv = new ModelAndView("admin.viewtransaction.index");
		mnv.addObject("transaction",
				inventoryTransactionService.getInventoryTransaction(id));
		mnv.addObject("transactionProducts", inventoryTransactionProductService
				.getAllProductsWithTransactionInventoryId(id));
		return mnv;
	}
}
