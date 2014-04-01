package com.altostratus.bionicwheels.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.DummyProductQty;
import com.altostratus.bionicwheels.model.Inventory;
import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.bionicwheels.model.InventoryTransactionProduct;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.model.Settings;
import com.altostratus.bionicwheels.service.InventoryService;
import com.altostratus.bionicwheels.service.InventoryTransactionProductService;
import com.altostratus.bionicwheels.service.InventoryTransactionService;
import com.altostratus.bionicwheels.service.ProductService;
import com.altostratus.bionicwheels.service.SettingsService;
import com.altostratus.bionicwheels.validator.InventoryTransactionValidator;
import com.altostratus.core.model.User;
import com.altostratus.core.service.UserManagementService;
import com.altostratus.core.util.ConvertCode;
import com.altostratus.core.util.CustomComparatorAscending;
import com.altostratus.core.util.CustomComparatorDescending;
import com.altostratus.core.util.InsufficientStockException;

@Controller("inventoryTransactionProductController")
public class InventoryTransactionProductController {

	@Autowired
	ProductService productService;

	@Autowired
	InventoryTransactionService inventoryTransactionService;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	InventoryTransactionProductService inventoryTransactionProductService;

	@Autowired
	InventoryTransactionValidator inventoryTransactionValidator;

	@Autowired
	SettingsService settingsService;

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	ConvertCode convertCode;

	private Logger logger = LoggerFactory.getLogger(InventoryTransactionProductController.class);

	@RequestMapping(value = "/stocktransaction-out")
	public ModelAndView stockTransactionOut(HttpServletRequest request, HttpSession session, Principal principal) {
		
		logger.info("Entering stock transaction index - stock-out by " + principal.getName());
		
		List<DummyProductQty> transactionItems = new ArrayList<DummyProductQty>();
		
		session.setAttribute("transactionItems", transactionItems);
		session.setAttribute("qtyOutChecker", "true");
		
		@SuppressWarnings("unchecked")
		List<DummyProductQty> checkTransactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
		
		logger.info("get transaction items size: " + checkTransactionItems.size());
		logger.info("quantity out checker is: " + session.getAttribute("qtyOutChecker"));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());
		ModelAndView mnv = new ModelAndView("admin.inventorytransactionproduct.out");
		mnv.addObject("transaction", new InventoryTransaction());

		mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
		mnv.addObject("getDate", s);

		return mnv;
	}

	@RequestMapping(value = "/stocktransaction-in")
	public ModelAndView stockTransactionIn(HttpServletRequest request, HttpSession session, Principal principal) {

		logger.info("Entering stock transaction index - stock-in by " + principal.getName());
		
		List<DummyProductQty> transactionItems = new ArrayList<DummyProductQty>();
		
		session.setAttribute("transactionItems", transactionItems);
		session.setAttribute("qtyOutChecker", "false");
		
		@SuppressWarnings("unchecked")
		List<DummyProductQty> checkTransactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
		
		logger.info("get transaction items size: " + checkTransactionItems.size());
		logger.info("quantity out checker is: " + session.getAttribute("qtyOutChecker"));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());
		ModelAndView mnv = new ModelAndView("admin.inventorytransactionproduct.in");
		mnv.addObject("transaction", new InventoryTransaction());

		mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
		mnv.addObject("getDate", s);

		return mnv;
	}

	@RequestMapping(value = "/stocktransaction-refund")
	public ModelAndView stockTransactionRefund(HttpServletRequest request, HttpSession session, Principal principal) {

		logger.info("Entering stock transaction index - refund by " + principal.getName());
		
		List<DummyProductQty> transactionItems = new ArrayList<DummyProductQty>();
		
		session.setAttribute("transactionItems", transactionItems);
		session.setAttribute("qtyOutChecker", "false");
		
		@SuppressWarnings("unchecked")
		List<DummyProductQty> checkTransactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
		
		logger.info("get transaction items size: " + checkTransactionItems.size());
		logger.info("quantity out checker is: " + session.getAttribute("qtyOutChecker"));
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());
		ModelAndView mnv = new ModelAndView("admin.inventorytransactionproduct.refund");
		mnv.addObject("transaction", new InventoryTransaction());

		mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
		mnv.addObject("getDate", s);

		return mnv;
	}

	@RequestMapping(value = "/getproductintransaction/{id}")
	public @ResponseBody
	ArrayList<String> getProductInTransaction(@PathVariable("id") Long id, Principal principal) {
		
		Product product = productService.getProduct(id);
		logger.info("Product is " + product + " - " + principal.getName());

		Long settingsId = (long) 1;

		List<Inventory> inventoriesOfProduct = inventoryService.getInventoryByProduct(product);

		logger.info("inventory size array: " + inventoriesOfProduct.size());

		if (settingsService.getSettings(settingsId) == null) {
			ArrayList<String> productInfo = new ArrayList<String>();
			logger.info("NULL SETTINGS!");
			productInfo.add("NO PRODUCT FOUND! - NULL SETTINGS");
			productInfo.add("NO PRODUCT CODE!");
			productInfo.add("NO QUANTITY FOUND!");
			productInfo.add("NO UNIT OF MEASURE FOUND!");
			productInfo.add("NO INVENTORY FOUND!");
			productInfo.add("NO INVENTORY FOUND!");
			return productInfo;
		}

		if (product != null) {

				ArrayList<String> productInfo = new ArrayList<String>();
				productInfo.add(product.getProductName());
				productInfo.add(product.getCode());
				productInfo.add(product.getTotalQty().toString());
				productInfo.add(product.getUnitOfMeasure().toString());
				String cost = "";
				String price = inventoryService.getInventory(product.getInventoryId()).getSrp().toString();

				if (settingsService.getSettings(settingsId).getStockProcess().equalsIgnoreCase("FIFO")) {

					logger.info("ASCENDING");

					Collections.sort(inventoriesOfProduct, new CustomComparatorAscending());

					for (Inventory inventory : inventoriesOfProduct) {
						cost = cost + convertCode.getCostCode(inventory.getCost());
						cost += "/";
					}
				} else {

					logger.info("DESCENDING");

					Collections.sort(inventoriesOfProduct, new CustomComparatorDescending());

					for (Inventory inventory : inventoriesOfProduct) {
						cost = cost + convertCode.getCostCode(inventory.getCost());
						cost += "/";
					}
				}
				productInfo.add(cost);
				productInfo.add(price);

				return productInfo;
				
		} else {
			ArrayList<String> productInfo = new ArrayList<String>();
			productInfo.add("NO PRODUCT FOUND!");
			productInfo.add("NO PRODUCT CODE!");
			productInfo.add("NO QUANTITY FOUND!");
			productInfo.add("NO UNIT OF MEASURE FOUND!");
			productInfo.add("NO INVENTORY FOUND!");
			productInfo.add("NO INVENTORY FOUND!");
			return productInfo;
		}
	}

	// used AJAX for array updating
	// 12/10/13 - I think logic in backend is ok, but need to return string of
	// items to the UI/front-end. - price in the parameter can be removed
	// change the return type to list string.
	// planning to remove the merge product into one if the same.
	@RequestMapping(value = "/addtransactionproduct/{id}/{qty}/{price}/")
	public @ResponseBody ArrayList<String> addTransactionProductToArrayList(HttpSession session, @PathVariable("id") Long id, @PathVariable("qty") Double qty, @PathVariable("price") String stringPrice, Principal principal) {

		ArrayList<String> productInfo = new ArrayList<String>();
		String checker = (String) session.getAttribute("qtyOutChecker");
		
		@SuppressWarnings("unchecked")
		List<DummyProductQty> transactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
		
		// convert stringPrice to Double price
		Double price;
		try {
			price = Double.parseDouble(stringPrice);
		} catch (NumberFormatException e) {
			logger.info("no product found!");
			productInfo.add("Error");
			productInfo.add("Product Not Found");

			return productInfo;
		}
		// Real Product
		Product product = productService.getProduct(id);

		logger.info("ENTERED - PRODUCT NAME IS: " + product.getProductName() + " by " + principal.getName());

		if (product.getTotalQty() < qty && checker.equalsIgnoreCase("true")) {
			logger.info("check quantity");
			productInfo.add("Error");
			productInfo.add("Error in getting product, check quantity");
			return productInfo;
		}

		// boolean checker = false;
		// boolean loopChecker = false;

		List<Inventory> inventories = new ArrayList<Inventory>();

		// testing the new solution - 12/8/13 (place in if statement product
		// !=
		// null after else bracket -- integrate this to the existing if
		// statement in checking if the same product
		inventories = inventoryService.getInventoryByProduct(product);

		Double bufferQty = qty;

		// integrate previous block with this below -start of integration
		if (inventories.size() > 0) {
			if (settingsService.getSettings((long) 1).getStockProcess().equalsIgnoreCase("LIFO")) {
				logger.info("LIFO");
				Collections.sort(inventories, new CustomComparatorDescending());
			} else {
				logger.info("FIFO");
				Collections.sort(inventories, new CustomComparatorAscending());
				logger.info("inside if statement inventory.size FIFO");
			}
		}
		// logger.info("TESTING SHIZ");

		// if (product != null) {

		// use inventory entity to check and add the arraylist
		if (checker.equalsIgnoreCase("false")) {
			logger.info("inside if statement - checker (FALSE)");
			DummyProductQty dpq = new DummyProductQty();
			dpq.setId(id);
			dpq.setQty(qty);
			dpq.setPrice(price);
			// might change this soon to CODE
			dpq.setCost(inventoryService.getInventory(product.getInventoryId()).getCost());

			transactionItems.add(dpq);
			
			session.setAttribute("transactionItems", transactionItems);
			
			productInfo.add(product.getProductName());
			// productInfo.add(product.getCode());
			productInfo.add(convertCode.getCostCode(inventoryService.getInventory(product.getInventoryId()).getCost()));
			productInfo.add(qty.toString());
			productInfo.add(product.getUnitOfMeasure());
			productInfo.add("N/A");

			logger.info(product.getProductName() + " - " + qty.toString() + " " + product.getUnitOfMeasure() + " " + "N/A" + " by " + principal.getName());

			return productInfo;
		}

		for (DummyProductQty dummyProductQty : transactionItems) {

			if (dummyProductQty.getId() == id) {
				productInfo.add("Error");
				productInfo.add("Product Found but Identical");

				logger.info("Identical Product. by " + principal.getName());

				return productInfo;
			}

		}

		logger.info("STOCK OUT");

		for (Inventory inventory : inventories) {
			if (bufferQty <= inventory.getQty()) {

				DummyProductQty dpq = new DummyProductQty();
				dpq.setId(id);
				dpq.setQty(bufferQty);
				dpq.setPrice(price);
				// might change this soon to CODE
				dpq.setCost(inventory.getCost());
				// dpq.setCheckerQty(qty + dummyProductQty.getCheckerQty());

				transactionItems.add(dpq);
				
				session.setAttribute("transactionItems", transactionItems);
				// add UI row here
				productInfo.add(product.getProductName());
				// productInfo.add(product.getCode());
				productInfo.add(convertCode.getCostCode(inventory.getCost()));
				productInfo.add(bufferQty.toString());
				productInfo.add(product.getUnitOfMeasure());
				productInfo.add(price.toString());

				logger.info(product.getProductName() + " - " + inventory.getCost().toString() + " " + qty.toString() + " " + product.getUnitOfMeasure() + " " + price.toString() + " by " + principal.getName());

				return productInfo;
			} else {

				if (inventory.getQty() != 0.0) {
					bufferQty = bufferQty - inventory.getQty();

					DummyProductQty dpq = new DummyProductQty();
					dpq.setId(id);
					dpq.setQty(inventory.getQty());
					dpq.setPrice(price);
					// might change this soon to CODE
					dpq.setCost(inventory.getCost());
					// dpq.setCheckerQty(qty +
					// dummyProductQty.getCheckerQty());

					logger.info(inventory.getCost().toString());

					productInfo.add(product.getProductName());
					// productInfo.add(product.getCode());
					productInfo.add(convertCode.getCostCode(inventory.getCost()));
					productInfo.add(inventory.getQty().toString());
					productInfo.add(product.getUnitOfMeasure());
					productInfo.add(price.toString());

					transactionItems.add(dpq);
					
					session.setAttribute("transactionItems", transactionItems);
					// add UI row here
				}
			}
		}

		// arrayList.add(dpq);
		logger.info("START UP: ARRAY LIST SIZE IS " + transactionItems.size() + " " + principal.getName());

		return productInfo;
	}

	@RequestMapping(value = "/deleteproductintransaction/{id}/")
	public @ResponseBody
	Double deleteTransactionProductToArrayList(@PathVariable("id") Long id, HttpSession session, Principal principal) {
		
		logger.info("delete product id: " + id + " by " + principal.getName());
		
		@SuppressWarnings("unchecked")
		List<DummyProductQty> transactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
		
		List<DummyProductQty> removeList = new ArrayList<DummyProductQty>();

		Double removedSRP = 0.0;

		for (DummyProductQty dpq : transactionItems) {
			
			if (dpq.getId() == id) {
				logger.info("DUMMY PRODUCT ID IS: " + dpq.getId() + " DUMMY PRODUCT QTY IS: " + dpq.getQty() + " DUMMY PRODUCT PRICE IS: " + dpq.getPrice());

				removeList.add(dpq);

				removedSRP += dpq.getQty() * dpq.getPrice();
			}
		}

		transactionItems.removeAll(removeList);
		
		session.setAttribute("transactionItems", transactionItems);

		logger.info("transactionItems size is " + transactionItems.size());

		return removedSRP;
	}

	// edit the delete check @Transactional
	// @Transactional(rollbackFor = InsufficientStockException.class)
	@RequestMapping(value = "/stocktransaction-in", method = RequestMethod.POST)
	public ModelAndView saveInventoryTransactionInProduct(HttpSession session, HttpServletRequest request, @ModelAttribute("transaction") InventoryTransaction inventoryTransaction, BindingResult result, Principal principal) {

		@SuppressWarnings("unchecked")
		List<DummyProductQty> transactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
		
		logger.info("[IN SAVE ITP] transactionItems size is: " + transactionItems.size() + " by " + principal.getName());
		ModelAndView mnv = new ModelAndView("admin.inventorytransactionproduct.in");
		ArrayList<String> headsUpMsgs = new ArrayList<String>();

		Long inventoryTransactionId = null;
		Double totalCost = 0.0;
		Double totalSale = 0.0;

		logger.info("Inventory Transaction is: " + InventoryTransaction.TRANSACTION_TYPE.INVENTORY_IN.toString());

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date dateCreated = df.parse(inventoryTransaction.getDateCreatedValue());
			inventoryTransaction.setDateCreated(dateCreated);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ModelAndView("redirect:/admin/stocktransaction-in");
		}

		inventoryTransactionValidator.validate(inventoryTransaction, result);

		if (result.hasErrors()) {
			logger.info(result.toString());
			mnv.addObject("transaction", new InventoryTransaction());
			mnv.addObject("headsup", headsUpMsgs);
			mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String s = df.format(new Date());
			mnv.addObject("getDate", s);
			logger.info("FAIL, Entering Index Again (Inventory IN)");
			return mnv;
		}

		if (transactionItems.size() != 0) {

			InventoryTransaction it = new InventoryTransaction();
			it.setTransactionType(InventoryTransaction.TRANSACTION_TYPE.INVENTORY_IN.toString());
			it.setReferenceNumber(inventoryTransaction.getReferenceNumber());
			User user = userManagementService.getUserByUsername(request.getUserPrincipal().getName());
			it.setUser(user);
			it.setPointPersonName(user.getUsername() + " - " + user.getFirstName());

			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dateCreated = df.parse(inventoryTransaction.getDateCreatedValue());
				it.setDateCreated(dateCreated);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return new ModelAndView("redirect:/admin/stocktransaction-in");
			}
			it = (InventoryTransaction) inventoryTransactionService.saveInventoryTransaction(it);

			inventoryTransactionId = it.getId();

			logger.info("transaction date is " + it.getDateCreated());

			// if (it.getTransactionType().equalsIgnoreCase("INVENTORY_IN")) {
			// logger.info("[INVENTORY IN]");
			for (DummyProductQty dpq : transactionItems) {
				logger.info("SAVE DPQ TO ITP");
				InventoryTransactionProduct itp = new InventoryTransactionProduct();
				itp.setInventoryTransaction(it);
				itp.setProduct(productService.getProduct(dpq.getId()));
				itp.setProductName(productService.getProduct(dpq.getId()).getProductName());
				itp.setQty(dpq.getQty());
				// i think no need to get the product cost and srp if
				// inventory in - but overall this would work
				itp.setProductCost(inventoryService.getInventory(productService.getProduct(dpq.getId()).getInventoryId()).getCost());
				itp.setProductSale(0.0);
				itp.setCategory(productService.getProduct(dpq.getId()).getCategory());
				itp.setUom(productService.getProduct(dpq.getId()).getUnitOfMeasure());

				logger.info("Inventory transaction id: " + it.getId());
				logger.info("Inventory transaction product is: " + itp.getProduct().getProductName());
				logger.info("Inventory transaction qty is: " + itp.getQty());

				itp = (InventoryTransactionProduct) inventoryTransactionProductService.saveInventoryTransactionProduct(itp);

				totalCost = totalCost + (dpq.getQty() * inventoryService.getInventory(productService.getProduct(dpq.getId()).getInventoryId()).getCost());

				Product product = (Product) productService.getProduct(dpq.getId());
				product.setTotalQty(product.getTotalQty() + dpq.getQty());
				product = productService.saveProduct(product);
				Inventory inventory = (Inventory) inventoryService.getInventory(product.getInventoryId());
				inventory.setQty(inventory.getQty() + dpq.getQty());
				inventory = inventoryService.saveInventory(inventory);
				headsUpMsgs.add("Successfully added quantity of Product: " + product.getProductName() + " - its quantity is now: " + product.getTotalQty());
			}
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());

		if (transactionItems.size() == 0) {
			logger.info("No Product to process!");
			headsUpMsgs.add("No Product for Transaction to be processed! Please add products for transaction");
			mnv.addObject("transaction", new InventoryTransaction());
			// mnv.addObject("transactionType",
			// InventoryTransaction.TRANSACTION_TYPE.values());
			mnv.addObject("headsup", headsUpMsgs);
			mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
			mnv.addObject("getDate", s);
			logger.info("ERROR!, Entering Index Again");

			transactionItems.clear();
			
			session.setAttribute("transactionItems", transactionItems);

			return mnv;

		}

		InventoryTransaction transaction = inventoryTransactionService.getInventoryTransaction(inventoryTransactionId);

		transaction.setTotalTransactionCost(totalCost);
		transaction.setTotalTransactionSale(totalSale);

		transaction = (InventoryTransaction) inventoryTransactionService.saveInventoryTransaction(transaction);

		Settings settings = settingsService.getSettings((long) 1);
		settings.setTransactionNumber(settings.getTransactionNumber() + 1);
		settings = settingsService.saveSettings(settings);
		
		mnv.addObject("transaction", new InventoryTransaction());
		mnv.addObject("headsup", headsUpMsgs);
		mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
		mnv.addObject("getDate", s);
		logger.info("Saved Transaction (INVENTORY-IN), Entering Index Again" + " by " + principal.getName());

		transactionItems.clear();
		
		session.setAttribute("transactionItems", transactionItems);
		
		return mnv;
	}

	// edit the delete check @Transactional
	// @Transactional(rollbackFor = InsufficientStockException.class)
	@RequestMapping(value = "/stocktransaction-refund", method = RequestMethod.POST)
	public ModelAndView saveInventoryTransactionRefoundProduct(HttpSession session, HttpServletRequest request, @ModelAttribute("transaction") InventoryTransaction inventoryTransaction, BindingResult result, Principal principal) {

		@SuppressWarnings("unchecked")
		List<DummyProductQty> transactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
		
		logger.info("[IN SAVE ITP] transactionItems size is: " + transactionItems.size() + " by " + principal.getName());
		ModelAndView mnv = new ModelAndView("admin.inventorytransactionproduct.refund");
		ArrayList<String> headsUpMsgs = new ArrayList<String>();

		Long inventoryTransactionId = null;
		Double totalCost = 0.0;
		Double totalSale = 0.0;

		logger.info("Inventory Transaction is: " + InventoryTransaction.TRANSACTION_TYPE.REFUND.toString());

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date dateCreated = df.parse(inventoryTransaction.getDateCreatedValue());
			inventoryTransaction.setDateCreated(dateCreated);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ModelAndView("redirect:/admin/stocktransaction-refund");
		}

		inventoryTransactionValidator.validate(inventoryTransaction, result);

		if (result.hasErrors()) {
			logger.info(result.toString());
			mnv.addObject("transaction", new InventoryTransaction());
			mnv.addObject("headsup", headsUpMsgs);
			mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String s = df.format(new Date());
			mnv.addObject("getDate", s);
			logger.info("FAIL, Entering Index Again (REFUND)");
			return mnv;
		}

		if (transactionItems.size() != 0) {

			InventoryTransaction it = new InventoryTransaction();
			it.setTransactionType(InventoryTransaction.TRANSACTION_TYPE.REFUND.toString());
			it.setReferenceNumber(inventoryTransaction.getReferenceNumber());
			User user = userManagementService.getUserByUsername(request.getUserPrincipal().getName());
			it.setUser(user);
			it.setPointPersonName(user.getUsername() + " - " + user.getFirstName());

			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dateCreated = df.parse(inventoryTransaction.getDateCreatedValue());
				it.setDateCreated(dateCreated);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return new ModelAndView("redirect:/admin/stocktransaction-refund");
			}
			it = (InventoryTransaction) inventoryTransactionService.saveInventoryTransaction(it);

			inventoryTransactionId = it.getId();

			logger.info("transaction date is " + it.getDateCreated());

			// if (it.getTransactionType().equalsIgnoreCase("INVENTORY_IN")) {
			// logger.info("[INVENTORY IN]");
			for (DummyProductQty dpq : transactionItems) {
				logger.info("SAVE DPQ TO ITP");
				InventoryTransactionProduct itp = new InventoryTransactionProduct();
				itp.setInventoryTransaction(it);
				itp.setProduct(productService.getProduct(dpq.getId()));
				itp.setProductName(productService.getProduct(dpq.getId()).getProductName());
				itp.setQty(dpq.getQty());
				// i think no need to get the product cost and srp if
				// inventory in - but overall this would work
				itp.setProductCost(-1 * inventoryService.getInventory(productService.getProduct(dpq.getId()).getInventoryId()).getCost());
				itp.setProductSale(-1 * inventoryService.getInventory(productService.getProduct(dpq.getId()).getInventoryId()).getSrp());
				itp.setCategory(productService.getProduct(dpq.getId()).getCategory());
				itp.setUom(productService.getProduct(dpq.getId()).getUnitOfMeasure());

				logger.info("Inventory transaction id: " + it.getId());
				logger.info("Inventory transaction product is: " + itp.getProduct().getProductName());
				logger.info("Inventory transaction qty is: " + itp.getQty());

				itp = (InventoryTransactionProduct) inventoryTransactionProductService.saveInventoryTransactionProduct(itp);

				totalCost = totalCost + (dpq.getQty() * inventoryService.getInventory(productService.getProduct(dpq.getId()).getInventoryId()).getCost());

				totalSale = totalSale + (dpq.getQty() * dpq.getPrice());

				Product product = (Product) productService.getProduct(dpq.getId());
				product.setTotalQty(product.getTotalQty() + dpq.getQty());
				product = productService.saveProduct(product);
				Inventory inventory = (Inventory) inventoryService.getInventory(product.getInventoryId());
				inventory.setQty(inventory.getQty() + dpq.getQty());
				inventory = inventoryService.saveInventory(inventory);
				headsUpMsgs.add("Successfully added quantity of Product: " + product.getProductName() + " - its quantity is now: " + product.getTotalQty());
			}
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());

		if (transactionItems.size() == 0) {
			logger.info("No Product to process!");
			headsUpMsgs.add("No Product for Transaction to be processed! Please add products for transaction");
			mnv.addObject("transaction", new InventoryTransaction());
			// mnv.addObject("transactionType",
			// InventoryTransaction.TRANSACTION_TYPE.values());
			mnv.addObject("headsup", headsUpMsgs);
			mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
			mnv.addObject("getDate", s);
			logger.info("ERROR!, Entering Index Again");

			transactionItems.clear();
			
			session.setAttribute("transactionItems", transactionItems);

			return mnv;

		}

		InventoryTransaction transaction = inventoryTransactionService.getInventoryTransaction(inventoryTransactionId);

		transaction.setTotalTransactionCost(totalCost *= -1);
		transaction.setTotalTransactionSale(totalSale *= -1);

		transaction = (InventoryTransaction) inventoryTransactionService.saveInventoryTransaction(transaction);

		Settings settings = settingsService.getSettings((long) 1);
		settings.setTransactionNumber(settings.getTransactionNumber() + 1);
		settings = settingsService.saveSettings(settings);
		
		mnv.addObject("transaction", new InventoryTransaction());
		mnv.addObject("headsup", headsUpMsgs);
		mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
		mnv.addObject("getDate", s);
		logger.info("Saved Transaction (REFUND), Entering Index Again" + " by " + principal.getName());

		transactionItems.clear();
		
		session.setAttribute("transactionItems", transactionItems);

		return mnv;
	}

	// edit the delete check @Transactional
	@Transactional(rollbackFor = InsufficientStockException.class)
	@RequestMapping(value = "/stocktransaction-out", method = RequestMethod.POST)
	public ModelAndView saveInventoryTransactionOutProduct(HttpSession session, HttpServletRequest request, @ModelAttribute("transaction") InventoryTransaction inventoryTransaction, BindingResult result, Principal principal) {

		@SuppressWarnings("unchecked")
		List<DummyProductQty> transactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
		
		logger.info("[IN SAVE ITP] transactionItems size is: " + transactionItems.size() + " by " + principal.getName());
		ModelAndView mnv = new ModelAndView("admin.inventorytransactionproduct.out");
		ArrayList<String> headsUpMsgs = new ArrayList<String>();

		Long inventoryTransactionId = null;
		Double totalCost = 0.0;
		Double totalSale = 0.0;

		logger.info("Inventory Transaction is: " + InventoryTransaction.TRANSACTION_TYPE.INVENTORY_OUT.toString());

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date dateCreated = df.parse(inventoryTransaction.getDateCreatedValue());
			inventoryTransaction.setDateCreated(dateCreated);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ModelAndView("redirect:/admin/stocktransaction-out");
		}

		inventoryTransactionValidator.validate(inventoryTransaction, result);

		if (result.hasErrors()) {
			logger.info(result.toString());
			mnv.addObject("transaction", new InventoryTransaction());
			mnv.addObject("headsup", headsUpMsgs);
			mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String s = df.format(new Date());
			mnv.addObject("getDate", s);
			logger.info("FAIL, Entering Index Again");
			return mnv;
		}

		if (transactionItems.size() != 0) {

			InventoryTransaction it = new InventoryTransaction();
			it.setTransactionType(InventoryTransaction.TRANSACTION_TYPE.INVENTORY_OUT.toString());
			it.setReferenceNumber(inventoryTransaction.getReferenceNumber());
			User user = userManagementService.getUserByUsername(request.getUserPrincipal().getName());
			it.setUser(user);
			it.setPointPersonName(user.getUsername() + " - " + user.getFirstName());

			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date dateCreated = df.parse(inventoryTransaction.getDateCreatedValue());
				it.setDateCreated(dateCreated);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				return new ModelAndView("redirect:/admin/stocktransaction-out");
			}
			it = (InventoryTransaction) inventoryTransactionService.saveInventoryTransaction(it);

			inventoryTransactionId = it.getId();

			logger.info("transaction date is " + it.getDateCreated());

			for (DummyProductQty dpq : transactionItems) {
				InventoryTransactionProduct itp = new InventoryTransactionProduct();
				itp.setInventoryTransaction(it);
				itp.setProduct(productService.getProduct(dpq.getId()));
				itp.setProductName(productService.getProduct(dpq.getId()).getProductName());
				itp.setQty(dpq.getQty());
				itp.setProductCost(inventoryService.getInventory(productService.getProduct(dpq.getId()).getInventoryId()).getCost());
				itp.setProductSale(inventoryService.getInventory(productService.getProduct(dpq.getId()).getInventoryId()).getSrp());
				itp.setCategory(productService.getProduct(dpq.getId()).getCategory());
				itp.setUom(productService.getProduct(dpq.getId()).getUnitOfMeasure());

				Product product = (Product) productService.getProduct(dpq.getId());

				// place inventory service here
				List<Inventory> inventoriesOfProduct = inventoryService.getInventoryByProduct(product);

				Double qtyAns = product.getTotalQty() - dpq.getQty();
				Double tempQty = dpq.getQty();
				Long id = (long) 1;
				try {
					if (qtyAns < 0) {
						headsUpMsgs.add("Pulling out this product was unsuccessful, check its QUANTITY");
						throw new InsufficientStockException();
					} else {
						product.setTotalQty(qtyAns);

						if (settingsService.getSettings(id).getStockProcess().equalsIgnoreCase("FIFO")) {

							Collections.sort(inventoriesOfProduct, new CustomComparatorAscending());

							for (Inventory inventory : inventoriesOfProduct) {
								logger.info("(AFTER-ASCENDING) date is: " + inventory.getDate().toString());

								tempQty = inventory.getQty() - Math.abs(tempQty);
								if (tempQty < 0) {
									Inventory realInventory = inventoryService.getInventory(inventory.getId());

									// placed computation here
									totalCost = totalCost + (realInventory.getCost() * realInventory.getQty());
									realInventory.setQty((double) 0);
									realInventory = inventoryService.saveInventory(realInventory);
									// place new sale price here same in
									// LIFO and else statement below.
									logger.info(totalCost.toString());

								} else {
									Inventory realInventory = inventoryService.getInventory(inventory.getId());
									Double lastInventoryQty = realInventory.getQty() - tempQty;
									totalCost = totalCost + (realInventory.getCost() * lastInventoryQty);
									realInventory.setQty(tempQty);
									realInventory = inventoryService.saveInventory(realInventory);
									break;
								}
							}
						}

						if (settingsService.getSettings(id).getStockProcess().equalsIgnoreCase("LIFO")) {
							Collections.sort(inventoriesOfProduct, new CustomComparatorDescending());

							for (Inventory inventory : inventoriesOfProduct) {
								logger.info("(AFTER-DESCENDING) date is: " + inventory.getDate().toString());

								tempQty = inventory.getQty() - Math.abs(tempQty);
								if (tempQty < 0) {
									Inventory realInventory = inventoryService.getInventory(inventory.getId());
									totalCost = totalCost + (realInventory.getCost() * realInventory.getQty());
									realInventory.setQty((double) 0);
									realInventory = inventoryService.saveInventory(realInventory);
								} else {
									Inventory realInventory = inventoryService.getInventory(inventory.getId());
									Double lastInventoryQty = realInventory.getQty() - tempQty;
									totalCost = totalCost + (realInventory.getCost() * lastInventoryQty);
									realInventory.setQty(tempQty);
									realInventory = inventoryService.saveInventory(realInventory);
									break;
								}
							}
						}

						totalSale = totalSale + (dpq.getQty() * dpq.getPrice());

						itp = (InventoryTransactionProduct) inventoryTransactionProductService.saveInventoryTransactionProduct(itp);
						headsUpMsgs.add("Successfully pulled-out the qty of Product: " + product.getProductName() + " - its quantity is now: " + product.getTotalQty());
					}
				} catch (InsufficientStockException e) {
					// TODO: handle exception
					logger.info("Unable to delete product because of qty.");
					logger.info("Entering stock transaction index - stock-out by " + principal.getName());
					
					transactionItems = new ArrayList<DummyProductQty>();
					
					session.setAttribute("transactionItems", transactionItems);
					session.setAttribute("qtyOutChecker", "true");
					
					@SuppressWarnings("unchecked")
					List<DummyProductQty> checkTransactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
					
					logger.info("get transaction items size: " + checkTransactionItems.size());
					logger.info("quantity out checker is: " + session.getAttribute("qtyOutChecker"));
					
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String s = df.format(new Date());
					ModelAndView mnv2 = new ModelAndView("admin.inventorytransactionproduct.out");
					mnv2.addObject("transaction", new InventoryTransaction());

					mnv2.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
					mnv2.addObject("getDate", s);

					mnv2.addObject("ERROR_MESSAGE", "Save Transaction unsuccessful. Kindly check if all fields are filled up.");
					
					return mnv2;
				}
			}
		} else if(transactionItems.isEmpty()){
			try{
				logger.info("No Items in the transaction items list.");
				headsUpMsgs.add("No Product for Transaction to be processed! Please add products for transaction");
				throw new InsufficientStockException();
			}
			catch(InsufficientStockException e){
				logger.info(e.getMessage());
				logger.info("Entering stock transaction index - stock-out by " + principal.getName());
				
				transactionItems = new ArrayList<DummyProductQty>();
				
				session.setAttribute("transactionItems", transactionItems);
				session.setAttribute("qtyOutChecker", "true");
				
				@SuppressWarnings("unchecked")
				List<DummyProductQty> checkTransactionItems = (ArrayList<DummyProductQty>) session.getAttribute("transactionItems");
				
				logger.info("get transaction items size: " + checkTransactionItems.size());
				logger.info("quantity out checker is: " + session.getAttribute("qtyOutChecker"));
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String s = df.format(new Date());
				ModelAndView mnv2 = new ModelAndView("admin.inventorytransactionproduct.out");
				mnv2.addObject("transaction", new InventoryTransaction());

				mnv2.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
				mnv2.addObject("getDate", s);

				mnv2.addObject("ERROR_MESSAGE", "Save Transaction unsuccessful. Kindly check if all fields are filled up.");
				
				return mnv2;
			}
		}
		
		InventoryTransaction transaction = inventoryTransactionService.getInventoryTransaction(inventoryTransactionId);

		transaction.setTotalTransactionCost(totalCost);
		transaction.setTotalTransactionSale(totalSale);

		transaction = (InventoryTransaction) inventoryTransactionService.saveInventoryTransaction(transaction);

		Settings settings = settingsService.getSettings((long) 1);
		settings.setTransactionNumber(settings.getTransactionNumber() + 1);
		settings = settingsService.saveSettings(settings);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());

		mnv.addObject("transaction", new InventoryTransaction());
		mnv.addObject("headsup", headsUpMsgs);
		mnv.addObject("referenceNumber", settingsService.getSettings((long) 1).getTransactionNumber());
		mnv.addObject("getDate", s);
		logger.info("Saved Transaction (INVENTORY-OUT), Entering Index Again" + " by " + principal.getName());

		transactionItems.clear();
		
		session.setAttribute("transactionItems", transactionItems);

		return mnv;
	}
}
