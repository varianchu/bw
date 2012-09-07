package com.altostratus.bionicwheels.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.DummyProduct;
import com.altostratus.bionicwheels.model.ImageDirectoryModel;
import com.altostratus.bionicwheels.model.Inventory;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.CategoryService;
import com.altostratus.bionicwheels.service.InventoryService;
import com.altostratus.bionicwheels.service.ProductService;
import com.altostratus.bionicwheels.service.SupplierService;
import com.altostratus.bionicwheels.validator.ProductValidator;

@Controller("productController")
@RequestMapping("/admin")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	SupplierService supplierService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductValidator productValidator;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	BrandService brandService;

	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	String brandName;

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ModelAndView productIndex(HttpServletRequest request) {
		logger.info("entering product index");
		ModelAndView mnv = new ModelAndView("admin.product.index");
		mnv.addObject("product", new DummyProduct());
		mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
		mnv.addObject("categories", categoryService.getAllCategories());
		List<Supplier> suppliers = supplierService.getAllSuppliers();
		mnv.addObject("suppliers", suppliers);
		List<Brand> brands = brandService
				.getAllBrandSuppliers(suppliers.get(0));
		List<String> brandNames = new ArrayList<String>();

		for (Brand brand : brands) {
			brandNames.add(brand.getBrandName());
		}

		mnv.addObject("brands", brandNames);
		mnv.addObject("products", productService.getAllProducts());
		return mnv;
	}

	@RequestMapping(value = "/product/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editProduct(@PathVariable("id") Long productId,
			HttpServletRequest request) {
		logger.info("Editing product id: " + productId.toString());

		DummyProduct dp = new DummyProduct();
		Product product = productService.getProduct(productId);
		Inventory inventory = inventoryService.getInventory(product
				.getInventoryId());

		dp.setId(productId);
		dp.setCode(product.getCode());
		dp.setProductName(product.getProductName());
		dp.setDescription(product.getDescription());
		dp.setTotalQty(product.getTotalQty());
		dp.setUnitOfMeasure(product.getUnitOfMeasure());
		dp.setCategoryId(product.getCategory().getId());
		dp.setSupplierId(product.getSupplier().getId());
		// dp.setBrandName(product.getBrand().getBrandName());
		dp.setInventoryId(product.getInventoryId());
		dp.setQty(inventory.getQty());
		dp.setSrp(inventory.getSrp());
		dp.setCost(inventory.getCost());

		ModelAndView mnv = new ModelAndView("admin.product.index");
		mnv.addObject("product", dp);
		mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
		mnv.addObject("categories", categoryService.getAllCategories());
		List<Supplier> suppliers = supplierService.getAllSuppliers();
		mnv.addObject("suppliers", suppliers);
		List<Brand> brands = brandService.getAllBrandSuppliers(product
				.getSupplier());
		List<String> brandNames = new ArrayList<String>();
		for (Brand brand : brands) {
			brandNames.add(brand.getBrandName());
		}
		mnv.addObject("brands", brandNames);
		mnv.addObject("products", productService.getAllProducts());
		return mnv;
	}

	@RequestMapping(value = "/product/remove/{id}", method = RequestMethod.GET)
	public ModelAndView removeProduct(@PathVariable("id") Long productId,
			HttpServletRequest request) {
		logger.info("Removing product id: " + productId.toString());
		productService.removeProduct(productId);
		ModelAndView mnv = new ModelAndView("redirect:/admin/product");
		return mnv;
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public ModelAndView saveProduct(HttpServletRequest request,
			@ModelAttribute("product") DummyProduct dummyProduct,
			BindingResult result) {
		logger.info("Saving product");

		// productValidator.validate(dummyProduct, result);
		// if (result.hasErrors()) {
		// logger.info("Failed to save product.");
		// ModelAndView mnv = new ModelAndView("admin.product.index");
		// mnv.addObject("product", new DummyProduct());
		// mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
		// mnv.addObject("categories", categoryService.getAllCategories());
		// mnv.addObject("suppliers", supplierService.getAllSuppliers());
		// mnv.addObject("products", productService.getAllProducts());
		// return mnv;
		// }

		Product product = new Product();
		MultipartFile file = dummyProduct.getFileData();
		String fileName = null;
		String fileNameThumbnail = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;

		// save product first, then check if inventory size of product is null,
		// if null create new inventory else create a new if statement
		// and check if newPrice is true, if it is true then create a new
		// inventory else save the same inventory to product

		// edit the ajax for setting the brandname
		logger.info("brandName from dummy product is: "
				+ dummyProduct.getBrandName().toString());

		try {

			if (file.getSize() > 0) {
				inputStream = file.getInputStream();
				if (file.getSize() > 10000000) {
					logger.info("Failed to upload image.");
					ModelAndView mnv = new ModelAndView("admin.product.index");
					mnv.addObject("product", new DummyProduct());
					mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
					mnv.addObject("categories",
							categoryService.getAllCategories());
					mnv.addObject("suppliers",
							supplierService.getAllSuppliers());
					mnv.addObject("products", productService.getAllProducts());
					mnv.addObject("fail", "failed to upload image.");
					return mnv;
				}

				ServletContext context = request.getSession()
						.getServletContext();
				fileName = context.getRealPath(request.getContextPath());
				fileNameThumbnail = fileName + "/product-catalogue/"
						+ dummyProduct.getCode() + "_thumbnail.png";
				fileName += "/product-catalogue/" + dummyProduct.getCode()
						+ ".png";
				outputStream = new FileOutputStream(fileName);
				logger.info(fileName);

				int readBytes = 0;
				byte[] buffer = new byte[10000000];
				while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
					outputStream.write(buffer, 0, readBytes);
				}
				outputStream.close();
				inputStream.close();

				BufferedImage img = new BufferedImage(75, 75,
						BufferedImage.TYPE_INT_RGB);
				img.createGraphics().drawImage(
						ImageIO.read(new File(fileName)).getScaledInstance(75,
								75, Image.SCALE_SMOOTH), 0, 0, null);
				ImageIO.write(img, "png", new File(fileNameThumbnail));

			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		if (dummyProduct.getId() != null) {
			product.setId(dummyProduct.getId());
			product.setInventoryId(dummyProduct.getInventoryId());
		}

		product.setCode(dummyProduct.getCode());
		product.setTotalQty(dummyProduct.getTotalQty());
		product.setProductName(dummyProduct.getProductName());
		product.setDescription(dummyProduct.getDescription());
		product.setUnitOfMeasure(dummyProduct.getUnitOfMeasure());
		product.setCategory(categoryService.getCategory(dummyProduct
				.getCategoryId()));
		product.setSupplier(supplierService.getSupplier(dummyProduct
				.getSupplierId()));

		// check the brand
		Brand brand = brandService.getBrandByBrandName(brandName);
		product.setBrand(brand);

		logger.info("brand is " + brand.getBrandName());

		product.setImageUrl(fileName);
		product.setThumbnailUrl(fileNameThumbnail);

		product = (Product) productService.saveProduct(product);

		if (dummyProduct.getId() == null) {
			try {
				Code39Bean bean = new Code39Bean();
				final int dpi = 150;
				bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
				bean.setWideFactor(3);
				bean.doQuietZone(false);
				ServletContext context = request.getSession()
						.getServletContext();
				String barcodeFileName = context.getRealPath(request
						.getContextPath());
				File outputFile = new File(barcodeFileName
						+ "/product-catalogue/barcode-" + product.getCode()
						+ ".jpg");
				OutputStream out = new FileOutputStream(outputFile);
				logger.info("barcode is generated in " + outputFile);
				try {
					BitmapCanvasProvider canvas = new BitmapCanvasProvider(out,
							"image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY,
							false, 0);
					bean.generateBarcode(canvas, product.getCode());
					canvas.finish();
				} finally {
					out.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		List<Inventory> inventories = inventoryService
				.getInventoryByProduct(product);

		if (inventories.size() == 0) {
			Inventory inventory = new Inventory();
			inventory.setCost(dummyProduct.getCost());
			inventory.setProduct(product);
			inventory.setQty(dummyProduct.getQty());
			inventory.setSrp(dummyProduct.getSrp());

			inventory.setDate(new Date());
			inventory = (Inventory) inventoryService.saveInventory(inventory);
			product = productService.getProduct(product.getId());
			product.setInventoryId(inventory.getId());
			product = productService.saveProduct(product);
			logger.info("inventory is saved, new product --- new inventory");
			logger.info("this product's inventory id link is: "
					+ product.getInventoryId());
		} else {
			if (dummyProduct.getNewPrice() == true) {
				Inventory newInventory = new Inventory();
				newInventory.setCost(dummyProduct.getCost());
				newInventory.setProduct(product);
				newInventory.setQty(dummyProduct.getQty());
				newInventory.setSrp(dummyProduct.getSrp());
				newInventory.setDate(new Date());
				newInventory = (Inventory) inventoryService
						.saveInventory(newInventory);
				product = productService.getProduct(product.getId());
				product.setInventoryId(newInventory.getId());
				product = productService.saveProduct(product);
				// product.
				logger.info("inventory is saved, old product --- new inventory");
				logger.info("inventory id for new price for PRODUCT: "
						+ product.getProductName() + " is "
						+ product.getInventoryId());
			} else {
				logger.info("HELLO THERE!");
				logger.info("product is: " + product + "product id is: "
						+ product.getId());
				logger.info("product inventory is: " + product.getInventoryId());
				Inventory oldInventory = (Inventory) inventoryService
						.getInventory(product.getInventoryId());
				logger.info("HELLO THERE AGAIN!");
				oldInventory.setCost(dummyProduct.getCost());
				oldInventory.setQty(dummyProduct.getQty());
				oldInventory.setSrp(dummyProduct.getSrp());
				oldInventory = (Inventory) inventoryService
						.saveInventory(oldInventory);
				logger.info("inventory is saved, old product --- old inventory");
			}
		}

		Double totalQty = 0.0;
		List<Inventory> qtyInventories = inventoryService
				.getInventoryByProduct(product);
		for (Inventory inventory : qtyInventories) {
			logger.info("inventory id from inventories: " + inventory.getId());
			totalQty += inventory.getQty();
		}
		logger.info("product id is: " + product.getId());
		product = productService.getProduct(product.getId());
		product.setTotalQty(totalQty);
		product = productService.saveProduct(product);

		ModelAndView mnv = new ModelAndView("redirect:/admin/product");
		return mnv;
	}

	@RequestMapping(value = "/view_products", method = RequestMethod.GET)
	public ModelAndView viewProducts(HttpServletRequest request) {
		logger.info("entering view products index");
		ModelAndView mnv = new ModelAndView("admin.viewproducts.index");
		ArrayList<ImageDirectoryModel> productURLs = new ArrayList<ImageDirectoryModel>();
		for (Product p : productService.getAllProducts()) {
			ImageDirectoryModel imageDirectoryModel = new ImageDirectoryModel();
			if (p.getImageUrl() == null) {
				imageDirectoryModel.setImageDir("/images/bioniclogo.jpg");
				imageDirectoryModel
						.setThumbnailDir("/images/bionicthumbnail.jpg");
				productURLs.add(imageDirectoryModel);
			} else {
				String productURL = "/product-catalogue/" + p.getCode()
						+ ".png";
				String thumbnailURL = "/product-catalogue/" + p.getCode()
						+ "_thumbnail.png";
				imageDirectoryModel.setImageDir(productURL);
				imageDirectoryModel.setThumbnailDir(thumbnailURL);

				productURLs.add(imageDirectoryModel);
			}
		}
		mnv.addObject("urls", productURLs);
		return mnv;
	}

	@RequestMapping(value = "/product-brands/{id}")
	public @ResponseBody
	List<String> getBrands(@PathVariable("id") Long id) {
		Supplier supplier = supplierService.getSupplier(id);

		List<Brand> brands = new ArrayList<Brand>();
		brands = brandService.getAllBrandSuppliers(supplier);

		List<String> brandNames = new ArrayList<String>();

		for (Brand brand : brands) {
			brandNames.add(brand.getBrandName());
		}

		ModelAndView mnv = new ModelAndView("admin.product.index");
		mnv.addObject("brands", brands);

		return brandNames;

	}

	@RequestMapping(value = "/setbrandname/{name}")
	public @ResponseBody
	void setBrandName(@PathVariable("name") String brandName) {

		this.brandName = brandName;

	}
}
