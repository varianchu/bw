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

import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.reports.model.ProductInventoryReport;
import com.altostratus.bionicwheels.reports.model.ProductInventorySubReport;
import com.altostratus.bionicwheels.service.ProductService;
import com.altostratus.core.service.UserManagementService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value = { "" })
public class DefaultController {
	@Autowired
	UserManagementService userManagementService;

	@Autowired
	ProductService productService;

	private Logger logger = LoggerFactory.getLogger(DefaultController.class);

	@RequestMapping(value = { "", "/", "/dashboard", "/favicon.ico" }, method = RequestMethod.GET)
	public ModelAndView dashboard(HttpServletRequest request) {
		logger.info("Entering dashbaord");

		ModelAndView mnv = new ModelAndView("index");

		return mnv;
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
