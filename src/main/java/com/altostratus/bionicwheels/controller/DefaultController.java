package com.altostratus.bionicwheels.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.BarGraphModel;
import com.altostratus.bionicwheels.model.BarGraphModel2;
import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.model.Settings;
import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.reports.model.ProductInventoryReport;
import com.altostratus.bionicwheels.reports.model.ProductInventorySubReport;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.CategoryService;
import com.altostratus.bionicwheels.service.ProductService;
import com.altostratus.bionicwheels.service.SettingsService;
import com.altostratus.bionicwheels.service.SupplierService;
import com.altostratus.core.service.UserManagementService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = { "" })
public class DefaultController {
	@Autowired
	UserManagementService userManagementService;

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	SettingsService settingsService;

	@Autowired
	BrandService brandService;

	@Autowired
	SupplierService supplierService;

	private Logger logger = LoggerFactory.getLogger(DefaultController.class);

	@RequestMapping(value = { "", "/", "/dashboard", "/favicon.ico" }, method = RequestMethod.GET)
	public ModelAndView dashboard(HttpServletRequest request) {
		logger.info("Entering dashboard");

		List<BarGraphModel> bgms = new ArrayList<BarGraphModel>();
		List<BarGraphModel2> bgms2 = new ArrayList<BarGraphModel2>();
		Long id = (long) 1;

		Settings settings = settingsService.getSettings(id);

		if (settings == null || productService.getAllProducts().size() == 0
				|| categoryService.getAllCategories().size() == 0) {

			ModelAndView mnv = new ModelAndView("indexstart");
			return mnv;

		} else {

			ModelAndView mnv = new ModelAndView("index");

			if (settings.getFilterBy().equalsIgnoreCase("CATEGORY")) {
				logger.info("testing");
				List<Category> categories = categoryService.getAllCategories();

				for (Category category : categories) {

					BarGraphModel bgm = new BarGraphModel();

					Double aboveNum = settings.getCeilingValue();
					Double belowNum = settings.getFloorValue();

					Integer sizeOfAboveNum = productService
							.getProductsByCategoryAboveNumber(category,
									aboveNum).size();
					// logger.info(sizeOfAboveNum.toString());
					Integer sizeOfBelowNum = productService
							.getProductsByCategoryBelowNumber(category,
									belowNum).size();
					Integer totalSize = productService.getProductsByCategory(
							category).size();

					Integer qty = totalSize - (sizeOfAboveNum + sizeOfBelowNum);

					bgm.setAboveQty(sizeOfAboveNum.toString());
					bgm.setBelowQty(sizeOfBelowNum.toString());
					bgm.setQty(qty.toString());
					bgm.setCategoryName(category.getCategoryName());

					bgms.add(bgm);
				}
				logger.info("testing2");
				logger.info("settings category choice is: "
						+ settings.getCategoryChoice());
				// error starts here
				Category category = categoryService.getCategoryByName(settings
						.getCategoryChoice());
				logger.info("Category is: " + category.getCategoryName());
				List<Product> products = productService
						.getProductsByCategory(category);
				logger.info("testing3");
				for (Product product : products) {
					if (product.getProductTire() == null) {
						BarGraphModel2 bgm2 = new BarGraphModel2();
						bgm2.setProductName(product.getProductName());
						bgm2.setQty(product.getTotalQty().toString());
						bgm2.setCeilingValue(settings.getCeilingValue()
								.toString());
						bgm2.setFloorValue(settings.getFloorValue().toString());

						bgms2.add(bgm2);
					} else if (product.getProductTire() != null) {
						BarGraphModel2 bgm2 = new BarGraphModel2();
						bgm2.setProductName(product.getProductTire().toString());
						bgm2.setQty(product.getTotalQty().toString());
						bgm2.setCeilingValue(settings.getCeilingValue()
								.toString());
						bgm2.setFloorValue(settings.getFloorValue().toString());

						bgms2.add(bgm2);
					}
				}
				mnv.addObject("title", "Category");
				mnv.addObject("title2", settings.getCategoryChoice());
				mnv.addObject("bgms", bgms);
				mnv.addObject("bgms2", bgms2);
				return mnv;

			} else if (settings.getFilterBy().equalsIgnoreCase("BRAND")) {

				List<Brand> brands = brandService.getAllBrands();

				for (Brand brand : brands) {

					BarGraphModel bgm = new BarGraphModel();

					Double aboveNum = settings.getCeilingValue();
					Double belowNum = settings.getFloorValue();

					Integer sizeOfAboveNum = productService
							.getProductsByBrandAboveNumber(brand, aboveNum)
							.size();
					// logger.info(sizeOfAboveNum.toString());
					Integer sizeOfBelowNum = productService
							.getProductsByBrandBelowNumber(brand, belowNum)
							.size();
					Integer totalSize = productService
							.getProductsByBrand(brand).size();

					Integer qty = totalSize - (sizeOfAboveNum + sizeOfBelowNum);

					bgm.setAboveQty(sizeOfAboveNum.toString());
					bgm.setBelowQty(sizeOfBelowNum.toString());
					bgm.setQty(qty.toString());
					bgm.setCategoryName(brand.getBrandName());

					bgms.add(bgm);
				}

				Brand brand = brandService.getBrandByBrandName(settings
						.getBrandChoice());

				List<Product> products = productService
						.getProductsByBrand(brand);

				for (Product product : products) {
					if (product.getProductTire() == null) {
						BarGraphModel2 bgm2 = new BarGraphModel2();
						bgm2.setProductName(product.getProductName());
						bgm2.setQty(product.getTotalQty().toString());
						bgm2.setCeilingValue(settings.getCeilingValue()
								.toString());
						bgm2.setFloorValue(settings.getFloorValue().toString());

						bgms2.add(bgm2);
					} else if (product.getProductTire() != null) {
						BarGraphModel2 bgm2 = new BarGraphModel2();
						bgm2.setProductName(product.getProductTire().toString());
						bgm2.setQty(product.getTotalQty().toString());
						bgm2.setCeilingValue(settings.getCeilingValue()
								.toString());
						bgm2.setFloorValue(settings.getFloorValue().toString());

						bgms2.add(bgm2);
					}
				}
				mnv.addObject("title", "Brand");
				mnv.addObject("title2", settings.getBrandChoice());
				mnv.addObject("bgms", bgms);
				mnv.addObject("bgms2", bgms2);
				return mnv;

			} else {

				List<Supplier> suppliers = supplierService.getAllSuppliers();

				for (Supplier supplier : suppliers) {

					BarGraphModel bgm = new BarGraphModel();

					Double aboveNum = settings.getCeilingValue();
					Double belowNum = settings.getFloorValue();

					Integer sizeOfAboveNum = productService
							.getProductsBySupplierAboveNumber(supplier,
									aboveNum).size();
					// logger.info(sizeOfAboveNum.toString());
					Integer sizeOfBelowNum = productService
							.getProductsBySupplierBelowNumber(supplier,
									belowNum).size();
					Integer totalSize = productService.getProductsBySupplier(
							supplier).size();

					Integer qty = totalSize - (sizeOfAboveNum + sizeOfBelowNum);

					bgm.setAboveQty(sizeOfAboveNum.toString());
					bgm.setBelowQty(sizeOfBelowNum.toString());
					bgm.setQty(qty.toString());
					bgm.setCategoryName(supplier.getSupplierName());

					bgms.add(bgm);
				}

				Supplier supplier = supplierService
						.getSupplierBySupplierName(settings.getSupplierChoice());

				List<Product> products = productService
						.getProductsBySupplier(supplier);

				for (Product product : products) {
					if (product.getProductTire() == null) {
						BarGraphModel2 bgm2 = new BarGraphModel2();
						bgm2.setProductName(product.getProductName());
						bgm2.setQty(product.getTotalQty().toString());
						bgm2.setCeilingValue(settings.getCeilingValue()
								.toString());
						bgm2.setFloorValue(settings.getFloorValue().toString());

						bgms2.add(bgm2);
					} else if (product.getProductTire() != null) {
						BarGraphModel2 bgm2 = new BarGraphModel2();
						bgm2.setProductName(product.getProductTire().toString());
						bgm2.setQty(product.getTotalQty().toString());
						bgm2.setCeilingValue(settings.getCeilingValue()
								.toString());
						bgm2.setFloorValue(settings.getFloorValue().toString());

						bgms2.add(bgm2);
					}
				}
				mnv.addObject("title", "Supplier");
				mnv.addObject("title2", settings.getSupplierChoice());
				mnv.addObject("bgms", bgms);
				mnv.addObject("bgms2", bgms2);
				return mnv;
			}
		}

	}

	@RequestMapping(value = { "/excel" }, method = RequestMethod.GET)
	public void excel(HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(new File("myfile.xls"));
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			Label label = new Label(0, 2, "R4R JExcel API Example");
			sheet.addCell(label);
			workbook.write();
			workbook.close();

			String fileName = "SalesReport.xls";
			response.setHeader("Content-Disposition", "inline; filename="
					+ fileName);
			// Make sure to set the correct content type
			response.setContentType("application/vnd.ms-excel");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 7. Write to the output stream
			ServletOutputStream outputStream = response.getOutputStream();
			baos.writeTo(outputStream);
			outputStream.flush();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/inventory-report")
	public void generateInventoryReport(HttpServletRequest request,
			HttpServletResponse response) {

		List<ProductInventoryReport> reports = new ArrayList<ProductInventoryReport>();
		List<ProductInventorySubReport> subReports = new ArrayList<ProductInventorySubReport>();
		List<Product> products = productService.getAllProducts();

		ProductInventoryReport productInventoryReport = new ProductInventoryReport();

		for (Product p : products) {

			ProductInventorySubReport productInventorySubReport = new ProductInventorySubReport();

			productInventorySubReport.setCategory(p.getCategory().toString());
			productInventorySubReport.setProductName(p.getProductName());
			productInventorySubReport.setCode(p.getCode());
			productInventorySubReport.setQty(p.getTotalQty().toString());
			productInventorySubReport.setUnitOfMeasure(p.getUnitOfMeasure());
			subReports.add(productInventorySubReport);
		}

		productInventoryReport.setSubReports(subReports);
		reports.add(productInventoryReport);

		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(reports);
			InputStream reportStream = this.getClass().getResourceAsStream(
					"/reports/report2.jrxml");
			JasperDesign jasperDesign = JRXmlLoader.load(reportStream);
			JasperReport jasperReport = JasperCompileManager
					.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, null, dataSource);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();
			String filename = "inventory_report.pdf";
			response.setContentType("application/pdf");
			// response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ filename);
			// response.setHeader("Content-Disposition", "inline; filename="
			// + filename);
			// response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			ServletOutputStream outputStream = response.getOutputStream();
			baos.writeTo(outputStream);
			outputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
