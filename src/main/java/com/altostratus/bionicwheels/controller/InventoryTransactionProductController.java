package com.altostratus.bionicwheels.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.DummyProductQty;
import com.altostratus.bionicwheels.model.Inventory;
import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.bionicwheels.model.InventoryTransactionProduct;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.service.InventoryService;
import com.altostratus.bionicwheels.service.InventoryTransactionProductService;
import com.altostratus.bionicwheels.service.InventoryTransactionService;
import com.altostratus.bionicwheels.service.ProductService;
import com.altostratus.bionicwheels.service.SettingsService;
import com.altostratus.bionicwheels.validator.InventoryTransactionValidator;
import com.altostratus.core.util.CustomComparatorAscending;
import com.altostratus.core.util.CustomComparatorDescending;
import com.altostratus.core.util.InsufficientStockException;

@Controller("inventoryTransactionProductController")
@RequestMapping("/admin")
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

	ArrayList<DummyProductQty> arrayList;

	private Logger logger = LoggerFactory
			.getLogger(InventoryTransactionProductController.class);

	@RequestMapping(value = "/stocktransaction")
	public ModelAndView stockTransactionIndex(HttpServletRequest request) {
		logger.info("Entering stock transaction index");
		arrayList = new ArrayList<DummyProductQty>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());
		ModelAndView mnv = new ModelAndView(
				"admin.inventorytransactionproduct.index");
		mnv.addObject("transaction", new InventoryTransaction());
		mnv.addObject("transactionType",
				InventoryTransaction.TRANSACTION_TYPE.values());
		mnv.addObject("referenceNumber", inventoryTransactionService
				.getAllInventoryTransactions().size() + 1);
		mnv.addObject("getDate", s);
		return mnv;
	}

	@RequestMapping(value = "/getproductintransaction/{id}")
	public @ResponseBody
	ArrayList<String> getProductInTransaction(@PathVariable("id") Long id) {
		Product product = productService.getProduct(id);
		logger.info("Product is " + product);

		if (product != null) {
			ArrayList<String> productInfo = new ArrayList<String>();
			productInfo.add(product.getProductName());
			productInfo.add(product.getCode());
			productInfo.add(product.getTotalQty().toString());
			productInfo.add(product.getUnitOfMeasure().toString());

			return productInfo;
		} else {
			ArrayList<String> productInfo = new ArrayList<String>();
			productInfo.add("NO PRODUCT FOUND!");
			productInfo.add("NO PRODUCT CODE!");
			productInfo.add("NO QUANTITY FOUND!");
			productInfo.add("NO UNIT OF MEASURE FOUND!");
			return productInfo;
		}
	}

	// used AJAX for array updating
	@RequestMapping(value = "/addtransactionproduct/{id}/{qty}/")
	public @ResponseBody
	String addTransactionProductToArrayList(@PathVariable("id") Long id,
			@PathVariable("qty") Double qty) {
		// Real Product
		Product product = productService.getProduct(id);
		boolean checker = false;

		if (product != null) {

			logger.info("ArrayList size is: " + arrayList.size());

			if (arrayList.size() == 0) {

				// Dummy product
				DummyProductQty dpq = new DummyProductQty();
				dpq.setId(id);
				dpq.setQty(qty);

				arrayList.add(dpq);
				logger.info("START UP: ARRAY LIST SIZE IS " + arrayList.size());

			} else {
				for (DummyProductQty dummyProductQty : arrayList) {

					if (dummyProductQty.getId() == id) {
						dummyProductQty.setQty(dummyProductQty.getQty() + qty);
						logger.info("ID is: " + dummyProductQty.getId());
						logger.info("QTY is: " + dummyProductQty.getQty()
								+ " and quantity is updated");
						checker = true;
					}
				}
				if (checker == false) {
					DummyProductQty dpq = new DummyProductQty();
					dpq.setId(id);
					dpq.setQty(qty);

					arrayList.add(dpq);
					logger.info("new product ID is: " + dpq.getId());
					logger.info("new product QTY is: " + dpq.getQty());
					logger.info("array list size is: " + arrayList.size());
				}
			}

			return "Product was Added!";

		} else {
			logger.info("PRODUCT IS NULL");
			return "null";

		}
	}

	@RequestMapping(value = "/deleteproductintransaction/{id}/{qty}/")
	public @ResponseBody
	void deleteTransactionProductToArrayList(@PathVariable("id") Long id,
			@PathVariable("qty") Double qty) {
		logger.info("delete product id: " + id + " with quantity: " + qty);
		for (DummyProductQty dpq : arrayList) {
			if (dpq.getId() == id) {
				logger.info("DUMMY PRODUCT ID IS: " + dpq.getId()
						+ " DUMMY PRODUCT QTY IS: " + dpq.getQty());
				logger.info("does contain? " + arrayList.contains(dpq));
				arrayList.remove(dpq);
				logger.info("arraylist size is: " + arrayList.size());
			}
		}
	}

	// edit the delete check @Transactional
	// @Transactional(rollbackFor = InsufficientStockException.class)
	@RequestMapping(value = "/stocktransaction", method = RequestMethod.POST)
	public ModelAndView saveInventoryTransactionProduct(
			HttpServletRequest request,
			@ModelAttribute("transaction") InventoryTransaction inventoryTransaction,
			BindingResult result) {
		logger.info("[IN SAVE ITP] arraylist size is: " + arrayList.size());
		ModelAndView mnv = new ModelAndView(
				"admin.inventorytransactionproduct.index");
		ArrayList<String> headsUpMsgs = new ArrayList<String>();

		logger.info("Inventory Transaction is: "
				+ inventoryTransaction.getTransactionType());

		// logger.info("Inventory Transaction's DATE is: "
		// + inventoryTransaction.getDateCreated());

		// try {
		// logger.info("entering try catch");
		// String stringDate = inventoryTransaction.getDateCreated()
		// .toString();
		// DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// Date date = (Date) formatter.parse(stringDate);
		// inventoryTransaction.setDateCreated(date);
		// logger.info("Inventory DATE: " + date);
		// } catch (Exception e) {
		// logger.info("error");
		// }

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date dateCreated = df.parse(inventoryTransaction
					.getDateCreatedValue());
			inventoryTransaction.setDateCreated(dateCreated);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return new ModelAndView("redirect:/admin/stocktransaction");
		}

		inventoryTransactionValidator.validate(inventoryTransaction, result);

		if (result.hasErrors()) {
			logger.info(result.toString());
			mnv.addObject("transaction", new InventoryTransaction());
			mnv.addObject("transactionType",
					InventoryTransaction.TRANSACTION_TYPE.values());
			mnv.addObject("headsup", headsUpMsgs);
			mnv.addObject("referenceNumber", inventoryTransactionService
					.getAllInventoryTransactions().size());
			mnv.addObject("getDate", new Date());
			logger.info("FAIL, Entering Index Again");
			return mnv;
		}

		if (arrayList.size() != 0) {

			InventoryTransaction it = (InventoryTransaction) inventoryTransactionService
					.saveInventoryTransaction(inventoryTransaction);

			logger.info("transaction date is " + it.getDateCreated());

			if (it.getTransactionType().equalsIgnoreCase("INVENTORY_IN")) {
				logger.info("[INVENTORY IN]");
				for (DummyProductQty dpq : arrayList) {
					logger.info("SAVE DPQ TO ITP");
					InventoryTransactionProduct itp = new InventoryTransactionProduct();
					itp.setInventoryTransaction(it);
					itp.setProduct(productService.getProduct(dpq.getId()));
					itp.setProductName(productService.getProduct(dpq.getId())
							.getProductName());
					itp.setQty(dpq.getQty());

					logger.info("Inventory transaction id: " + it.getId());
					logger.info("Inventory transaction product is: "
							+ itp.getProduct().getProductName());
					logger.info("Inventory transaction qty is: " + itp.getQty());

					itp = (InventoryTransactionProduct) inventoryTransactionProductService
							.saveInventoryTransactionProduct(itp);
					Product product = (Product) productService.getProduct(dpq
							.getId());
					product.setTotalQty(product.getTotalQty() + dpq.getQty());
					product = productService.saveProduct(product);
					Inventory inventory = (Inventory) inventoryService
							.getInventory(product.getInventoryId());
					inventory.setQty(inventory.getQty() + dpq.getQty());
					inventory = inventoryService.saveInventory(inventory);
					headsUpMsgs.add("Successfully added quantity of Product: "
							+ product.getProductName()
							+ " - its quantity is now: "
							+ product.getTotalQty());
				}
			} else if (it.getTransactionType()
					.equalsIgnoreCase("INVENTORY_OUT")) {
				for (DummyProductQty dpq : arrayList) {
					InventoryTransactionProduct itp = new InventoryTransactionProduct();
					itp.setInventoryTransaction(it);
					itp.setProduct(productService.getProduct(dpq.getId()));
					itp.setProductName(productService.getProduct(dpq.getId())
							.getProductName());
					itp.setQty(dpq.getQty());

					itp = (InventoryTransactionProduct) inventoryTransactionProductService
							.saveInventoryTransactionProduct(itp);
					Product product = (Product) productService.getProduct(dpq
							.getId());

					// place inventory service here
					List<Inventory> inventoriesOfProduct = inventoryService
							.getInventoryByProduct(product);

					Double qtyAns = product.getTotalQty() - dpq.getQty();
					Double tempQty = dpq.getQty();
					Long id = (long) 1;
					try {
						if (qtyAns < 0) {
							headsUpMsgs
									.add("Deleting this product was unsuccessful, check its QUANTITY");
							throw new InsufficientStockException();
						} else {
							product.setTotalQty(qtyAns);

							if (settingsService.getSettings(id)
									.getStockProcess() == "FIFO") {

								Collections.sort(inventoriesOfProduct,
										new CustomComparatorAscending());

								for (Inventory inventory : inventoriesOfProduct) {
									logger.info("(AFTER-ASCENDING) date is: "
											+ inventory.getDate().toString());

									tempQty = inventory.getQty()
											- Math.abs(tempQty);
									if (tempQty < 0) {
										Inventory realInventory = inventoryService
												.getInventory(inventory.getId());
										realInventory.setQty((double) 0);
										realInventory = inventoryService
												.saveInventory(realInventory);
									} else {
										Inventory realInventory = inventoryService
												.getInventory(inventory.getId());
										realInventory.setQty(tempQty);
										realInventory = inventoryService
												.saveInventory(realInventory);
										break;
									}
								}
							} else {
								Collections.sort(inventoriesOfProduct,
										new CustomComparatorDescending());

								for (Inventory inventory : inventoriesOfProduct) {
									logger.info("(AFTER-DESCENDING) date is: "
											+ inventory.getDate().toString());

									tempQty = inventory.getQty()
											- Math.abs(tempQty);
									if (tempQty < 0) {
										Inventory realInventory = inventoryService
												.getInventory(inventory.getId());
										realInventory.setQty((double) 0);
										realInventory = inventoryService
												.saveInventory(realInventory);
									} else {
										Inventory realInventory = inventoryService
												.getInventory(inventory.getId());
										realInventory.setQty(tempQty);
										realInventory = inventoryService
												.saveInventory(realInventory);
										break;
									}
								}
							}

							headsUpMsgs
									.add("Successfully deleted the qty of Product: "
											+ product.getProductName()
											+ " - its quantity is now: "
											+ product.getTotalQty());
						}
					} catch (InsufficientStockException e) {
						// TODO: handle exception
						logger.info("Unable to delete product because of qty.");
					}
				}
			}
		} else {
			headsUpMsgs
					.add("No Product for Transaction to be processed! Please add products for transaction");
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = df.format(new Date());

		mnv.addObject("transaction", new InventoryTransaction());
		mnv.addObject("transactionType",
				InventoryTransaction.TRANSACTION_TYPE.values());
		mnv.addObject("headsup", headsUpMsgs);
		mnv.addObject("referenceNumber", inventoryTransactionService
				.getAllInventoryTransactions().size());
		mnv.addObject("getDate", s);
		logger.info("Saved, Entering Index Again");
		return mnv;
		// return new ModelAndView("redirect:/admin/stocktransaction");
	}
}
