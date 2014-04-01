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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.DummyMagsProduct;
import com.altostratus.bionicwheels.model.DummyTireProduct;
import com.altostratus.bionicwheels.model.InventoryTransaction;
import com.altostratus.bionicwheels.model.InventoryTransactionProduct;
import com.altostratus.bionicwheels.model.ProductMagWheels;
import com.altostratus.bionicwheels.model.ProductTire;
import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.InventoryTransactionService;
import com.altostratus.bionicwheels.service.ProductMagWheelsService;
import com.altostratus.bionicwheels.service.ProductTireService;
import com.altostratus.bionicwheels.service.SupplierService;

@Controller("tireMagWheelsController")
public class TireMagWheelsController {

	@Autowired
	ProductTireService productTireService;

	@Autowired
	ProductMagWheelsService productMagWheelsService;

	@Autowired
	BrandService brandService;

	@Autowired
	SupplierService supplierService;

	@Autowired
	InventoryTransactionService inventoryTransactionService;

	private Logger logger = LoggerFactory
			.getLogger(TireMagWheelsController.class);

	@RequestMapping(value = "/search-tires", method = RequestMethod.GET)
	public ModelAndView searchTiresIndex(HttpServletRequest request,
			Principal principal) {
		logger.info("entering search tire index - " + principal.getName());
		ModelAndView mnv = new ModelAndView("admin.viewtireproducts.index");
		mnv.addObject("tire", new DummyTireProduct());
		mnv.addObject("tires", null);
		mnv.addObject("brands", brandService.getAllBrands());
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		mnv.addObject("filters", DummyTireProduct.FILTERS.values());
		return mnv;
	}

	@RequestMapping(value = "/search-tires", method = RequestMethod.POST)
	public ModelAndView searchTires(HttpServletRequest request,
			@ModelAttribute("tire") DummyTireProduct tire,
			BindingResult result, Principal principal) {
		logger.info("searching tire by " + principal.getName());
		ModelAndView mnv = new ModelAndView("admin.viewtireproducts.index");
		if (tire.getFilterName().equalsIgnoreCase(
				"CrossSectionWidth_Profile_Diameter")) {
			List<ProductTire> tires = productTireService
					.getProductTiresByCrossSectionWidthProfileDiameter(
							tire.getCrossSectionWidth(), tire.getProfile(),
							tire.getDiameter());
			mnv.addObject("tire", new DummyTireProduct());
			if (tires.size() != 0) {
				mnv.addObject("tires", tires);
				logger.info("searched tires by cross section width, profile and diameter by "
						+ principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Tire Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyTireProduct.FILTERS.values());
		}
		if (tire.getFilterName().equalsIgnoreCase("Brand_Qty")) {
			Brand brand = brandService.getBrandByBrandName(tire.getBrandName());
			List<ProductTire> tires = productTireService
					.getProductTiresByBrandTotalQty(brand, tire.getQty());
			mnv.addObject("tire", new DummyTireProduct());
			if (tires.size() != 0) {
				mnv.addObject("tires", tires);
				logger.info("searched tires by brand and qty by "
						+ principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Tire Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyTireProduct.FILTERS.values());
		}
		if (tire.getFilterName().equalsIgnoreCase("Brand")) {
			Brand brand = brandService
					.getBrandByBrandName(tire.getBrandName2());
			List<ProductTire> tires = productTireService
					.getProductTiresByBrand(brand);
			mnv.addObject("tire", new DummyTireProduct());
			if (tires.size() != 0) {
				mnv.addObject("tires", tires);
				logger.info("searched tires by brand by " + principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Tire Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyTireProduct.FILTERS.values());
		}
		if (tire.getFilterName().equalsIgnoreCase("Supplier")) {
			Supplier supplier = supplierService.getSupplierBySupplierName(tire
					.getSupplierName());
			List<ProductTire> tires = productTireService
					.getProductTiresBySupplier(supplier);
			mnv.addObject("tire", new DummyTireProduct());
			if (tires.size() != 0) {
				mnv.addObject("tires", tires);
				logger.info("searched tires by supplier by "
						+ principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Tire Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyTireProduct.FILTERS.values());
		}
		if (tire.getFilterName().equalsIgnoreCase("CrossSectionWidth")) {
			List<ProductTire> tires = productTireService
					.getProductTireByCrossSectionWidth(tire
							.getCrossSectionWidth2());
			mnv.addObject("tire", new DummyTireProduct());
			if (tires.size() != 0) {
				mnv.addObject("tires", tires);
				logger.info("searched tires by cross section width by "
						+ principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Tire Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyTireProduct.FILTERS.values());
		}
		if (tire.getFilterName().equalsIgnoreCase("Profile")) {
			List<ProductTire> tires = productTireService
					.getProductTireByProfile(tire.getProfile2());
			mnv.addObject("tire", new DummyTireProduct());
			if (tires.size() != 0) {
				mnv.addObject("tires", tires);
				logger.info("searched tires by profile by "
						+ principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Tire Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyTireProduct.FILTERS.values());
		}
		if (tire.getFilterName().equalsIgnoreCase("Diameter")) {
			List<ProductTire> tires = productTireService
					.getProductTireByDiameter(tire.getDiameter2());
			mnv.addObject("tire", new DummyTireProduct());
			if (tires.size() != 0) {
				mnv.addObject("tires", tires);
				logger.info("searched tires by diameter by "
						+ principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Tire Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyTireProduct.FILTERS.values());
		}
		return mnv;
	}

	@RequestMapping(value = "/search-mags", method = RequestMethod.GET)
	public ModelAndView searchMagsIndex(HttpServletRequest request,
			Principal principal) {
		logger.info("entering search mags index by " + principal.getName());

		List<ProductMagWheels> magWheels = productMagWheelsService
				.getAllMagWheels();

		List<Brand> magWheelBrands = new ArrayList<Brand>();
		List<Supplier> magWheelSuppliers = new ArrayList<Supplier>();

		for (ProductMagWheels magWheel : magWheels) {
			magWheelBrands.add(magWheel.getProduct().getBrand());
			magWheelSuppliers.add(magWheel.getProduct().getSupplier());
		}

		ModelAndView mnv = new ModelAndView("admin.viewmagsproducts.index");
		mnv.addObject("mags", new DummyMagsProduct());
		mnv.addObject("magwheels", null);
		mnv.addObject("brands", magWheelBrands);
		mnv.addObject("suppliers", magWheelSuppliers);
		mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		return mnv;
	}

	@RequestMapping(value = "/search-mags", method = RequestMethod.POST)
	public ModelAndView searchMags(HttpServletRequest request,
			@ModelAttribute("mags") DummyMagsProduct mags,
			BindingResult result, Principal principal) {
		logger.info("searching mags by " + principal.getName());

		List<ProductMagWheels> magWheels = productMagWheelsService
				.getAllMagWheels();

		List<Brand> magWheelBrands = new ArrayList<Brand>();
		List<Supplier> magWheelSuppliers = new ArrayList<Supplier>();

		for (ProductMagWheels magWheel : magWheels) {
			magWheelBrands.add(magWheel.getProduct().getBrand());
			magWheelSuppliers.add(magWheel.getProduct().getSupplier());
		}

		ModelAndView mnv = new ModelAndView("admin.viewmagsproducts.index");
		if (mags.getFilterName().equalsIgnoreCase("SIZE_PCD")) {
			List<ProductMagWheels> magwheels = productMagWheelsService
					.getAllMagWheelsBySizeAndPCD(mags.getSize(), mags.getPcd());
			mnv.addObject("mags", new DummyMagsProduct());
			if (magwheels.size() != 0) {
				mnv.addObject("magwheels", magwheels);
				logger.info("searched mags by size and pcd by "
						+ principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", magWheelBrands);
			mnv.addObject("suppliers", magWheelSuppliers);
			mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		}
		if (mags.getFilterName().equalsIgnoreCase("SIZE")) {
			List<ProductMagWheels> magwheels = productMagWheelsService
					.getAllMagWheelsBySize(mags.getSize2());
			mnv.addObject("mags", new DummyMagsProduct());
			if (magwheels.size() != 0) {
				mnv.addObject("magwheels", magwheels);
				logger.info("searched mags by size by " + principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", magWheelBrands);
			mnv.addObject("suppliers", magWheelSuppliers);
			mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		}
		if (mags.getFilterName().equalsIgnoreCase("PCD")) {
			List<ProductMagWheels> magwheels = productMagWheelsService
					.getAllMagWheelsByPCD(mags.getPcd2());
			mnv.addObject("mags", new DummyMagsProduct());
			if (magwheels.size() != 0) {
				mnv.addObject("magwheels", magwheels);
				logger.info("searched mags by pcd by " + principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", magWheelBrands);
			mnv.addObject("suppliers", magWheelSuppliers);
			mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		}
		if (mags.getFilterName().equalsIgnoreCase("BRAND")) {
			Brand brand = brandService.getBrandByBrandName(mags.getBrandName());
			List<ProductMagWheels> magwheels = productMagWheelsService
					.getAllMagWheelsByBrand(brand);
			mnv.addObject("mags", new DummyMagsProduct());
			if (magwheels.size() != 0) {
				mnv.addObject("magwheels", magwheels);
				logger.info("searched mags by brand by " + principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", magWheelBrands);
			mnv.addObject("suppliers", magWheelSuppliers);
			mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		}
		if (mags.getFilterName().equalsIgnoreCase("SUPPLIER")) {
			Supplier supplier = supplierService.getSupplierBySupplierName(mags
					.getSupplierName());
			List<ProductMagWheels> magwheels = productMagWheelsService
					.getAllMagWheelsBySupplier(supplier);
			mnv.addObject("mags", new DummyMagsProduct());
			if (magwheels.size() != 0) {
				mnv.addObject("magwheels", magwheels);
				logger.info("searched mags by supplier by "
						+ principal.getName());
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", magWheelBrands);
			mnv.addObject("suppliers", magWheelSuppliers);
			mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		}
		return mnv;
	}

	@RequestMapping(value = "/view-purchased-tires-today", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TIRE_MAGS_PERSON', 'ROLE_ADMIN')")
	public ModelAndView viewPurchasedTiresToday(HttpServletRequest request, Principal principal) {
		
		logger.info(principal.getName() + " tries to view the tires purchases today.");
		
		ModelAndView mnv = new ModelAndView("view.tirestoday");
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date todayDate = new Date();
		Date dateToday = null;
		
		try{
			dateToday = formatter.parse(formatter.format(todayDate));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();

		Date endDate = null;

		try {
			cal1.setTime(dateToday);
			cal1.add(Calendar.DATE, 1);

			endDate = cal1.getTime();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		List<InventoryTransaction> its = new ArrayList<InventoryTransaction>();
		List<InventoryTransactionProduct> itps = new ArrayList<InventoryTransactionProduct>();

		its = inventoryTransactionService.getAllInventoryTransactionsWithinDate(dateToday, endDate);

		for (InventoryTransaction it : its) {
			for (InventoryTransactionProduct itp : it.getInventoryTransactionProducts()) {
				if (itp.getProduct().getProductTire() != null) {
					itps.add(itp);
				}
			}
		}

		mnv.addObject("itps", itps);
		mnv.addObject("dateToday", dateToday);
		return mnv;
	}

	@RequestMapping(value = "/view-purchased-magwheels-today", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('TIRE_MAGS_PERSON', 'ROLE_ADMIN')")
	public ModelAndView viewPurchasedMagWheelsToday(HttpServletRequest request, Principal principal) {
		
		logger.info(principal.getName() + " tries to view the mag wheels purchases today.");
		
		ModelAndView mnv = new ModelAndView("view.magwheelstoday");
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date todayDate = new Date();
		Date dateToday = null;
		try{
			dateToday = formatter.parse(formatter.format(todayDate));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Calendar cal1 = Calendar.getInstance();

		Date endDate = null;

		try {
			cal1.setTime(dateToday);
			cal1.add(Calendar.DATE, 1);

			endDate = cal1.getTime();
			
			logger.info("added date: " + endDate);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		List<InventoryTransaction> its = new ArrayList<InventoryTransaction>();
		List<InventoryTransactionProduct> itps = new ArrayList<InventoryTransactionProduct>();

		its = inventoryTransactionService.getAllInventoryTransactionsWithinDate(todayDate, endDate);

		for (InventoryTransaction it : its) {
			for (InventoryTransactionProduct itp : it.getInventoryTransactionProducts()) {
				if (itp.getProduct().getProductMagWheels() != null) {
					itps.add(itp);
				}
			}
		}

		mnv.addObject("itps", itps);
		mnv.addObject("dateToday", dateToday);
		return mnv;
	}

}
