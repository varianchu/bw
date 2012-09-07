package com.altostratus.bionicwheels.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.TransactionsWithinDate;
import com.altostratus.bionicwheels.service.InventoryTransactionProductService;
import com.altostratus.bionicwheels.service.InventoryTransactionService;

@Controller("viewTransactions")
@RequestMapping("/admin")
public class ViewTransactionsController {

	private Logger logger = LoggerFactory
			.getLogger(ViewTransactionsController.class);

	@Autowired
	InventoryTransactionService inventoryTransactionService;

	@Autowired
	InventoryTransactionProductService inventoryTransactionProductService;

	@RequestMapping(value = "/view_transactions", method = RequestMethod.GET)
	public ModelAndView viewTransactionsIndex(HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView("admin.viewtransactions.index");
		mnv.addObject("transactionDates", new TransactionsWithinDate());
		return mnv;
	}

	@RequestMapping(value = "/view_transactions", method = RequestMethod.POST)
	public ModelAndView viewTransactions(
			HttpServletRequest request,
			@ModelAttribute("transactionDates") TransactionsWithinDate transactionsWithinDate) {
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
			mnv.addObject("fail",
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
