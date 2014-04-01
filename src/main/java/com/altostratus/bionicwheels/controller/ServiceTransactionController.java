package com.altostratus.bionicwheels.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;	
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.ServiceTransaction;
import com.altostratus.bionicwheels.model.ServiceTransactionItem;
import com.altostratus.bionicwheels.service.MechanicTireManService;
import com.altostratus.bionicwheels.service.ServiceTransactionItemService;
import com.altostratus.bionicwheels.service.ServiceTransactionService;
import com.altostratus.bionicwheels.validator.ServiceTransactionItemValidator;
import com.altostratus.bionicwheels.validator.ServiceTransactionValidator;
import com.altostratus.core.model.User;
import com.altostratus.core.service.UserManagementService;

@Controller("serviceTransactionController")
public class ServiceTransactionController {

	@Autowired
	ServiceTransactionService serviceTransactionService;

	@Autowired
	ServiceTransactionItemService serviceTransactionItemService;
	
	@Autowired
	ServiceTransactionItemValidator serviceTransactionItemValidator;
	
	@Autowired
	UserManagementService userManagementService;
	
	@Autowired
	ServiceTransactionValidator serviceTransactionValidator;

	@Autowired
	MechanicTireManService mechanicTireManService;
	
	private Logger logger = LoggerFactory.getLogger(ServiceTransactionController.class);

	@RequestMapping(value = "/admin/service-transaction/form", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER')")
	public ModelAndView serviceTransactionIndex(HttpServletRequest request, HttpSession session, Principal principal) {

		logger.info("Entering service transaction form " + principal.getName());

		List<ServiceTransactionItem> items = new ArrayList<ServiceTransactionItem>();

		session.setAttribute("items", items);

		@SuppressWarnings("unchecked")
		List<ServiceTransactionItem> sti = (List<ServiceTransactionItem>) session.getAttribute("items");
		
		logger.info("items size in session: " + sti.size());

		ModelAndView mnv = new ModelAndView("service.transaction.form");
		mnv.addObject("serviceTransactionForm", new ServiceTransaction());
		mnv.addObject("serviceTransactionItem", new ServiceTransactionItem());
		mnv.addObject("users", userManagementService.getBasicUsers());
		mnv.addObject("workers", mechanicTireManService.getAllMechanicTireMan());
		
		return mnv;
	}
	
	@RequestMapping(value = "/service-transaction/form", method = RequestMethod.GET)
	@PreAuthorize("hasRole('SERVICE_MANAGER')")
	public ModelAndView serviceTransactionIndexByUser(HttpServletRequest request, HttpSession session, Principal principal) {

		logger.info("Entering service transaction form " + principal.getName());

		List<ServiceTransactionItem> items = new ArrayList<ServiceTransactionItem>();

		session.setAttribute("items", items);

		@SuppressWarnings("unchecked")
		List<ServiceTransactionItem> sti = (List<ServiceTransactionItem>) session.getAttribute("items");
		
		logger.info("items size in session: " + sti.size());

		ModelAndView mnv = new ModelAndView("service.transaction.form");
		mnv.addObject("serviceTransactionForm", new ServiceTransaction());
		mnv.addObject("serviceTransactionItem", new ServiceTransactionItem());
		List<User> users = new ArrayList<User>();
		users.add(userManagementService.getUserByUsername(request.getUserPrincipal().getName()));
		mnv.addObject("users", users);
		mnv.addObject("workers", mechanicTireManService.getAllMechanicTireMan());
		
		return mnv;
	}

	@RequestMapping(value = "/admin/service-transaction/form", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER','SERVICE_MANAGER')")
	public ModelAndView saveServiceTransactionForm(HttpServletRequest request, @ModelAttribute("serviceTransactionForm") ServiceTransaction serviceTransaction, BindingResult result, HttpSession session, Principal principal) {
		
		logger.info("Saving Service Transaction by " + principal.getName());
		
		ModelAndView mnv = new ModelAndView("service.transaction.form");
		
		serviceTransactionValidator.validate(serviceTransaction, result);
		
		if(result.hasErrors()){
			logger.info(result.getAllErrors().toString());
			mnv.addObject("serviceTransactionForm", new ServiceTransaction());
			mnv.addObject("serviceTransactionItem", new ServiceTransactionItem());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("workers", mechanicTireManService.getAllMechanicTireMan());
			mnv.addObject("ERROR_MESSAGE", "Unsuccessful in saving service transaction");
			
			logger.info("Unsuccessfully saved service transaction by " + principal.getName());
			
			return mnv;
		}

		// logger.info("GET SERVICE TRANSACTION ITEM SIZE: " +
		// serviceTransaction.getServiceTransactionItemsBuffer().size());
		
		@SuppressWarnings("unchecked")
		List<ServiceTransactionItem> sti = (List<ServiceTransactionItem>) session.getAttribute("items");
		
		if(sti.size()==0){
			mnv.addObject("serviceTransactionForm", new ServiceTransaction());
			mnv.addObject("serviceTransactionItem", new ServiceTransactionItem());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("workers", mechanicTireManService.getAllMechanicTireMan());
			mnv.addObject("ERROR_MESSAGE", "Unsuccessful in saving service transaction - add a transaction item first");
			
			return mnv;
		}
		else{
			
			Double totalPrice = 0.0;
			Double totalProfit = 0.0;
			
			serviceTransaction.setUser(userManagementService.getUser(serviceTransaction.getUserId()));
			serviceTransaction.setServiceDate(new Date());
			serviceTransaction.setCar(serviceTransaction.getCar().toUpperCase());
			serviceTransaction.setCustomerName(serviceTransaction.getCustomerName().toUpperCase());
			serviceTransaction = (ServiceTransaction) serviceTransactionService.saveServiceTransaction(serviceTransaction);
			
//			@SuppressWarnings("unchecked")
//			List<ServiceTransactionItem> sti = (List<ServiceTransactionItem>) session.getAttribute("items");
			
			for(ServiceTransactionItem item : sti){
				ServiceTransactionItem serviceTransactionItem = new ServiceTransactionItem();
				serviceTransactionItem.setServiceMadePart(item.getServiceMadePart());
				serviceTransactionItem.setServicePrice(item.getServicePrice());
				serviceTransactionItem.setServiceProfit(item.getServiceProfit());
				serviceTransactionItem.setServiceTransaction(serviceTransaction);
				serviceTransactionItem = (ServiceTransactionItem) serviceTransactionItemService.saveServiceTransactionItem(serviceTransactionItem);
				
				totalPrice += item.getServicePrice();
				totalProfit += item.getServiceProfit();
			}
			
			serviceTransaction.setTotalServiceTransactionSale(totalPrice);
			serviceTransaction.setTotalServiceTransactionProfit(totalProfit);
			serviceTransaction = (ServiceTransaction) serviceTransactionService.saveServiceTransaction(serviceTransaction);
			
			logger.info("saved service transaction with price: " + totalPrice + " by " + principal.getName() + " date: " + serviceTransaction.getServiceDate());
		}
		
		session.removeAttribute("items");
		
		ServiceTransaction st = new ServiceTransaction();
		
		mnv.addObject("serviceTransactionForm", st);
		mnv.addObject("serviceTransactionItem", new ServiceTransactionItem());
		mnv.addObject("users", userManagementService.getBasicUsers());
		mnv.addObject("workers", mechanicTireManService.getAllMechanicTireMan());
		mnv.addObject("SUCCESS_MESSAGE", "Successfully saved service transaction.");
		
		logger.info("successfully saved service transaction by " + principal.getName());
		
		return mnv;
	}
	
	@RequestMapping(value = "/admin/service-transaction-item")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER','SERVICE_MANAGER')")
	@ResponseBody
	public String addServiceItemToArray(@ModelAttribute("serviceTransactionItem") ServiceTransactionItem serviceTransactionItem, BindingResult result, HttpSession session){
		
		logger.info("adding service transaction item");
		
		serviceTransactionItemValidator.validate(serviceTransactionItem, result);
		
		if(result.hasErrors()){
			String error = result.getFieldError().getDefaultMessage();
			return error;
		}
		
		@SuppressWarnings("unchecked")
		List<ServiceTransactionItem> items = (ArrayList<ServiceTransactionItem>) session.getAttribute("items");
		
		serviceTransactionItem.setServiceMadePart(serviceTransactionItem.getServiceMadePart().toUpperCase());
		
		items.add(serviceTransactionItem);
		
		session.setAttribute("items", items);
		
		@SuppressWarnings("unchecked")
		List<ServiceTransactionItem> items2 = (ArrayList<ServiceTransactionItem>) session.getAttribute("items");
		
		logger.info("size of new array in items is: " + items2.size());
		for(ServiceTransactionItem item : items2){
			logger.info("Service Made/Parts: " + item.getServiceMadePart());
			logger.info("Service Price: " + item.getServicePrice());
			logger.info("Service Profit: " + item.getServiceProfit());
			logger.info("-------------------------------");
		}
		
		return "successful";
	}
	
	@RequestMapping(value = "/admin/view-service-transactions")
	public ModelAndView viewServiceTransactionsIndex(HttpServletRequest request, Principal principal){
		
		logger.info(principal.getName() + " enters view service transactions url.");
		
		ModelAndView mnv = new ModelAndView("admin.viewservicetransactions.index");
		
		mnv.addObject("serviceTransactionForm", new ServiceTransaction());
		
		return mnv;
	}
	
	@RequestMapping(value = "/admin/view-service-transactions-user")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER','SERVICE_MANAGER')")
	public ModelAndView viewServiceTransactionsByUserIndex(HttpServletRequest request, Principal principal){
		
		logger.info(principal.getName() + " enters view service transactions user url.");
		
		ModelAndView mnv = new ModelAndView("admin.viewservicetransactionsuser.index");
		
		mnv.addObject("serviceTransactionForm", new ServiceTransaction());
		mnv.addObject("users", userManagementService.getBasicUsers());
		
		return mnv;
	}
	
	@RequestMapping(value = "/view-service-transactions-user")
	@PreAuthorize("hasRole('SERVICE_MANAGER')")
	public ModelAndView viewServiceTransactionsByUserIndexByUser(HttpServletRequest request, Principal principal){
		
		logger.info(principal.getName() + " enters view service transactions user url.");
		
		ModelAndView mnv = new ModelAndView("admin.viewservicetransactionsuser.index");
		
		mnv.addObject("serviceTransactionForm", new ServiceTransaction());
		List<User> users = new ArrayList<User>();
		users.add(userManagementService.getUserByUsername(request.getUserPrincipal().getName()));
		mnv.addObject("users", users);
		
		return mnv;
	}
	
	@RequestMapping(value = "/admin/view-service-transactions-worker")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER','SERVICE_MANAGER')")
	public ModelAndView viewServiceTransactionsByWorkerIndex(HttpServletRequest request, Principal principal){
		
		logger.info(principal.getName() + " enters view service transactions worker url.");
		
		ModelAndView mnv = new ModelAndView("admin.viewservicetransactionsworker.index");
		
		mnv.addObject("serviceTransactionForm", new ServiceTransaction());
		mnv.addObject("workers", mechanicTireManService.getAllMechanicTireMan());
		
		return mnv;
	}
	
	
	@RequestMapping(value = "/admin/view-service-transactions", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER','SERVICE_MANAGER')")
	public ModelAndView getServiceTransactionsWithinDate(HttpServletRequest request, @ModelAttribute("serviceTransactionForm") ServiceTransaction serviceTransaction, BindingResult result, Principal principal){
		
		logger.info(principal.getName() + " tries to view service transactions within date.");
		
		ModelAndView mnv = new ModelAndView("admin.viewservicetransactions.index");
		
		String date1 = serviceTransaction.getStartDate();
		String date2 = serviceTransaction.getEndDate();
		
		Double totalSale = 0.0;
		Double totalProfit = 0.0;

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);
		
			List<ServiceTransaction> serviceTransactions = serviceTransactionService.getServiceTransactionsByDateBetween(startDate, endDate);
			
			for(ServiceTransaction st : serviceTransactions){
				totalSale += st.getTotalServiceTransactionSale();
				totalProfit += st.getTotalServiceTransactionProfit();
			}
			
			mnv.addObject("serviceTransactionForm", new ServiceTransaction());
			mnv.addObject("serviceTransactions", serviceTransactions);
			mnv.addObject("totalSRP", totalSale);
			mnv.addObject("totalProfit", totalProfit);
			
			return mnv;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			
			mnv.addObject("ERROR_MESSAGE", "Unable to get service transactions within date. Kindly check the format of the date.");
			mnv.addObject("serviceTransactionForm", new ServiceTransaction());
			
			return mnv;
		}
	}
	
	@RequestMapping(value = "/admin/view-service-transactions-user", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER','SERVICE_MANAGER')")
	public ModelAndView getServiceTransactionsWithinDateUser(HttpServletRequest request, @ModelAttribute("serviceTransactionForm") ServiceTransaction serviceTransaction, BindingResult result, Principal principal){
		
		logger.info(principal.getName() + " tries to view service transactions by user within date.");
		
		ModelAndView mnv = new ModelAndView("admin.viewservicetransactionsuser.index");
		
		String date1 = serviceTransaction.getStartDate();
		String date2 = serviceTransaction.getEndDate();
		User user = userManagementService.getUser(serviceTransaction.getUserId());
		
		Double totalSale = 0.0;
		Double totalProfit = 0.0;
		
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);
		
			List<ServiceTransaction> serviceTransactions = serviceTransactionService.getServiceTransactionsByUserAndDateBetween(user, startDate, endDate);
			
			for(ServiceTransaction st : serviceTransactions){
				totalSale += st.getTotalServiceTransactionSale();
				totalProfit += st.getTotalServiceTransactionProfit();
			}
			
			mnv.addObject("serviceTransactionForm", new ServiceTransaction());
			mnv.addObject("serviceTransactions", serviceTransactions);
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("user", user);
			mnv.addObject("totalSRP", totalSale);
			mnv.addObject("totalProfit", totalProfit);
			
			return mnv;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			
			mnv.addObject("ERROR_MESSAGE", "Unable to get service transactions within date by user. Kindly check the format of the date.");
			mnv.addObject("serviceTransactionForm", new ServiceTransaction());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("user", user);
			
			return mnv;
		}
	}
	
	@RequestMapping(value = "/admin/view-service-transactions-worker", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER','SERVICE_MANAGER')")
	public ModelAndView getServiceTransactionsWithinDateWorker(HttpServletRequest request, @ModelAttribute("serviceTransactionForm") ServiceTransaction serviceTransaction, BindingResult result, Principal principal){
		
		logger.info(principal.getName() + " tries to view service transactions by worker within date.");
		
		ModelAndView mnv = new ModelAndView("admin.viewservicetransactionsworker.index");
		
		String date1 = serviceTransaction.getStartDate();
		String date2 = serviceTransaction.getEndDate();
		
		Double totalSale = 0.0;
		Double totalProfit = 0.0;
		
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);
		
			List<ServiceTransaction> serviceTransactions = serviceTransactionService.getServiceTransactionByMechanicTireManAndDateBetween(serviceTransaction.getMechanicTireMan(), startDate, endDate);
			
			for(ServiceTransaction st : serviceTransactions){
				totalSale += st.getTotalServiceTransactionSale();
				totalProfit += st.getTotalServiceTransactionProfit();
			}
			
			mnv.addObject("serviceTransactionForm", new ServiceTransaction());
			mnv.addObject("serviceTransactions", serviceTransactions);
			mnv.addObject("workers", mechanicTireManService.getAllMechanicTireMan());
			mnv.addObject("worker", serviceTransaction.getMechanicTireMan());
			mnv.addObject("totalSRP", totalSale);
			mnv.addObject("totalProfit", totalProfit);
			
			return mnv;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			
			mnv.addObject("ERROR_MESSAGE", "Unable to get service transactions within date by worker. Kindly check the format of the date.");
			mnv.addObject("serviceTransactionForm", new ServiceTransaction());
			mnv.addObject("workers", mechanicTireManService.getAllMechanicTireMan());
			mnv.addObject("worker", serviceTransaction.getMechanicTireMan());
			
			return mnv;
		}
	}
	
	@RequestMapping(value = "/admin/view-service-transactions/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'CASHIER', 'ROLE_SYSTEM_MANAGER','SERVICE_MANAGER')")
	public ModelAndView getServiceTransactionItem(HttpServletRequest request, @PathVariable("id") Long id, Principal principal){
		
		logger.info("Accessing service transaction item by " + principal.getName());
		
		ModelAndView mnv = new ModelAndView("admin.viewservicetransactionitem.index");
		
		ServiceTransaction serviceTransaction = serviceTransactionService.getServiceTransaction(id);
		List<ServiceTransactionItem> serviceTransactionItems = serviceTransactionItemService.getServiceTransactionItemsByServiceTransaction(serviceTransaction);
		
		mnv.addObject("serviceTransactionItems", serviceTransactionItems);
		
		mnv.addObject("serviceTransaction", serviceTransaction);
		
		return mnv;
	}
	
}
