package com.altostratus.bionicwheels.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.altostratus.bionicwheels.model.AccountsReceivable;
import com.altostratus.bionicwheels.model.Customer;
import com.altostratus.bionicwheels.service.AccountsReceivableService;
import com.altostratus.bionicwheels.service.CustomerCarService;
import com.altostratus.bionicwheels.validator.AccountsReceivableValidator;

@Controller("accountsReceivableController")
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER')")
public class AccountsReceivableController {

	@Autowired
	AccountsReceivableService accountsReceivableService;

	@Autowired
	CustomerCarService customerCarService;

	@Autowired
	AccountsReceivableValidator accountsReceivableValidator;

	private Logger logger = LoggerFactory.getLogger(AccountsReceivableController.class);

	@RequestMapping(value = "/accounts-receivable/form", method = RequestMethod.GET)
	public ModelAndView accountsReceivableIndex(HttpServletRequest request, Principal principal) {

		logger.info("entering accounts receivable form by " + principal.getName());
		System.out.println("entering accounts receivable form by " + principal.getName());
		
		List<String> terms = new ArrayList<String>();

		terms.add("30 days");
		terms.add("60 days");
		terms.add("90 days");
		terms.add("120 days");
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());

		ModelAndView mnv = new ModelAndView("admin.accountsreceivable.form");
		mnv.addObject("customers", customerCarService.getAllCustomers());
		mnv.addObject("accountsReceivable", new AccountsReceivable());
		mnv.addObject("terms", terms);
		mnv.addObject("date", s);

		return mnv;
	}
	
	@RequestMapping(value = "/accounts-receivable/form/{id}", method = RequestMethod.GET)
	public ModelAndView accountsReceivableEdit(HttpServletRequest request, @PathVariable("id") Long id, Principal principal) {

		logger.info("entering accounts receivable form by " + principal.getName());
		System.out.println("entering accounts receivable form by " + principal.getName());
		
		List<String> terms = new ArrayList<String>();

		terms.add("30 days");
		terms.add("60 days");
		terms.add("90 days");
		terms.add("120 days");
		
		AccountsReceivable ar = accountsReceivableService.getAccountsReceivable(id);
		logger.info("customer is " + ar.getCustomer());
		ar.setCustomerId(ar.getCustomer().getId());
		
		ModelAndView mnv = new ModelAndView("admin.accountsreceivable.form");
		mnv.addObject("customers", customerCarService.getAllCustomers());
		mnv.addObject("accountsReceivable", ar);
		mnv.addObject("terms", terms);
		mnv.addObject("date", ar.getDateCreated());

		return mnv;
	}

	@RequestMapping(value = "/accounts-receivable", method = RequestMethod.GET)
	public ModelAndView viewDueAccountsReceivableIndex(HttpServletRequest request, Principal principal) {

		logger.info("entering accounts receivable viewer by " + principal.getName());
		System.out.println("entering accounts receivable viewer by " + principal.getName());
		
		Date dateToday = new Date();

		List<AccountsReceivable> accountsReceivables = accountsReceivableService.getDueAccountsReceivable(dateToday);

		Double totalAmount = 0.0;

		for (AccountsReceivable accountsReceivable : accountsReceivables) {
			totalAmount += accountsReceivable.getAmount();
		}

		ModelAndView mnv = new ModelAndView("admin.accountsreceivable.view");
		mnv.addObject("dueAccountsReceivable", accountsReceivables);
		mnv.addObject("totalAmount", totalAmount);
		mnv.addObject("customer", "VARIOUS");
		mnv.addObject("customers", customerCarService.getAllCustomers());

		return mnv;
	}

	@RequestMapping(value = "/accounts-receivable-all", method = RequestMethod.GET)
	public ModelAndView viewAllAccountsReceivableIndex(HttpServletRequest request, Principal principal) {

		logger.info("entering accounts receivable viewer by " + principal.getName());
		System.out.println("entering accounts receivable viewer by " + principal.getName());
		
		List<AccountsReceivable> accountsReceivables = accountsReceivableService.getAllAccountsReceivable();

		Double totalAmount = 0.0;

		for (AccountsReceivable accountsReceivable : accountsReceivables) {
			totalAmount += accountsReceivable.getAmount();
		}

		ModelAndView mnv = new ModelAndView("admin.accountsreceivable.view");
		mnv.addObject("dueAccountsReceivable", accountsReceivables);
		mnv.addObject("totalAmount", totalAmount);
		mnv.addObject("customer", "VARIOUS");
		mnv.addObject("customers", customerCarService.getAllCustomers());

		return mnv;
	}

	@RequestMapping(value = "/accounts-receivable/receiptnumber/{receiptNumber}", method = RequestMethod.GET)
	public ModelAndView viewDueAccountsReceivableIndexByReceiptNumber(HttpServletRequest request,@PathVariable("receiptNumber") String receiptNumber, Principal principal) {

		logger.info("entering accounts receivable viewer - by receipt number - by " + principal.getName());
		System.out.println("entering accounts receivable viewer - by receipt number - by " + principal.getName());
		
		AccountsReceivable accountsReceivable = accountsReceivableService.getAccountsReceivableByReceiptNumber(receiptNumber);

		if (accountsReceivable == null) {

			Date dateToday = new Date();

			ModelAndView mnv = new ModelAndView("admin.accountsreceivable.view");
			mnv.addObject("dueAccountsReceivable", accountsReceivableService.getDueAccountsReceivable(dateToday));
			mnv.addObject("totalAmount", 0.0);
			mnv.addObject("customer", "NONE");
			mnv.addObject("customers", customerCarService.getAllCustomers());
			mnv.addObject("ERROR_MESSAGE", "No Reference Number Found!");

			return mnv;
		}

		Double totalAmount = accountsReceivable.getAmount();

		List<AccountsReceivable> accountsReceivables = new ArrayList<AccountsReceivable>();

		accountsReceivables.add(accountsReceivable);

		ModelAndView mnv = new ModelAndView("admin.accountsreceivable.view");
		mnv.addObject("dueAccountsReceivable", accountsReceivables);
		mnv.addObject("totalAmount", totalAmount);
		mnv.addObject("customer", accountsReceivable.getCustomer().getCustomerName());
		mnv.addObject("customers", customerCarService.getAllCustomers());

		return mnv;
	}

	@RequestMapping(value = "/accounts-receivable/customer/{customerId}", method = RequestMethod.GET)
	public ModelAndView viewDueAccountsReceivableIndexByReceiptNumber(HttpServletRequest request, @PathVariable("customerId") Long customerId, Principal principal) {

		logger.info("entering accounts receivable viewer - by customer - by " + principal.getName());
		System.out.println("entering accounts receivable viewer - by customer - by " + principal.getName());
		
		Customer customer = customerCarService.getCustomerById(customerId);

		List<AccountsReceivable> accountsReceivables = accountsReceivableService.getAccountsReceivableByCustomer(customer);

		Double totalAmount = 0.0;

		for (AccountsReceivable accountsReceivable : accountsReceivables) {
			totalAmount += accountsReceivable.getAmount();
		}

		ModelAndView mnv = new ModelAndView("admin.accountsreceivable.view");
		mnv.addObject("dueAccountsReceivable", accountsReceivables);
		mnv.addObject("totalAmount", totalAmount);
		mnv.addObject("customer", customer.getCustomerName());
		mnv.addObject("customers", customerCarService.getAllCustomers());

		return mnv;
	}

	// edit this
	@RequestMapping(value = "/accounts-receivable/form", method = RequestMethod.POST)
	public ModelAndView accountsReceivableSave(HttpServletRequest request, @ModelAttribute("accountsReceivable") AccountsReceivable accountsReceivable, BindingResult bindingResult, Principal principal) {

		logger.info("trying to save accounts receivable by " + principal.getName());
		System.out.println("trying to save accounts receivable by " + principal.getName());
		
		List<String> terms = new ArrayList<String>();

		terms.add("30 days");
		terms.add("60 days");
		terms.add("90 days");
		terms.add("120 days");

		String dateTodayString = accountsReceivable.getDateCreatedValue();
		Date expectedDateReceivable = null;
		Date dateToday = null;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());

		try {
			dateToday = df.parse(dateTodayString);
		}
		catch (Exception e) {
			// TODO: handle exception
			logger.info(bindingResult.toString());
			// mnv.addObject("category", new Category());
			// mnv.addObject("categories", categoryService.getAllCategories());
			logger.info("Failed to save accounts receivable because of Date");
			
			ModelAndView mnv = new ModelAndView("admin.accountsreceivable.form");
			mnv.addObject("customers", customerCarService.getAllCustomers());
			mnv.addObject("accountsReceivable", new AccountsReceivable());
			mnv.addObject("terms", terms);
			mnv.addObject("ERROR_MESSAGE", "Accounts Receivable not saved because of Date.");
			mnv.addObject("date", s);
			// successMessage = null;
			return mnv;
		}

		accountsReceivableValidator.validate(accountsReceivable, bindingResult);

		if (bindingResult.hasErrors()) {
			logger.info(bindingResult.toString());
			// mnv.addObject("category", new Category());
			// mnv.addObject("categories", categoryService.getAllCategories());
			logger.info("Failed to save accounts receivable");
			ModelAndView mnv = new ModelAndView("admin.accountsreceivable.form");
			mnv.addObject("customers", customerCarService.getAllCustomers());
			mnv.addObject("accountsReceivable", new AccountsReceivable());
			mnv.addObject("terms", terms);
			mnv.addObject("ERROR_MESSAGE", "Accounts Receivable not saved. Kindly check inputted fields.");
			mnv.addObject("date", new Date());
			// successMessage = null;
			return mnv;
		}

		expectedDateReceivable = expectDate(accountsReceivable.getExpectedDateReceivableValue(), dateToday);

		accountsReceivable.setExpectedDateReceivable(expectedDateReceivable);
		accountsReceivable.setCustomer(customerCarService.getCustomerById(accountsReceivable.getCustomerId()));
		accountsReceivable.setDateCreated(dateToday);

		accountsReceivable = (AccountsReceivable) accountsReceivableService.saveAccountsReceivable(accountsReceivable);

		ModelAndView mnv = new ModelAndView("admin.accountsreceivable.form");
		mnv.addObject("SUCCESS_MESSAGE", "Accounts Receivable Saved");
		mnv.addObject("customers", customerCarService.getAllCustomers());
		mnv.addObject("accountsReceivable", new AccountsReceivable());
		mnv.addObject("terms", terms);
		mnv.addObject("date", s);
		// mnv.addObject("terms", terms);

		return mnv;
	}

	@RequestMapping(value = "/accounts-receivable/remove-paid/{id}", method = RequestMethod.GET)
	public ModelAndView paidAccountsReceivable(@PathVariable("id") Long accountsReceivableId, HttpServletRequest request, Principal principal) {
		
		logger.info("Removing accounts receivable id: " + accountsReceivableId.toString() + "(PAID ALREADY) viewed by " + principal.getName());
		System.out.println("Removing accounts receivable id: " + accountsReceivableId.toString() + "(PAID ALREADY) viewed by " + principal.getName());
		String receiptNumber = accountsReceivableService.getAccountsReceivable(accountsReceivableId).getReceiptNumber();

		Date dateToday = new Date();
		
		List<AccountsReceivable> accountsReceivables = accountsReceivableService.getDueAccountsReceivable(dateToday);

		Double totalAmount = 0.0;

		for (AccountsReceivable accountsReceivable : accountsReceivables) {
			totalAmount += accountsReceivable.getAmount();
		}
		
		try {
			accountsReceivableService.removeAccountsReceivable(accountsReceivableId);
			String successMessage = "Account Removed (RECEIPT NUMBER: "	+ receiptNumber + ") - Customer paid the debt already";
			// change the redirect to the accounts receivables page
			ModelAndView mnv = new ModelAndView("admin.accountsreceivable.view");
			mnv.addObject("dueAccountsReceivable", accountsReceivables);
			mnv.addObject("totalAmount", totalAmount);
			mnv.addObject("customer", "VARIOUS");
			mnv.addObject("customers", customerCarService.getAllCustomers());
			mnv.addObject("SUCCESS_MESSAGE", successMessage);
			return mnv;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			logger.info("remove unsuccessful (Accounts Receivable).");
			String errorMessage = "Delete Accounts Receivable Unsuccessful.";
			// change the redirect to the accounts receivables page
			ModelAndView mnv = new ModelAndView("admin.accountsreceivable.view");
			mnv.addObject("dueAccountsReceivable", accountsReceivables);
			mnv.addObject("totalAmount", totalAmount);
			mnv.addObject("customer", "VARIOUS");
			mnv.addObject("customers", customerCarService.getAllCustomers());
			mnv.addObject("ERROR_MESSAGE", errorMessage);
			return mnv;
		}
	}

	public Date expectDate(String terms, Date dateToday) {

		Date expectedDateReceivable = null;

		Integer value = 0;

		if (terms.equalsIgnoreCase("30 days")) {
			value = 30;
		} else if (terms.equalsIgnoreCase("60 days")) {
			value = 60;
		} else if (terms.equalsIgnoreCase("90 days")) {
			value = 90;
		} else if (terms.equalsIgnoreCase("120 days")) {
			value = 120;
		}

		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(dateToday);
			cal.add(Calendar.DATE, value);

			expectedDateReceivable = cal.getTime();
			System.out.println(cal.getTime());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {

			DateFormat format2 = new SimpleDateFormat("EEEE");
			String finalDay = format2.format(expectedDateReceivable);

			if (finalDay.equalsIgnoreCase("Sunday")) {
				try {
					Integer number = value + 1;
					cal.setTime(dateToday);
					cal.add(Calendar.DATE, number);
					expectedDateReceivable = cal.getTime();
					System.out.println("WITHIN TRY CATCH " + finalDay + " "
							+ expectedDateReceivable);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else if (finalDay.equalsIgnoreCase("Saturday")) {
				try {
					Integer number = value + 2;
					cal.setTime(dateToday);
					cal.add(Calendar.DATE, number);
					expectedDateReceivable = cal.getTime();
					System.out.println("WITHIN TRY CATCH " + finalDay + " "
							+ expectedDateReceivable);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

			System.out.println("NOT WITHIN TRY CATCH " + finalDay);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return expectedDateReceivable;
	}

}
