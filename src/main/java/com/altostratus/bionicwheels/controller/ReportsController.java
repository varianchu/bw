package com.altostratus.bionicwheels.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.model.Inventory;
import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.bionicwheels.model.InventoryTransactionProduct;
import com.altostratus.bionicwheels.model.LineItemProductReport;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.model.ServiceRendered;
import com.altostratus.bionicwheels.model.ServiceTransaction;
import com.altostratus.bionicwheels.model.ServiceTransactionItem;
import com.altostratus.bionicwheels.model.TransactionsWithinDateCategory;
import com.altostratus.bionicwheels.model.TransactionsWithinDateUser;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.CategoryService;
import com.altostratus.bionicwheels.service.InventoryService;
import com.altostratus.bionicwheels.service.InventoryTransactionService;
import com.altostratus.bionicwheels.service.ProductService;
import com.altostratus.bionicwheels.service.ServiceRenderedService;
import com.altostratus.bionicwheels.service.ServiceTransactionService;
import com.altostratus.bionicwheels.service.SettingsService;
import com.altostratus.core.model.User;
import com.altostratus.core.service.UserManagementService;
import com.altostratus.core.util.CustomComparatorQtyDescending;
import com.altostratus.core.util.CustomComparatorServiceDescending;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = { "/admin/reports" })
public class ReportsController {
	@Autowired
	UserManagementService userManagementService;

	@Autowired
	ProductService productService;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	BrandService brandService;

	@Autowired
	SettingsService settingsService;

	@Autowired
	InventoryTransactionService inventoryTransactionService;

	@Autowired
	ServiceTransactionService serviceTransactionService;

	@Autowired
	ServiceRenderedService serviceRenderedService;

	private Logger logger = LoggerFactory.getLogger(DefaultController.class);

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public ModelAndView reportsMenu(HttpServletRequest request,
			Principal principal) {
		logger.info("Entering reports menu by " + principal.getName());

		ModelAndView mnv = new ModelAndView("admin.reports.index");
		mnv.addObject("dateToday", new Date());
		mnv.addObject("transactionDatesCategoryIn",
				new TransactionsWithinDateCategory());
		mnv.addObject("inventoryCount", new TransactionsWithinDateCategory());
		mnv.addObject("inventoryCountBrand",
				new TransactionsWithinDateCategory());
		mnv.addObject("transactionDatesSummaryCategory",
				new TransactionsWithinDateCategory());
		mnv.addObject("transactionDatesRankingInventoryCategory",
				new TransactionsWithinDateCategory());
		mnv.addObject("transactionDatesUser", new TransactionsWithinDateUser());
		mnv.addObject("transactionDatesService",
				new TransactionsWithinDateUser());
		mnv.addObject("categories", categoryService.getAllCategories());
		mnv.addObject("brands", brandService.getAllBrands());
		mnv.addObject("users", userManagementService.getBasicUsers());
		return mnv;
	}

	@RequestMapping(value = "/report-category-purchases", method = RequestMethod.POST)
	public ModelAndView viewPurchasesByCategoryReport(
			HttpServletRequest request,
			@ModelAttribute("transactionDatesCategoryIn") TransactionsWithinDateCategory transactionsWithinDateCategory,
			BindingResult result, Principal principal) {

		logger.info("report accessed by " + principal.getName());

		ModelAndView mnv = new ModelAndView("admin.report.category.purchases");

		String date1 = transactionsWithinDateCategory.getDate1();
		String date2 = transactionsWithinDateCategory.getDate2();
		Long id = transactionsWithinDateCategory.getCategoryId();
		Double totalPurchasesCost = 0.0;
		Double totalPurchasesQty = 0.0;

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);

			List<InventoryTransactionProduct> itps = inventoryTransactionService
					.getAllInventoryTransactionProductsWithinDateByCategoryInventoryIn(
							startDate, endDate, id);

			List<LineItemProductReport> lineItemProducts = new ArrayList<LineItemProductReport>();

			List<String> stringProducts = new ArrayList<String>();

			for (InventoryTransactionProduct inventoryTransactionProduct : itps) {
				stringProducts.add(inventoryTransactionProduct.getProduct()
						.getId()
						+ "---"
						+ inventoryTransactionProduct.getProductCost());
			}

			HashSet<String> hs = new HashSet<String>();
			hs.addAll(stringProducts);
			stringProducts.clear();
			stringProducts.addAll(hs);

			for (String string : stringProducts) {
				String[] lineItemSplit = string.split("---");
				LineItemProductReport lipr = new LineItemProductReport();
				lipr.setProduct(productService.getProduct(Long
						.parseLong(lineItemSplit[0])));
				Double cost = Double.parseDouble(lineItemSplit[1]);
				lipr.setCostOfGood(cost);

				lineItemProducts.add(lipr);
			}

			for (InventoryTransactionProduct inventoryTransactionProduct : itps) {
				for (LineItemProductReport lineItemProduct : lineItemProducts) {
					if (lineItemProduct.getProduct() == inventoryTransactionProduct
							.getProduct()
							&& lineItemProduct
									.getCostOfGood()
									.toString()
									.equals(inventoryTransactionProduct
											.getProductCost().toString())) {
						Double updatedQty = lineItemProduct.getQty()
								+ inventoryTransactionProduct.getQty();
						lineItemProduct.setQty(updatedQty);
						break;
						// maybe this one is not needed, i included this because
						// view is not showing.
						// Double totalCOG = updatedQty *
						// inventoryTransactionProduct.getProductCost();
						// lineItemProduct.setTotalCostOfGood(totalCOG);
					}
				}
			}

			for (LineItemProductReport lineItemProductReport : lineItemProducts) {
				totalPurchasesCost = (lineItemProductReport.getCostOfGood() * lineItemProductReport
						.getQty()) + totalPurchasesCost;
				totalPurchasesQty = lineItemProductReport.getQty()
						+ totalPurchasesQty;
			}

			DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

			mnv.addObject("date1", sdf.format(startDate));
			mnv.addObject("date2", sdf.format(endDate));
			mnv.addObject("lineItemProducts", lineItemProducts);
			mnv.addObject("category", categoryService.getCategory(id));
			mnv.addObject("totalPurchasesCost", totalPurchasesCost);
			mnv.addObject("totalPurchasesQty", totalPurchasesQty);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			// mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject(
					"ERROR_MESSAGE",
					"There were no transactions found with the specified dates or Date format exception.");
			return mnv;
		}
		return mnv;
	}

	@RequestMapping(value = "/report-category-inventory", method = RequestMethod.POST)
	public ModelAndView viewTotalInventoryCountByCategoryReport(
			HttpServletRequest request,
			@ModelAttribute("inventoryCount") TransactionsWithinDateCategory transactionsWithinDateCategory,
			BindingResult result, Principal principal) {

		logger.info("report accessed by " + principal.getName());

		ModelAndView mnv = new ModelAndView("admin.report.category.inventory");

		Long id = transactionsWithinDateCategory.getCategoryId();
		Category category = categoryService.getCategory(id);
		Double totalQty = 0.0;
		Double totalCostOfGoodsInStock = 0.0;

		List<LineItemProductReport> lipr = new ArrayList<LineItemProductReport>();

		try {
			List<Product> products = productService
					.getProductsByCategory(category);

			for (Product product : products) {
				totalQty = totalQty + product.getTotalQty();
				for (Inventory inventory : product.getInventories()) {

					int retval = Double.compare(inventory.getQty(), 0.0);

					if (retval != 0) {
						totalCostOfGoodsInStock = totalCostOfGoodsInStock
								+ (inventory.getQty() * inventory.getCost());

						LineItemProductReport lineItemProductReport = new LineItemProductReport();
						lineItemProductReport.setProduct(product);
						lineItemProductReport
								.setCostOfGood(inventory.getCost());
						lineItemProductReport.setQty(inventory.getQty());

						lipr.add(lineItemProductReport);
						break;
					}
				}
			}

			DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

			mnv.addObject("lineItemProducts", lipr);
			mnv.addObject("date", sdf.format(new Date()));
			logger.info(sdf.format(new Date()));
			mnv.addObject("totalQty", totalQty);
			mnv.addObject("totalCostOfGoodsInStock", totalCostOfGoodsInStock);
			mnv.addObject("category", categoryService.getCategory(id));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("ERROR_MESSAGE", "Something went wrong.");
			return mnv;
		}
		return mnv;
	}

	// new June 25, 2014
	@RequestMapping(value = "/report-brand-inventory", method = RequestMethod.POST)
	public ModelAndView viewTotalInventoryCountByBrandReport(
			HttpServletRequest request,
			@ModelAttribute("inventoryCountBrand") TransactionsWithinDateCategory transactionsWithinDateCategory,
			BindingResult result, Principal principal) {

		logger.info("report accessed by " + principal.getName());

		ModelAndView mnv = new ModelAndView("admin.report.brand.inventory");

		Long id = transactionsWithinDateCategory.getBrandId();
		Brand brand = brandService.getBrand(id);
		Double totalQty = 0.0;
		Double totalCostOfGoodsInStock = 0.0;

		List<LineItemProductReport> lipr = new ArrayList<LineItemProductReport>();

		try {
			List<Product> products = productService.getProductsByBrand(brand);

			for (Product product : products) {
				totalQty = totalQty + product.getTotalQty();
				for (Inventory inventory : product.getInventories()) {

					int retval = Double.compare(inventory.getQty(), 0.0);

					if (retval != 0) {
						totalCostOfGoodsInStock = totalCostOfGoodsInStock
								+ (inventory.getQty() * inventory.getCost());

						LineItemProductReport lineItemProductReport = new LineItemProductReport();
						lineItemProductReport.setProduct(product);
						lineItemProductReport
								.setCostOfGood(inventory.getCost());
						lineItemProductReport.setQty(inventory.getQty());

						lipr.add(lineItemProductReport);
						break;
					}
				}
			}

			DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

			mnv.addObject("lineItemProducts", lipr);
			mnv.addObject("date", sdf.format(new Date()));
			logger.info(sdf.format(new Date()));
			mnv.addObject("totalQty", totalQty);
			mnv.addObject("totalCostOfGoodsInStock", totalCostOfGoodsInStock);
			mnv.addObject("brand", brandService.getBrand(id));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("ERROR_MESSAGE", "Something went wrong.");
			return mnv;
		}
		return mnv;
	}

	@RequestMapping(value = "/report-category-summary", method = RequestMethod.POST)
	public ModelAndView viewTotalPurchasesAndSaleByCategoryReport(
			HttpServletRequest request,
			@ModelAttribute("transactionDatesSummaryCategory") TransactionsWithinDateCategory transactionsWithinDateCategory,
			BindingResult result, Principal principal) {

		logger.info("report accessed by " + principal.getName());

		ModelAndView mnv = new ModelAndView("admin.report.category.summary");

		String date1 = transactionsWithinDateCategory.getDate1();
		String date2 = transactionsWithinDateCategory.getDate2();
		Long id = transactionsWithinDateCategory.getCategoryId();
		Double summaryTotalCOGS = 0.0;
		Double summaryTotalSales = 0.0;
		Double summaryTotalProfit = 0.0;
		Double summaryTotalPurchasesCost = 0.0;
		Double summaryTotalNetProfit = 0.0;

		// List<LineItemProductReport> lipr = new
		// ArrayList<LineItemProductReport>();

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);

			List<InventoryTransactionProduct> inventoryTransactionProducts = inventoryTransactionService
					.getAllInventoryTransactionProductsWithinDateByCategory(
							startDate, endDate, id);
			// List<InventoryTransaction> inventoryTransactions =
			// inventoryTransactionService.getAllInventoryTransactionsWithinDate(startDate,
			// endDate);
			// List<Product> products =
			// productService.getProductsByCategory(categoryService.getCategory(id));

			List<LineItemProductReport> liprs = new ArrayList<LineItemProductReport>();

			List<String> stringProducts = new ArrayList<String>();

			for (InventoryTransactionProduct inventoryTransactionProduct : inventoryTransactionProducts) {
				stringProducts.add(inventoryTransactionProduct.getProduct()
						.getId()
						+ "---"
						+ inventoryTransactionProduct.getProductCost());
			}

			HashSet<String> hs = new HashSet<String>();
			hs.addAll(stringProducts);
			stringProducts.clear();
			stringProducts.addAll(hs);

			for (String string : stringProducts) {
				String[] lineItemSplit = string.split("---");
				LineItemProductReport lineItemProduct = new LineItemProductReport();
				lineItemProduct.setProduct(productService.getProduct(Long
						.parseLong(lineItemSplit[0])));
				Double cost = Double.parseDouble(lineItemSplit[1]);
				lineItemProduct.setCostOfGood(cost);

				liprs.add(lineItemProduct);
			}

			for (InventoryTransactionProduct inventoryTransactionProduct : inventoryTransactionProducts) {
				for (LineItemProductReport lipr : liprs) {
					if (lipr.getProduct().equals(
							inventoryTransactionProduct.getProduct())
							&& lipr.getCostOfGood()
									.toString()
									.equals(inventoryTransactionProduct
											.getProductCost().toString())) {
						if (inventoryTransactionProduct
								.getInventoryTransaction()
								.getTransactionType()
								.equalsIgnoreCase(
										InventoryTransaction.TRANSACTION_TYPE.INVENTORY_IN
												.toString())) {
							Double qty = lipr.getQtyIn()
									+ inventoryTransactionProduct.getQty();
							Double totalPurchaseCost = qty
									* inventoryTransactionProduct
											.getProductCost();
							Double netProfit = lipr.getTotalProfit()
									- totalPurchaseCost;
							lipr.setQtyIn(qty);
							lipr.setTotalPurchaseCost(totalPurchaseCost);
							lipr.setNetProfit(netProfit);

							qty = 0.0;
							totalPurchaseCost = 0.0;
							netProfit = 0.0;
						} else if (inventoryTransactionProduct
								.getInventoryTransaction()
								.getTransactionType()
								.equalsIgnoreCase(
										InventoryTransaction.TRANSACTION_TYPE.INVENTORY_OUT
												.toString())) {
							Double qty = lipr.getQtyOut()
									+ inventoryTransactionProduct.getQty();
							Double totalCOGS = lipr.getTotalCostOfGoodSold()
									+ (inventoryTransactionProduct.getQty() * inventoryTransactionProduct
											.getProductCost());
							Double totalSRP = lipr.getTotalSRP()
									+ (inventoryTransactionProduct.getQty() * inventoryTransactionProduct
											.getProductSale());
							logger.info(totalSRP.toString());
							Double totalProfit = totalSRP - totalCOGS;
							Double netProfit = totalProfit
									- lipr.getTotalPurchaseCost();
							lipr.setQtyOut(qty);
							lipr.setTotalSRP(totalSRP);
							lipr.setTotalCostOfGoodSold(totalCOGS);
							lipr.setTotalProfit(totalProfit);
							lipr.setNetProfit(netProfit);

							qty = 0.0;
							totalCOGS = 0.0;
							totalSRP = 0.0;
							totalProfit = 0.0;
							netProfit = 0.0;
						}
					}
				}
			}

			for (LineItemProductReport lipr : liprs) {
				summaryTotalPurchasesCost += lipr.getTotalPurchaseCost();
				summaryTotalCOGS += lipr.getTotalCostOfGoodSold();
				summaryTotalSales += lipr.getTotalSRP();
			}

			summaryTotalProfit = summaryTotalSales - summaryTotalCOGS;
			summaryTotalNetProfit = summaryTotalProfit
					- summaryTotalPurchasesCost;

			DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

			mnv.addObject("date1", sdf.format(startDate));
			mnv.addObject("date2", sdf.format(endDate));
			mnv.addObject("category", categoryService.getCategory(id));
			mnv.addObject("lineItemProducts", liprs);
			mnv.addObject("summaryTotalPurchasesCost",
					summaryTotalPurchasesCost);
			mnv.addObject("summaryTotalCOGS", summaryTotalCOGS);
			mnv.addObject("summaryTotalSales", summaryTotalSales);
			mnv.addObject("summaryTotalProfit", summaryTotalProfit);
			mnv.addObject("summaryNetProfit", summaryTotalNetProfit);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("ERROR_MESSAGE", "Something went wrong.");
			return mnv;
		}
		return mnv;
	}

	@RequestMapping(value = "/report-category-rank", method = RequestMethod.POST)
	public ModelAndView viewTotalSaleByCategoryReport(
			HttpServletRequest request,
			@ModelAttribute("transactionDatesRankingInventoryCategory") TransactionsWithinDateCategory transactionsWithinDateCategory,
			BindingResult result, Principal principal) {

		logger.info("report accessed by " + principal.getName());

		ModelAndView mnv = new ModelAndView("admin.report.category.rank");

		String date1 = transactionsWithinDateCategory.getDate1();
		String date2 = transactionsWithinDateCategory.getDate2();
		Long id = transactionsWithinDateCategory.getCategoryId();

		List<LineItemProductReport> liprs = new ArrayList<LineItemProductReport>();

		try {

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);
			List<Long> longProducts = new ArrayList<Long>();

			List<InventoryTransactionProduct> inventoryTransactionProducts = inventoryTransactionService
					.getAllInventoryTransactionProductsWithinDateByCategoryInventoryOut(
							startDate, endDate, id);

			for (InventoryTransactionProduct inventoryTransactionProduct : inventoryTransactionProducts) {
				longProducts.add(inventoryTransactionProduct.getProduct()
						.getId());
			}

			// i believe this is not needed
			HashSet<Long> hs = new HashSet<Long>();
			hs.addAll(longProducts);
			longProducts.clear();
			longProducts.addAll(hs);

			for (Long longProduct : longProducts) {
				LineItemProductReport lipr = new LineItemProductReport();
				lipr.setProduct(productService.getProduct(longProduct));

				liprs.add(lipr);
			}

			for (InventoryTransactionProduct itp : inventoryTransactionProducts) {
				for (LineItemProductReport lipr : liprs) {
					if (lipr.getProduct().equals(itp.getProduct())) {
						lipr.setQtyOut(itp.getQty() + lipr.getQtyOut());
						break;
					}
				}
			}

			Collections.sort(liprs, new CustomComparatorQtyDescending());

			DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

			logger.info("SUCCESS!");

			mnv.addObject("date1", sdf.format(startDate));
			mnv.addObject("date2", sdf.format(endDate));
			mnv.addObject("lineItemProducts", liprs);
			mnv.addObject("category", categoryService.getCategory(id));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("ERROR_MESSAGE", "Something went wrong.");
			return mnv;
		}
		return mnv;
	}

	@RequestMapping(value = "/report-category-user-transactions", method = RequestMethod.POST)
	public ModelAndView viewTransactionsByCategoryUserReport(
			HttpServletRequest request,
			@ModelAttribute("transactionDatesUser") TransactionsWithinDateUser transactionsWithinDateUser,
			BindingResult result, Principal principal) {

		logger.info("report accessed by " + principal.getName());

		ModelAndView mnv = new ModelAndView("admin.report.category.user");

		String date1 = transactionsWithinDateUser.getDate1();
		String date2 = transactionsWithinDateUser.getDate2();
		User user = userManagementService.getUser(transactionsWithinDateUser
				.getUserId());

		Double totalCost = 0.0;
		Double totalSRP = 0.0;
		Double totalPurchases = 0.0;

		try {

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);

			List<InventoryTransaction> inventoryTransactions = inventoryTransactionService
					.getAllInventoryTransactionsWithinDateByUser(user,
							startDate, endDate);
			List<InventoryTransactionProduct> inventoryTransactionProductsPurchases = new ArrayList<InventoryTransactionProduct>();
			List<InventoryTransactionProduct> inventoryTransactionProducts = new ArrayList<InventoryTransactionProduct>();
			List<LineItemProductReport> liprs = new ArrayList<LineItemProductReport>();

			for (InventoryTransaction inventoryTransaction : inventoryTransactions) {
				if (inventoryTransaction.getTransactionType().equalsIgnoreCase(
						InventoryTransaction.TRANSACTION_TYPE.INVENTORY_IN
								.toString())) {
					inventoryTransactionProductsPurchases
							.addAll(inventoryTransaction
									.getInventoryTransactionProducts());
				} else {
					inventoryTransactionProducts.addAll(inventoryTransaction
							.getInventoryTransactionProducts());
				}
			}

			for (InventoryTransactionProduct inventoryTransactionProduct : inventoryTransactionProductsPurchases) {

				LineItemProductReport lipr = new LineItemProductReport();
				lipr.setProduct(inventoryTransactionProduct.getProduct());
				Double totalCOGS = inventoryTransactionProduct.getProductCost()
						* inventoryTransactionProduct.getQty();
				lipr.setTotalCostOfGoodSold(totalCOGS);
				lipr.setTotalSRP(0.0);
				lipr.setTotalProfit(0.0);
				lipr.setQty(inventoryTransactionProduct.getQty());

				liprs.add(lipr);

				totalPurchases += totalCOGS;
			}

			for (InventoryTransactionProduct inventoryTransactionProduct : inventoryTransactionProducts) {

				totalCost += inventoryTransactionProduct.getProductCost()
						* inventoryTransactionProduct.getQty();
				totalSRP += inventoryTransactionProduct.getProductSale()
						* inventoryTransactionProduct.getQty();

				LineItemProductReport lipr = new LineItemProductReport();
				lipr.setProduct(inventoryTransactionProduct.getProduct());
				Double totalCOGS = inventoryTransactionProduct.getProductCost()
						* inventoryTransactionProduct.getQty();
				Double totalSale = inventoryTransactionProduct.getProductSale()
						* inventoryTransactionProduct.getQty();
				lipr.setTotalCostOfGoodSold(totalCOGS);
				lipr.setTotalSRP(totalSale);
				lipr.setTotalProfit(totalSale - totalCOGS);
				lipr.setQty(inventoryTransactionProduct.getQty());

				liprs.add(lipr);
			}

			logger.info("USER REPORT FILTER SUCCESS! - ITP size: "
					+ inventoryTransactionProducts.size());

			DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

			mnv.addObject("date1", sdf.format(startDate));
			mnv.addObject("date2", sdf.format(endDate));
			mnv.addObject("lineItemProducts", liprs);
			mnv.addObject("totalCost", totalCost);
			mnv.addObject("totalSRP", totalSRP);
			mnv.addObject("totalProfit", totalSRP - totalCost);
			mnv.addObject("totalPurchases", totalPurchases);
			mnv.addObject("user", user);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("ERROR_MESSAGE", "Something went wrong.");
			return mnv;
		}
		return mnv;
	}

	// new july 12
	@RequestMapping(value = "/report-service-transactions", method = RequestMethod.POST)
	public ModelAndView viewTransactionsByServices(HttpServletRequest request, @ModelAttribute("transactionDatesService") TransactionsWithinDateCategory transactionsWithinDateCategory, BindingResult result, Principal principal) {

		logger.info("service report accessed by " + principal.getName());

		ModelAndView mnv = new ModelAndView("admin.report.service.transactions");

		String date1 = transactionsWithinDateCategory.getDate1();
		String date2 = transactionsWithinDateCategory.getDate2();

		List<LineItemProductReport> liprs = new ArrayList<LineItemProductReport>();
		List<LineItemProductReport> liprsBuffer = new ArrayList<LineItemProductReport>();
		List<ServiceTransaction> serviceTransactions = new ArrayList<ServiceTransaction>();
		List<ServiceTransactionItem> serviceTransactionItems = new ArrayList<ServiceTransactionItem>();
		Double totalProfit = 0.0;
		Double totalSale = 0.0;

		try {

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = df.parse(date1);
			Date endDate = df.parse(date2);

			serviceTransactions = serviceTransactionService.getServiceTransactionsByDateBetween(startDate, endDate);

			for (ServiceTransaction serviceTransaction : serviceTransactions) {
				serviceTransactionItems.addAll(serviceTransaction.getServiceTransactionItems());
			}

			for (ServiceRendered serviceRendered : serviceRenderedService.getAllServiceRendered()) {
				
				LineItemProductReport lineItemBuffer = new LineItemProductReport();
				lineItemBuffer.setServiceMadePart(serviceRendered.getServiceMadePart());
				lineItemBuffer.setQty(0.0);
				lineItemBuffer.setTotalProfit(0.0);
				lineItemBuffer.setTotalSRP(0.0);

				liprsBuffer.add(lineItemBuffer);

//				logger.info(lineItemBuffer.getServiceMadePart());
			}

			for (ServiceTransactionItem sti : serviceTransactionItems) {
				for (LineItemProductReport lipr : liprsBuffer) {
					
					if (sti.getServiceMadePart().equalsIgnoreCase(lipr.getServiceMadePart())) {

						// LineItemProductReport lineItemProductReport = new
						// LineItemProductReport();
						lipr.setQty(lipr.getQty() + 1);
						lipr.setTotalProfit(lipr.getTotalProfit() + sti.getServiceProfit());
						lipr.setTotalSRP(lipr.getTotalSRP() + sti.getServicePrice());
						totalProfit += sti.getServiceProfit();
						totalSale += sti.getServicePrice();

						break;
					}
				}
			}

			Collections.sort(liprsBuffer, new CustomComparatorServiceDescending());
			// ended here

			// Collections.sort(liprs, new CustomComparatorQtyDescending());

			DateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

			logger.info("SUCCESS!");

			mnv.addObject("date1", sdf.format(startDate));
			mnv.addObject("date2", sdf.format(endDate));
			mnv.addObject("lineItemProducts", liprsBuffer);
			mnv.addObject("summaryTotalSales", totalSale);
			mnv.addObject("summaryTotalProfit", totalProfit);
			// mnv.addObject("category", categoryService.getCategory(id));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			mnv.addObject("users", userManagementService.getBasicUsers());
			mnv.addObject("ERROR_MESSAGE", "Something went wrong.");
			return mnv;
		}
		return mnv;

	}

//	@RequestMapping(value = "/test", method = RequestMethod.GET)
//	public ModelAndView viewTest(HttpServletRequest request) {
//
//		logger.info("TEST VIEW");
//
//		ModelAndView mnv = new ModelAndView("test");
//
//		List<ServiceTransactionItem> serviceTransactionItems = new ArrayList<ServiceTransactionItem>();
//		List<String> serviceRenderedBuffer = new ArrayList<String>();
//
//		for (ServiceTransaction serviceTransaction : serviceTransactionService
//				.getAllServiceTransactions()) {
//			serviceTransactionItems.addAll(serviceTransaction
//					.getServiceTransactionItems());
//		}
//
//		for (ServiceTransactionItem serviceTransactionItem : serviceTransactionItems) {
//			serviceRenderedBuffer.add(serviceTransactionItem.getServiceMadePart());
//		}
//
//		HashSet<String> hs = new HashSet<String>();
//		hs.addAll(serviceRenderedBuffer);
//		serviceRenderedBuffer.clear();
//		serviceRenderedBuffer.addAll(hs);
//		
//		for(String serviceMadePartBuffer : serviceRenderedBuffer){
//			ServiceRendered serviceRendered = new ServiceRendered();
//			serviceRendered.setServiceMadePart(serviceMadePartBuffer);
//			
//			serviceRendered = (ServiceRendered) serviceRenderedService.saveServiceRendered(serviceRendered);
//		}
//
//		logger.info("DONE SAVING SERVICES RENDERED (BATCH)");
//		
//		mnv.addObject("serviceRenderedBuffer", serviceRenderedBuffer);
//
//		return mnv;
//
//	}
}
