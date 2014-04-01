package com.altostratus.bionicwheels.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.bionicwheels.model.TransactionsWithinDate;
import com.altostratus.bionicwheels.model.TransactionsWithinDateUser;
import com.altostratus.bionicwheels.service.InventoryTransactionProductService;
import com.altostratus.bionicwheels.service.InventoryTransactionService;
import com.altostratus.core.model.User;
import com.altostratus.core.service.UserManagementService;

@Controller("viewTransactions")
public class ViewTransactionsController {

	private Logger logger = LoggerFactory.getLogger(ViewTransactionsController.class);

	@Autowired
	InventoryTransactionService inventoryTransactionService;

	@Autowired
	InventoryTransactionProductService inventoryTransactionProductService;

	@Autowired
	UserManagementService userManagementService;

	@RequestMapping(value = "/admin/view-transactions", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView viewTransactionsIndex(HttpServletRequest request, Principal principal) {
		logger.info(principal.getName() + " has entered the view transactions page.");
		ModelAndView mnv = new ModelAndView("admin.viewtransactions.index");
		mnv.addObject("transactionDates", new TransactionsWithinDate());
		return mnv;
	}

	@RequestMapping(value = "/admin/view-transactions-user", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView viewTransactionsByUserIndex(HttpServletRequest request, Principal principal) {
		logger.info(principal.getName() + " has entered the view transactions by user page.");
		ModelAndView mnv = new ModelAndView("admin.viewtransactionsuser.index");
		mnv.addObject("transactionDatesUser", new TransactionsWithinDateUser());
		mnv.addObject("users", userManagementService.getBasicUsers());
		return mnv;
	}
	
	@RequestMapping(value = "/view-transactions-user", method = RequestMethod.GET)
	public ModelAndView viewTransactionsByOneUserIndex(HttpServletRequest request, Principal principal) {
		logger.info(principal.getName() + " tries to access his/her own transactions only.");
		User user = userManagementService.getUserByUsername(principal.getName());
		List<User> users = new ArrayList<User>();
		users.add(user);
		ModelAndView mnv = new ModelAndView("admin.viewtransactionsuser.index");
		mnv.addObject("transactionDatesUser", new TransactionsWithinDateUser());
		mnv.addObject("users", users);
		return mnv;
	}

	@RequestMapping(value = "/view-transactions-user", method = RequestMethod.POST)
	public ModelAndView viewTransactionsByUser(HttpServletRequest request, @ModelAttribute("transactionDatesUser") TransactionsWithinDateUser transactionsWithinDateUser, BindingResult result, Principal principal) {
		
		logger.info(principal.getName() + " tries to view per user transaction/s.");
		
		ModelAndView mnv = new ModelAndView("admin.viewtransactionsuser.index");
		String date1 = transactionsWithinDateUser.getDate1();
		String date2 = transactionsWithinDateUser.getDate2();

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);
			User user = userManagementService.getUser(transactionsWithinDateUser.getUserId());

			List<InventoryTransaction> inventoryTransactions = inventoryTransactionService.getAllInventoryTransactionsWithinDateByUser(user, startDate, endDate);

			Double totalCostWithinDates = 0.0;
			Double totalSRPWithinDates = 0.0;
			// Double totalPurchasesCost = 0.0;

			for (InventoryTransaction inventoryTransaction : inventoryTransactions) {

				if (!(inventoryTransaction.getTransactionType().equalsIgnoreCase(InventoryTransaction.TRANSACTION_TYPE.INVENTORY_IN.toString()))) {
					// totalPurchasesCost +=
					// inventoryTransaction.getTotalTransactionCost();

					totalCostWithinDates += inventoryTransaction.getTotalTransactionCost();
					totalSRPWithinDates += inventoryTransaction.getTotalTransactionSale();
				}

			}

			Double totalProfitWithinDates = totalSRPWithinDates - totalCostWithinDates;

			logger.info("TOTAL COST: " + totalCostWithinDates);
			logger.info("TOTAL SRP: " + totalSRPWithinDates);
			logger.info("TOTAL PROFIT: " + totalProfitWithinDates);
			// logger.info("TOTAL PURCHASES COST: " + totalPurchasesCost);

			mnv.addObject("transactions", inventoryTransactions);
			mnv.addObject("totalCost", totalCostWithinDates);
			mnv.addObject("totalSRP", totalSRPWithinDates);
			mnv.addObject("totalProfit", totalProfitWithinDates);
			mnv.addObject("users", userManagementService.getBasicUsers());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("ERROR_MESSAGE", "There were no transactions found with the specified dates or user.");
			mnv.addObject("users", userManagementService.getBasicUsers());
			return mnv;
		}
		return mnv;

	}

	@RequestMapping(value = "/admin/view-transactions", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView viewTransactions(HttpServletRequest request, @ModelAttribute("transactionDates") TransactionsWithinDate transactionsWithinDate, BindingResult result, Principal principal) {

		logger.info(principal.getName() + " tries to view transactions within dates.");
		
		ModelAndView mnv = new ModelAndView("admin.viewtransactions.index");

		String date1 = transactionsWithinDate.getDate1();
		String date2 = transactionsWithinDate.getDate2();

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);

			List<InventoryTransaction> inventoryTransactions = inventoryTransactionService.getAllInventoryTransactionsWithinDate(startDate, endDate);

			Double totalCostWithinDates = 0.0;
			Double totalSRPWithinDates = 0.0;
			Double totalPurchasesCost = 0.0;

			for (InventoryTransaction inventoryTransaction : inventoryTransactions) {

				if (inventoryTransaction.getTransactionType().equalsIgnoreCase(InventoryTransaction.TRANSACTION_TYPE.INVENTORY_IN.toString())) {
					totalPurchasesCost += inventoryTransaction.getTotalTransactionCost();
				} else {
					totalCostWithinDates += inventoryTransaction.getTotalTransactionCost();
					totalSRPWithinDates += inventoryTransaction.getTotalTransactionSale();
				}

			}

			Double totalProfitWithinDates = totalSRPWithinDates - totalCostWithinDates;

			logger.info("TOTAL COST: " + totalCostWithinDates);
			logger.info("TOTAL SRP: " + totalSRPWithinDates);
			logger.info("TOTAL PROFIT: " + totalProfitWithinDates);
			logger.info("TOTAL PURCHASES COST: " + totalPurchasesCost);

			mnv.addObject("transactions", inventoryTransactions);
			mnv.addObject("totalCost", totalCostWithinDates);
			mnv.addObject("totalSRP", totalSRPWithinDates);
			mnv.addObject("totalProfit", totalProfitWithinDates);
			mnv.addObject("totalPurchases", totalPurchasesCost);
			mnv.addObject("netProfit", totalProfitWithinDates - totalPurchasesCost);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("ERROR_MESSAGE", "There were no transactions found with the specified dates.");
			return mnv;
		}
		return mnv;
	}

	@RequestMapping(value = "/view-transaction/{id}", method = RequestMethod.GET)
	public ModelAndView viewTransaction(HttpServletRequest request, @PathVariable("id") Long id, Principal principal) {
		
		logger.info(principal.getName() + " views per transaction line item.");
		
		ModelAndView mnv = new ModelAndView("admin.viewtransaction.index");
		mnv.addObject("transaction",inventoryTransactionService.getInventoryTransaction(id));
		mnv.addObject("transactionProducts", inventoryTransactionProductService.getAllProductsWithTransactionInventoryId(id));
		return mnv;
	}
}
