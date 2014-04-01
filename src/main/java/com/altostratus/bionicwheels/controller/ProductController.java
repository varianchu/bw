package com.altostratus.bionicwheels.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
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

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.DummyProduct;
import com.altostratus.bionicwheels.model.Inventory;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.model.ProductMagWheels;
import com.altostratus.bionicwheels.model.ProductTire;
import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.service.BrandService;
import com.altostratus.bionicwheels.service.CategoryService;
import com.altostratus.bionicwheels.service.InventoryService;
import com.altostratus.bionicwheels.service.ProductMagWheelsService;
import com.altostratus.bionicwheels.service.ProductService;
import com.altostratus.bionicwheels.service.ProductTireService;
import com.altostratus.bionicwheels.service.SupplierService;
import com.altostratus.bionicwheels.validator.ProductMagWheelsValidator;
import com.altostratus.bionicwheels.validator.ProductTireValidator;
import com.altostratus.bionicwheels.validator.ProductValidator;
import com.altostratus.core.util.ConvertCode;

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
	ProductTireValidator productTireValidator;

	@Autowired
	ProductMagWheelsValidator productMagsValidator;

	@Autowired
	InventoryService inventoryService;

	@Autowired
	BrandService brandService;

	@Autowired
	ProductTireService productTireService;

	@Autowired
	ProductMagWheelsService productMagWheelsService;
	
	@Autowired
	ConvertCode convertCode;

	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	@RequestMapping(value = "/product/{message}", method = RequestMethod.GET)
	public ModelAndView productIndex(HttpServletRequest request, HttpSession session, @PathVariable("message") String message, Principal principal) {

		logger.info(principal.getName() + " is entering product index");

		String productType = "GENERIC";

		session.setAttribute("productType", productType);

		String sessionProductType = (String) session.getAttribute("productType");

		logger.info(principal.getName() + " - (INIT) session attribute product type: " + sessionProductType);

		ModelAndView mnv = new ModelAndView("admin.product.index");
		mnv.addObject("product", new DummyProduct());
		mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
		mnv.addObject("categories", categoryService.getAllCategories());
		List<Supplier> suppliers = supplierService.getAllSuppliers();
		mnv.addObject("suppliers", suppliers);
		mnv.addObject("tireconstruction", ProductTire.CONSTRUCTION.values());
		List<String> brandNames = new ArrayList<String>();
		if (suppliers.size() == 0) {
			brandNames.add("");
		} else {
			List<Brand> brands = brandService.getAllBrandSuppliers(suppliers.get(0));
			for (Brand brand : brands) {
				brandNames.add(brand.getBrandName());
			}
		}
		List<Inventory> productInventories = null;
		mnv.addObject("productInventories", productInventories);
		mnv.addObject("brands", brandNames);
		mnv.addObject("products", productService.getAllProducts());
		if(message.equalsIgnoreCase("SUCCESS")){
			mnv.addObject("SUCCESS_MESSAGE", "Successfully saved Product");
		}
		return mnv;
	}

	@RequestMapping(value = "/product/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editProduct(@PathVariable("id") Long productId, HttpServletRequest request, HttpSession session, Principal principal) {
		logger.info(principal.getName() + " tries to edit/view product id: " + productId.toString());

		DummyProduct dummyProduct = new DummyProduct();
		Product product = productService.getProduct(productId);
		Inventory inventory = inventoryService.getInventory(product.getInventoryId());

		dummyProduct.setId(productId);
		dummyProduct.setCode(product.getCode());
		dummyProduct.setProductName(product.getProductName());
		dummyProduct.setDescription(product.getDescription());
		dummyProduct.setTotalQty(product.getTotalQty());
		dummyProduct.setUnitOfMeasure(product.getUnitOfMeasure());
		dummyProduct.setCategoryId(product.getCategory().getId());
		dummyProduct.setSupplierId(product.getSupplier().getId());
		dummyProduct.setBrandName(product.getBrand().getBrandName());
		dummyProduct.setInventoryId(product.getInventoryId());
		dummyProduct.setConvertCode(convertCode.getCostCode(inventoryService.getInventory(product.getInventoryId()).getCost()));
		dummyProduct.setDataUri(product.getDataUri());
		
		dummyProduct.setQty(inventory.getQty());
		dummyProduct.setSrp(inventory.getSrp());
		dummyProduct.setCost(inventory.getCost());
		
		
		logger.info(principal.getName() + " dummy product setup ok.");

		ModelAndView mnv = new ModelAndView("admin.product.index");
		
		mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
		mnv.addObject("categories", categoryService.getAllCategories());
		List<Supplier> suppliers = supplierService.getAllSuppliers();
		mnv.addObject("suppliers", suppliers);
		List<Brand> brands = brandService.getAllBrandSuppliers(product.getSupplier());
		List<String> brandNames = new ArrayList<String>();
		for (Brand brand : brands) {
			brandNames.add(brand.getBrandName());
		}

		// if edited do the necessary changes
		if (product.getProductTire() != null) {
			dummyProduct.setTireId(product.getProductTire().getId());
			dummyProduct.setCrossSectionWidth(product.getProductTire().getCrossSectionWidth());
			dummyProduct.setProfile(product.getProductTire().getProfile());
			dummyProduct.setConstruction(product.getProductTire().getConstruction());
			dummyProduct.setDiameter(product.getProductTire().getDiameter());
			
			String productType = "TIRE";

			session.setAttribute("productType", productType);

			String sessionProductType = (String) session.getAttribute("productType"); 
			
			logger.info(principal.getName() + " - (EDIT/VIEW) session attribute product type: " + sessionProductType);
		}

		// logger.info("product mag wheels id: "
		// + product.getProductmagWheels().getId());

		else if (product.getProductMagWheels() != null) {
			dummyProduct.setMagsId(product.getProductMagWheels().getId());
			dummyProduct.setStyle(product.getProductMagWheels().getStyle());
			dummyProduct.setSize(product.getProductMagWheels().getSize());
			dummyProduct.setSpokes(product.getProductMagWheels().getSpokes());
			dummyProduct.setPcd(product.getProductMagWheels().getPcd());
			dummyProduct.setOffset(product.getProductMagWheels().getOffset());
			dummyProduct.setFinish(product.getProductMagWheels().getFinish());
			
			String productType = "MAGWHEEL";

			session.setAttribute("productType", productType);

			String sessionProductType = (String) session.getAttribute("productType"); 
			
			logger.info(principal.getName() + " - (EDIT/VIEW) session attribute product type: " + sessionProductType);
		} else {
			String productType = "GENERIC";

			session.setAttribute("productType", productType);

			String sessionProductType = (String) session.getAttribute("productType"); 
			
			logger.info(principal.getName() + " - (EDIT/VIEW) session attribute product type: " + sessionProductType);
		}

		mnv.addObject("product", dummyProduct);
		mnv.addObject("productInventories", product.getInventories());
		mnv.addObject("brands", brandNames);
		mnv.addObject("tireconstruction", ProductTire.CONSTRUCTION.values());
		mnv.addObject("products", productService.getAllProducts());
		return mnv;
	}

	@RequestMapping(value = "/product/remove/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView removeProduct(@PathVariable("id") Long productId, HttpServletRequest request, Principal principal) {
		
		logger.info(principal.getName() + "tries to remove product id: " + productId.toString());
		ModelAndView mnv = new ModelAndView("admin.product.index");
		try {
			productService.removeProduct(productId);
			mnv.addObject("product", new DummyProduct());
			mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
			mnv.addObject("categories", categoryService.getAllCategories());
			List<Supplier> suppliers = supplierService.getAllSuppliers();
			mnv.addObject("suppliers", suppliers);
			mnv.addObject("tireconstruction", ProductTire.CONSTRUCTION.values());
			// error here because of suppliers.get(0)
			List<String> brandNames = new ArrayList<String>();
			if (suppliers.size() == 0) {
				brandNames.add("");
			} else {
				List<Brand> brands = brandService.getAllBrandSuppliers(suppliers.get(0));
				for (Brand brand : brands) {
					brandNames.add(brand.getBrandName());
				}
			}
			List<Inventory> productInventories = null;
			mnv.addObject("productInventories", productInventories);
			mnv.addObject("brands", brandNames);
			mnv.addObject("products", productService.getAllProducts());
			mnv.addObject("SUCCESS_MESSAGE", "Successfully removed Product");
			
			logger.info(principal.getName() + " has successfully removed product.");
			
			return mnv;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			logger.info("remove unsuccessful (Product) by " + principal.getName());
			
			mnv.addObject("product", new DummyProduct());
			mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
			mnv.addObject("categories", categoryService.getAllCategories());
			List<Supplier> suppliers = supplierService.getAllSuppliers();
			mnv.addObject("suppliers", suppliers);
			mnv.addObject("tireconstruction", ProductTire.CONSTRUCTION.values());
			// error here because of suppliers.get(0)
			List<String> brandNames = new ArrayList<String>();
			if (suppliers.size() == 0) {
				brandNames.add("");
			} else {
				List<Brand> brands = brandService.getAllBrandSuppliers(suppliers.get(0));
				for (Brand brand : brands) {
					brandNames.add(brand.getBrandName());
				}
			}
			List<Inventory> productInventories = null;
			mnv.addObject("productInventories", productInventories);
			mnv.addObject("brands", brandNames);
			mnv.addObject("products", productService.getAllProducts());
			return mnv;
		}
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER', 'TIRE_MAGS_PERSON')")
	public ModelAndView saveProduct(HttpServletRequest request, @ModelAttribute("product") DummyProduct dummyProduct, BindingResult result, HttpSession session, Principal principal) {
		logger.info(principal.getName() + " tries to save product.");

		Product product = new Product();

		String sessionProductType = (String) session.getAttribute("productType"); 
		
		logger.info(principal.getName() + " - (POST) session attribute product type: " + sessionProductType);

		if (sessionProductType.equalsIgnoreCase("GENERIC")) {
			logger.info(principal.getName() + " - saving generic product");
			productValidator.validate(dummyProduct, result);

			if (result.hasErrors()) {
				ModelAndView mnv = new ModelAndView("admin.product.index");
				mnv.addObject("product", new DummyProduct());
				mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
				mnv.addObject("categories", categoryService.getAllCategories());
				mnv.addObject("suppliers", supplierService.getAllSuppliers());
				mnv.addObject("products", productService.getAllProducts());
				mnv.addObject("ERROR_MESSAGE", "Product was not saved. Please Check the fields.");
				List<Supplier> suppliers = supplierService.getAllSuppliers();
				List<String> brandNames = new ArrayList<String>();
				if (suppliers.size() == 0) {
					brandNames.add("");
				} else {
					List<Brand> brands = brandService.getAllBrandSuppliers(suppliers.get(0));
					for (Brand brand : brands) {
						brandNames.add(brand.getBrandName());
					}
				}
				mnv.addObject("brands", brandNames);
				logger.info("Unsuccessfully saved product (GENERIC) by " + principal.getName());
				return mnv;
			}

			product.setProductName(dummyProduct.getProductName().toUpperCase());
			logger.info(dummyProduct.getProductName());

		}
		if (sessionProductType.equalsIgnoreCase("TIRE")) {
			logger.info("saving tires.");
			productTireValidator.validate(dummyProduct, result);
			if (result.hasErrors()) {
				ModelAndView mnv = new ModelAndView("admin.product.index");
				mnv.addObject("product", new DummyProduct());
				mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
				mnv.addObject("categories", categoryService.getAllCategories());
				List<Supplier> suppliers = supplierService.getAllSuppliers();
				mnv.addObject("suppliers", suppliers);
				mnv.addObject("products", productService.getAllProducts());
				mnv.addObject("tireconstruction", ProductTire.CONSTRUCTION.values());
				List<String> brandNames = new ArrayList<String>();
				if (suppliers.size() == 0) {
					brandNames.add("");
				} else {
					List<Brand> brands = brandService.getAllBrandSuppliers(suppliers.get(0));
					for (Brand brand : brands) {
						brandNames.add(brand.getBrandName());
					}
				}
				mnv.addObject("brands", brandNames);
				mnv.addObject("ERROR_MESSAGE", "Product was not saved. Please Check the fields of Tire and Product.");
				logger.info("Unsuccessfully saved product (TIRE) by " + principal.getName());
				return mnv;
			}

			product.setProductName(dummyProduct.getCrossSectionWidth() + "/" + dummyProduct.getProfile() + dummyProduct.getConstruction() + "/" + dummyProduct.getDiameter());

		}

		if (sessionProductType.equalsIgnoreCase("MAGWHEEL")) {
			logger.info("saving mags!");
			productMagsValidator.validate(dummyProduct, result);
			if (result.hasErrors()) {
				ModelAndView mnv = new ModelAndView("admin.product.index");
				mnv.addObject("product", new DummyProduct());
				mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
				mnv.addObject("categories", categoryService.getAllCategories());
				List<Supplier> suppliers = supplierService.getAllSuppliers();
				mnv.addObject("suppliers", suppliers);
				mnv.addObject("products", productService.getAllProducts());
				mnv.addObject("tireconstruction", ProductTire.CONSTRUCTION.values());
				List<String> brandNames = new ArrayList<String>();
				if (suppliers.size() == 0) {
					brandNames.add("");
				} else {
					List<Brand> brands = brandService.getAllBrandSuppliers(suppliers.get(0));
					for (Brand brand : brands) {
						brandNames.add(brand.getBrandName());
					}
				}
				mnv.addObject("brands", brandNames);
				mnv.addObject("ERROR_MESSAGE", "Product was not saved. Please Check the fields of Mag Wheels and Product.");
				logger.info("Unsuccessfully saved product (MAGWHEEL) by " + principal.getName());
				return mnv;
			}

			product.setProductName((dummyProduct.getProductName() + "-" + dummyProduct.getSize()).toUpperCase());
			logger.info(dummyProduct.getProductName());

		}

		logger.info(principal.getName() + " - brandName from dummy product is: " + dummyProduct.getBrandName().toString());

		// check this part, this means that this overwrites the qty and other
		// details?
		if (dummyProduct.getId() != null) {
			product.setId(dummyProduct.getId());
			product.setInventoryId(dummyProduct.getInventoryId());
		}

		product.setCode(dummyProduct.getCode().toUpperCase());
		product.setTotalQty(0.0);
		product.setDescription(dummyProduct.getDescription());
		product.setUnitOfMeasure(dummyProduct.getUnitOfMeasure());
		product.setCategory(categoryService.getCategory(dummyProduct.getCategoryId()));
		product.setSupplier(supplierService.getSupplier(dummyProduct.getSupplierId()));
		Brand brand = brandService.getBrandByBrandName(dummyProduct.getBrandName());
		product.setBrand(brand);
		product.setDataUri(dummyProduct.getDataUri());
		
		logger.info("Product set - CODE: " + product.getCode() + " CATEGORY: " + product.getCategory() + " SUPPLIER: " + product.getSupplier() + " BRAND: " + product.getBrand());

		// product.setImageUrl(fileName);
		// product.setThumbnailUrl(fileNameThumbnail);
		
//		if(dummyProduct.getId()!=null){
//			product.setDataUri(product.getDataUri());
//		}

		product = (Product) productService.saveProduct(product);
		logger.info("Successfully saved product (INIT SETUP) by " + principal.getName());

		String imageString = "";
		
		if (dummyProduct.getId() == null) {
			try {
				Code39Bean bean = new Code39Bean();
				final int dpi = 150;
				//this is 150 before dpi
				bean.setModuleWidth(UnitConv.in2mm(2.0f / dpi));
//				bean.setModuleWidth(dpi);
				bean.setWideFactor(3);
				bean.doQuietZone(false);
				bean.setBarHeight(5.0);
				ServletContext context = request.getSession().getServletContext();
				String barcodeFileName = context.getRealPath(request.getContextPath());
				File outputFile = new File(barcodeFileName + "/barcode-catalogue/barcode-" + product.getId() + ".jpg");
				OutputStream out = new FileOutputStream(outputFile);
				logger.info("barcode is generated in " + outputFile);
				try {
					BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
					bean.generateBarcode(canvas, product.getId().toString());
					canvas.finish();
				} finally {
					out.close();
				}
				//added feb 24, 2014 - working!
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedImage image = null;
				try{
					image = ImageIO.read(outputFile);
				}catch(IOException e){
					logger.info("failed to convert to buffered image by " + principal.getName());
				    e.printStackTrace();
				}
				try {
				    ImageIO.write(image, "jpeg", baos);
				} catch (IOException e) {
					logger.info("failed to convert to uri - " + principal.getName());
				    e.printStackTrace();
				}
				imageString = "data:image/jpeg;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());
				logger.info("successfully converted barcode to datauri - " + principal.getName());
				//end
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.info("barcode conversion error - " + principal.getName());
			}
		}

		// change after, product.getInventories... no need additional method in
		// inventory service
		List<Inventory> inventories = inventoryService.getInventoryByProduct(product);

		if (inventories.size() == 0) {
			Inventory inventory = new Inventory();
			inventory.setCost(dummyProduct.getCost());
			inventory.setProduct(product);
			// inventory.setQty(dummyProduct.getQty());
			inventory.setQty(0.0);
			inventory.setSrp(dummyProduct.getSrp());

			inventory.setDate(new Date());
			inventory = (Inventory) inventoryService.saveInventory(inventory);
			product = productService.getProduct(product.getId());
			product.setInventoryId(inventory.getId());
			product.setDataUri(imageString);
			product = productService.saveProduct(product);
			logger.info("inventory is saved, new product --- new inventory by " + principal.getName());
			logger.info("this product's inventory id link is: " + product.getInventoryId() + " provided by " + principal.getName());
		} else {
			// do checkbox! error here!
			logger.info("get new price: " + dummyProduct.getNewPrice() + " by " + principal.getName());
			if (dummyProduct.getNewPrice() == true) {
				logger.info("NEW PRICE CLICKED BY " + principal.getName());
				Inventory newInventory = new Inventory();
				newInventory.setCost(dummyProduct.getCost());
				newInventory.setProduct(product);
				// newInventory.setQty(dummyProduct.getQty());
				newInventory.setQty(0.0);
				newInventory.setSrp(dummyProduct.getSrp());
				newInventory.setDate(new Date());
				newInventory = (Inventory) inventoryService.saveInventory(newInventory);
				product = productService.getProduct(product.getId());
				product.setInventoryId(newInventory.getId());
//				product.setDataUri(product.getDataUri());
				product = productService.saveProduct(product);
				// product.
				logger.info("inventory is saved, old product --- new inventory by " + principal.getName());
				logger.info("inventory id for new price for PRODUCT: " + product.getProductName() + " is " + product.getInventoryId() + " by " + principal.getName());
			}
			if (dummyProduct.getNewPrice() == null || dummyProduct.getNewPrice() == false) {
				logger.info("product is: " + product + "product id is: " + product.getId() + " by " + principal.getName());
				logger.info("product inventory is: " + product.getInventoryId() + " by " + principal.getName());
				Inventory oldInventory = (Inventory) inventoryService.getInventory(product.getInventoryId());
				oldInventory.setCost(dummyProduct.getCost());
				oldInventory.setSrp(dummyProduct.getSrp());
				oldInventory = (Inventory) inventoryService.saveInventory(oldInventory);
				logger.info("inventory is saved, old product --- old inventory by " + principal.getName());
			}
		}

		Double totalQty = 0.0;
		List<Inventory> qtyInventories = inventoryService.getInventoryByProduct(product);
		for (Inventory inventory : qtyInventories) {
			logger.info("inventory id from inventories: " + inventory.getId());
			totalQty += inventory.getQty();
		}
		logger.info("product id is: " + product.getId());
		product = productService.getProduct(product.getId());
		product.setTotalQty(totalQty);
		// product = productService.saveProduct(product);

		// add new productValidatorTire here additional fields :)
		if (sessionProductType.equalsIgnoreCase("TIRE")) {

			if (dummyProduct.getMagsId() != null) {
				// Long id = product.getProductMagWheels().getId();
				product.setProductMagWheels(null);
				productMagWheelsService.removeMagWheels(dummyProduct.getMagsId());
			}

			ProductTire tire;
			if (dummyProduct.getTireId() != null) {
				tire = productTireService.getProductTire(dummyProduct.getTireId());
				tire.setId(dummyProduct.getTireId());
				logger.info("tire is not null and product id is: " + tire.getId() + " by " + principal.getName());
			} else {
				tire = new ProductTire();
			}
			tire.setCrossSectionWidth(dummyProduct.getCrossSectionWidth());
			tire.setProfile(dummyProduct.getProfile());
			tire.setConstruction(dummyProduct.getConstruction());
			tire.setDiameter(dummyProduct.getDiameter());
			tire.setProduct(product);
			logger.info("Product Tire: " + product.getProductName());
			tire = productTireService.saveProductTire(tire);
			logger.info("Tire is saved! by " + principal.getName());

		}

		if (sessionProductType.equalsIgnoreCase("MAGWHEEL")) {

			if (dummyProduct.getTireId() != null) {
				logger.info("product tire: " + product.getProductTire());
				ProductTire tire = productTireService.getProductTire(dummyProduct.getTireId());
				logger.info("tire is " + tire);
				product.setProductTire(null);
				productTireService.removeProductTire(tire);
			}

			ProductMagWheels mags;
			if (dummyProduct.getMagsId() != null) {
				mags = productMagWheelsService.getMagWheels(dummyProduct.getMagsId());
				mags.setId(dummyProduct.getMagsId());
				logger.info("mags is not null and product id is: " + mags.getId() + " by " + principal.getName());
			} else {
				mags = new ProductMagWheels();
			}

			mags.setFinish(dummyProduct.getFinish());
			mags.setOffset(dummyProduct.getOffset());
			mags.setPcd(dummyProduct.getPcd());
			mags.setProduct(product);
			mags.setSize(dummyProduct.getSize());
			mags.setSpokes(dummyProduct.getSpokes());
			mags.setStyle(dummyProduct.getStyle());

			// MultipartFile file = dummyProduct.getFileData();
			String fileName = null;
			String fileNameThumbnail = null;
			// InputStream inputStream = null;
			// OutputStream outputStream = null;

			logger.info("Brand Name from dummy product is: " + dummyProduct.getBrandName().toString());

			mags.setImageUrl(fileName);
			mags.setThumbnailUrl(fileNameThumbnail);
			mags = productMagWheelsService.saveMagWheels(mags);
			logger.info("Mags is saved! by " + principal.getName());
		}

		if (sessionProductType.equalsIgnoreCase("GENERIC")) {
			logger.info("demoted to generic product");

			try {
				// Long id = product.getProductTire().getId();
				ProductTire tire;
				ProductMagWheels mags;
				if (dummyProduct.getTireId() != null) {
					tire = productTireService.getProductTire(dummyProduct.getTireId());
					logger.info("tire is not null and product(tire) id is: " + tire.getId() + " by " + principal.getName());
					product.setProductTire(null);
					productTireService.removeProductTire(tire);
				}
				if (dummyProduct.getMagsId() != null) {
					mags = productMagWheelsService.getMagWheels(dummyProduct.getMagsId());
					logger.info("mags is not null and product(mags) id is: " + mags.getId() + " by " + principal.getName());
					product.setProductMagWheels(null);
					productMagWheelsService.removeMagWheels(dummyProduct.getMagsId());
				}

			} catch (Exception e) {
				// TODO: handle exception
				logger.info(e.getMessage());
				logger.info("error");
			}
		}

		product = productService.saveProduct(product);
		ModelAndView mnv = new ModelAndView("redirect:/admin/product/SUCCESS");
		return mnv;
	}

	// change to magwheels only
//	@RequestMapping(value = "/view-products-gallery", method = RequestMethod.GET)
//	public ModelAndView viewProductsGallery(HttpServletRequest request) {
//		logger.info("entering view products index");
//		ModelAndView mnv = new ModelAndView("admin.viewproductsgallery.index");
//		ArrayList<ImageDirectoryModel> productURLs = new ArrayList<ImageDirectoryModel>();
		// for (Product p : productService.getAllProducts()) {
		// ImageDirectoryModel imageDirectoryModel = new ImageDirectoryModel();
		// if (p.getImageUrl() == null) {
		// imageDirectoryModel.setImageDir("/images/bioniclogo.jpg");
		// imageDirectoryModel
		// .setThumbnailDir("/images/bionicthumbnail.jpg");
		// productURLs.add(imageDirectoryModel);
		// } else {
		// String productURL = "/product-catalogue/" + p.getCode()
		// + ".png";
		// String thumbnailURL = "/product-catalogue/" + p.getCode()
		// + "_thumbnail.png";
		// imageDirectoryModel.setImageDir(productURL);
		// imageDirectoryModel.setThumbnailDir(thumbnailURL);
		//
		// productURLs.add(imageDirectoryModel);
		// }
		// }
		// mnv.addObject("urls", productURLs);
//		return mnv;
//	}

	@RequestMapping(value = "/view-products", method = RequestMethod.GET)
	public ModelAndView viewAllProducts(HttpServletRequest request, Principal principal) {
		logger.info("viewing all products by " + principal.getName());
		ModelAndView mnv = new ModelAndView("admin.viewproducts.index");
		mnv.addObject("products", productService.getAllProducts());
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

		// ModelAndView mnv = new ModelAndView("admin.product.index");
		// mnv.addObject("brands", brands);

		return brandNames;

	}

	// change in the future
	@RequestMapping(value = "/enable-tire")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER', 'TIRE_MAGS_PERSON')")
	public @ResponseBody void enableTire(HttpSession session, Principal principal) {
		
		String productType = "TIRE";

		session.setAttribute("productType", productType);

		String sessionProductType = (String) session.getAttribute("productType");

		logger.info("session attribute product type: " + sessionProductType + " by " + principal.getName());

	}

	@RequestMapping(value = "/enable-mags")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER', 'TIRE_MAGS_PERSON')")
	public @ResponseBody void enableMags(HttpSession session, Principal principal) {
		
		String productType = "MAGWHEEL";

		session.setAttribute("productType", productType);

		String sessionProductType = (String) session.getAttribute("productType"); 
		
		logger.info("session attribute product type: " + sessionProductType + " by " + principal.getName());

	}

	@RequestMapping(value = "/disable-all")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','CASHIER','ROLE_SYSTEM_MANAGER', 'TIRE_MAGS_PERSON')")
	public @ResponseBody void disableAll(HttpSession session, Principal principal) {
		
		String productType = "GENERIC";

		session.setAttribute("productType", productType);

		String sessionProductType = (String) session.getAttribute("productType"); 
		
		logger.info("session attribute product type: " + sessionProductType + " by " + principal.getName());

	}

	@RequestMapping(value = "/barcode/{id}", method = RequestMethod.GET)
	public ModelAndView getBarcode(@PathVariable("id") Long id, HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView("admin.barcode");
		
		List<DummyProduct> dps = new ArrayList<DummyProduct>();
		
		DummyProduct dp = new DummyProduct();
		
		Product product = productService.getProduct(id);
		
		dp.setBrandName(product.getBrand().getBrandName());
		dp.setProductName(product.getProductName());
		dp.setDescription(convertCode.getCostCode(inventoryService.getInventory(product.getInventoryId()).getCost()));
		dp.setCode(product.getCode());
		dp.setSrp(inventoryService.getInventory(product.getInventoryId()).getSrp());
		dp.setDataUri(product.getDataUri());
		
		dps.add(dp);
		
		mnv.addObject("products", dps);
		mnv.addObject("product", product);
		
		return mnv;
	}
	
	@RequestMapping(value = "/generate/batch-barcode", method = RequestMethod.GET)
	public ModelAndView generateBatchBarcodeIndex(HttpServletRequest request){
		ModelAndView mnv = new ModelAndView("admin.batchbarcode.index");
		mnv.addObject("categories", categoryService.getAllCategories());
		mnv.addObject("brands", brandService.getAllBrands());
		return mnv;
	}
	
	@RequestMapping(value = "/generate/batch-barcode-category/{id}", method = RequestMethod.GET)
	public ModelAndView generateBatchBarcodeByCategory(HttpServletRequest request, @PathVariable("id") Long id){
		ModelAndView mnv = new ModelAndView("admin.batchbarcode.category");
		
		List<DummyProduct> dps = new ArrayList<DummyProduct>();
		
		List<Product> bufferProducts = productService.getProductsByCategory(categoryService.getCategory(id));
		
		for(Product product : bufferProducts){
			DummyProduct dp = new DummyProduct();
			dp.setBrandName(product.getBrand().getBrandName());
			dp.setProductName(product.getProductName());
			dp.setDescription(convertCode.getCostCode(inventoryService.getInventory(product.getInventoryId()).getCost()));
			dp.setCode(product.getCode());
			dp.setSrp(inventoryService.getInventory(product.getInventoryId()).getSrp());
			dp.setDataUri(product.getDataUri());
			
			dps.add(dp);
		}
		
		mnv.addObject("products", dps);
		mnv.addObject("category", categoryService.getCategory(id).getCategoryName());
		
		
		return mnv;
	}
	
	@RequestMapping(value = "/generate/batch-barcode-brand/{id}", method = RequestMethod.GET)
	public ModelAndView generateBatchBarcodeByBrand(HttpServletRequest request, @PathVariable("id") Long id){
		ModelAndView mnv = new ModelAndView("admin.batchbarcode.brand");
		
		List<DummyProduct> dps = new ArrayList<DummyProduct>();
		
		List<Product> bufferProducts = productService.getProductsByBrand(brandService.getBrand(id));
		
		for(Product product : bufferProducts){
			DummyProduct dp = new DummyProduct();
			dp.setBrandName(product.getBrand().getBrandName());
			dp.setProductName(product.getProductName());
			dp.setDescription(convertCode.getCostCode(inventoryService.getInventory(product.getInventoryId()).getCost()));
			dp.setCode(product.getCode());
			dp.setSrp(inventoryService.getInventory(product.getInventoryId()).getSrp());
			dp.setDataUri(product.getDataUri());
			
			dps.add(dp);
		}
		
		mnv.addObject("products", dps);
		mnv.addObject("brand", brandService.getBrand(id).getBrandName());
		
		return mnv;
	}
}
