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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.DummyMagsProduct;
import com.altostratus.bionicwheels.model.DummyTireProduct;
import com.altostratus.bionicwheels.model.ProductMagWheels;
import com.altostratus.bionicwheels.model.ProductTire;
import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.ProductMagWheelsService;
import com.altostratus.bionicwheels.service.ProductTireService;
import com.altostratus.bionicwheels.service.SupplierService;

@Controller("tireMagWheelsController")
@RequestMapping("/admin")
public class TireMagWheelsController {
	@Autowired
	ProductTireService productTireService;

	@Autowired
	ProductMagWheelsService productMagWheelsService;

	@Autowired
	BrandService brandService;

	@Autowired
	SupplierService supplierService;

	private Logger logger = LoggerFactory
			.getLogger(TireMagWheelsController.class);

	ArrayList<String> filterOptions = new ArrayList<String>();

	@RequestMapping(value = "/search-tires", method = RequestMethod.GET)
	public ModelAndView searchTiresIndex(HttpServletRequest request) {
		logger.info("entering search tire index.");
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
			@ModelAttribute("tire") DummyTireProduct tire, BindingResult result) {
		logger.info("searching tire.");
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
	public ModelAndView searchMagsIndex(HttpServletRequest request) {
		logger.info("entering search tire index.");
		ModelAndView mnv = new ModelAndView("admin.viewmagsproducts.index");
		mnv.addObject("mags", new DummyMagsProduct());
		mnv.addObject("magwheels", null);
		mnv.addObject("brands", brandService.getAllBrands());
		mnv.addObject("suppliers", supplierService.getAllSuppliers());
		mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		return mnv;
	}

	@RequestMapping(value = "/search-mags", method = RequestMethod.POST)
	public ModelAndView searchMags(HttpServletRequest request,
			@ModelAttribute("mags") DummyMagsProduct mags, BindingResult result) {
		logger.info("searching mags.");
		ModelAndView mnv = new ModelAndView("admin.viewmagsproducts.index");
		if (mags.getFilterName().equalsIgnoreCase("SIZE_PCD")) {
			List<ProductMagWheels> magwheels = productMagWheelsService
					.getAllMagWheelsBySizeAndPCD(mags.getSize(), mags.getPcd());
			mnv.addObject("mags", new DummyMagsProduct());
			if (magwheels.size() != 0) {
				mnv.addObject("magwheels", magwheels);
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		}
		if (mags.getFilterName().equalsIgnoreCase("SIZE")) {
			List<ProductMagWheels> magwheels = productMagWheelsService
					.getAllMagWheelsBySize(mags.getSize2());
			mnv.addObject("mags", new DummyMagsProduct());
			if (magwheels.size() != 0) {
				mnv.addObject("magwheels", magwheels);
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		}
		if (mags.getFilterName().equalsIgnoreCase("PCD")) {
			List<ProductMagWheels> magwheels = productMagWheelsService
					.getAllMagWheelsByPCD(mags.getPcd2());
			mnv.addObject("mags", new DummyMagsProduct());
			if (magwheels.size() != 0) {
				mnv.addObject("magwheels", magwheels);
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		}
		if (mags.getFilterName().equalsIgnoreCase("BRAND")) {
			Brand brand = brandService.getBrandByBrandName(mags.getBrandName());
			List<ProductMagWheels> magwheels = productMagWheelsService
					.getAllMagWheelsByBrand(brand);
			mnv.addObject("mags", new DummyMagsProduct());
			if (magwheels.size() != 0) {
				mnv.addObject("magwheels", magwheels);
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
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
			} else {
				mnv.addObject("ERROR_MESSAGE", "No Mag Wheels Found!");
			}
			mnv.addObject("brands", brandService.getAllBrands());
			mnv.addObject("suppliers", supplierService.getAllSuppliers());
			mnv.addObject("filters", DummyMagsProduct.FILTERS.values());
		}
		return mnv;
	}
}
