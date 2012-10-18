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

	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	Boolean isTire = false;
	Boolean isMags = false;

	private List<Inventory> productInventories = null;

	private String errorMessage = null;
	private String successMessage = null;

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ModelAndView productIndex(HttpServletRequest request) {
		logger.info("entering product index");
		ModelAndView mnv = new ModelAndView("admin.product.index");
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
			List<Brand> brands = brandService.getAllBrandSuppliers(suppliers
					.get(0));
			for (Brand brand : brands) {
				brandNames.add(brand.getBrandName());
			}
		}
		mnv.addObject("productInventories", productInventories);
		mnv.addObject("brands", brandNames);
		mnv.addObject("products", productService.getAllProducts());
		mnv.addObject("SUCCESS_MESSAGE", successMessage);
		mnv.addObject("ERROR_MESSAGE", errorMessage);
		successMessage = null;
		errorMessage = null;
		return mnv;
	}

	@RequestMapping(value = "/product/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editProduct(@PathVariable("id") Long productId,
			HttpServletRequest request) {
		logger.info("Editing product id: " + productId.toString());

		DummyProduct dummyProduct = new DummyProduct();
		Product product = productService.getProduct(productId);
		Inventory inventory = inventoryService.getInventory(product
				.getInventoryId());

		// testing ok
		logger.info("product tire is: " + product.getProductTire());
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
		logger.info("ok");

		// logger.info("id is: " + product.getProductMagWheels().getId());
		// // dummyProduct.setMagsId(product.getProductMagWheels().getId());
		// logger.info("mags id is: " + dummyProduct.getMagsId());
		dummyProduct.setQty(inventory.getQty());
		dummyProduct.setSrp(inventory.getSrp());
		dummyProduct.setCost(inventory.getCost());

		ModelAndView mnv = new ModelAndView("admin.product.index");
		mnv.addObject("product", dummyProduct);
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

		// if edited do the necessary changes
		if (product.getProductTire() != null) {
			dummyProduct.setTireId(product.getProductTire().getId());
			dummyProduct.setCrossSectionWidth(product.getProductTire()
					.getCrossSectionWidth());
			dummyProduct.setProfile(product.getProductTire().getProfile());
			dummyProduct.setConstruction(product.getProductTire()
					.getConstruction());
			dummyProduct.setDiameter(product.getProductTire().getDiameter());
			isTire = true;
			isMags = false;
		}

		// logger.info("product mag wheels id: "
		// + product.getProductmagWheels().getId());

		if (product.getProductMagWheels() != null) {
			dummyProduct.setMagsId(product.getProductMagWheels().getId());
			dummyProduct.setStyle(product.getProductMagWheels().getStyle());
			dummyProduct.setSize(product.getProductMagWheels().getSize());
			dummyProduct.setSpokes(product.getProductMagWheels().getSpokes());
			dummyProduct.setPcd(product.getProductMagWheels().getPcd());
			dummyProduct.setOffset(product.getProductMagWheels().getOffset());
			dummyProduct.setFinish(product.getProductMagWheels().getFinish());
			logger.info("all set for mags!");
			isMags = true;
			isTire = false;
		}

		productInventories = product.getInventories();
		mnv.addObject("productInventories", product.getInventories());
		mnv.addObject("brands", brandNames);
		mnv.addObject("tireconstruction", ProductTire.CONSTRUCTION.values());
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

		if (isMags == false && isTire == false) {

			productValidator.validate(dummyProduct, result);

			if (result.hasErrors()) {
				ModelAndView mnv = new ModelAndView("admin.product.index");
				mnv.addObject("product", new DummyProduct());
				mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
				mnv.addObject("categories", categoryService.getAllCategories());
				mnv.addObject("suppliers", supplierService.getAllSuppliers());
				mnv.addObject("products", productService.getAllProducts());
				mnv.addObject("ERROR_MESSAGE",
						"Product was not saved. Please Check the fields.");
				List<Supplier> suppliers = supplierService.getAllSuppliers();
				List<String> brandNames = new ArrayList<String>();
				if (suppliers.size() == 0) {
					brandNames.add("");
				} else {
					List<Brand> brands = brandService
							.getAllBrandSuppliers(suppliers.get(0));
					for (Brand brand : brands) {
						brandNames.add(brand.getBrandName());
					}
				}
				mnv.addObject("brands", brandNames);
				return mnv;
			}
		}
		if (isTire == true) {

			productTireValidator.validate(dummyProduct, result);
			if (result.hasErrors()) {
				ModelAndView mnv = new ModelAndView("admin.product.index");
				mnv.addObject("product", new DummyProduct());
				mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
				mnv.addObject("categories", categoryService.getAllCategories());
				List<Supplier> suppliers = supplierService.getAllSuppliers();
				mnv.addObject("suppliers", suppliers);
				mnv.addObject("products", productService.getAllProducts());
				mnv.addObject("tireconstruction",
						ProductTire.CONSTRUCTION.values());
				List<String> brandNames = new ArrayList<String>();
				if (suppliers.size() == 0) {
					brandNames.add("");
				} else {
					List<Brand> brands = brandService
							.getAllBrandSuppliers(suppliers.get(0));
					for (Brand brand : brands) {
						brandNames.add(brand.getBrandName());
					}
				}
				mnv.addObject("brands", brandNames);
				mnv.addObject("ERROR_MESSAGE",
						"Product was not saved. Please Check the fields of Tire and Product.");
				return mnv;
			}

		}

		if (isMags == true) {

			productMagsValidator.validate(dummyProduct, result);
			if (result.hasErrors()) {
				ModelAndView mnv = new ModelAndView("admin.product.index");
				mnv.addObject("product", new DummyProduct());
				mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
				mnv.addObject("categories", categoryService.getAllCategories());
				List<Supplier> suppliers = supplierService.getAllSuppliers();
				mnv.addObject("suppliers", suppliers);
				mnv.addObject("products", productService.getAllProducts());
				mnv.addObject("tireconstruction",
						ProductTire.CONSTRUCTION.values());
				List<String> brandNames = new ArrayList<String>();
				if (suppliers.size() == 0) {
					brandNames.add("");
				} else {
					List<Brand> brands = brandService
							.getAllBrandSuppliers(suppliers.get(0));
					for (Brand brand : brands) {
						brandNames.add(brand.getBrandName());
					}
				}
				mnv.addObject("brands", brandNames);
				mnv.addObject("ERROR_MESSAGE",
						"Product was not saved. Please Check the fields of Mag Wheels and Product.");
				return mnv;
			}

		}

		Product product = new Product();

		logger.info("brandName from dummy product is: "
				+ dummyProduct.getBrandName().toString());

		if (dummyProduct.getId() != null) {
			product.setId(dummyProduct.getId());
			product.setInventoryId(dummyProduct.getInventoryId());
		}

		product.setCode(dummyProduct.getCode());
		logger.info(dummyProduct.getCode());
		product.setTotalQty(dummyProduct.getTotalQty());
		logger.info(dummyProduct.getTotalQty().toString());
		product.setProductName(dummyProduct.getProductName());
		logger.info(dummyProduct.getProductName());
		product.setDescription(dummyProduct.getDescription());
		logger.info("description: " + dummyProduct.getDescription());
		product.setUnitOfMeasure(dummyProduct.getUnitOfMeasure());
		logger.info(dummyProduct.getUnitOfMeasure());
		product.setCategory(categoryService.getCategory(dummyProduct
				.getCategoryId()));
		logger.info("Category id: " + dummyProduct.getCategoryId());
		product.setSupplier(supplierService.getSupplier(dummyProduct
				.getSupplierId()));
		logger.info("Supplier id: " + dummyProduct.getSupplierId());
		Brand brand = brandService.getBrandByBrandName(dummyProduct
				.getBrandName());
		product.setBrand(brand);

		logger.info("brand is " + brand.getBrandName());

		// product.setImageUrl(fileName);
		// product.setThumbnailUrl(fileNameThumbnail);

		product = (Product) productService.saveProduct(product);
		logger.info("product saved! YEY!");

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
						+ "/barcode-catalogue/barcode-" + product.getCode()
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

		// change after, product.getInventories... no need additional method in
		// inventory service
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
			// do checkbox! error here!
			logger.info("testing pls. help!" + inventories.size());
			logger.info("get new price: " + dummyProduct.getNewPrice());
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
			}
			if (dummyProduct.getNewPrice() == null
					|| dummyProduct.getNewPrice() == false) {
				// logger.info("HELLO THERE!");
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
		// product = productService.saveProduct(product);

		// add new productValidatorTire here additional fields :)
		if (isTire == true) {

			if (dummyProduct.getMagsId() != null) {
				// Long id = product.getProductMagWheels().getId();
				product.setProductMagWheels(null);
				productMagWheelsService.removeMagWheels(dummyProduct
						.getMagsId());
			}

			ProductTire tire;
			if (dummyProduct.getTireId() != null) {
				tire = productTireService.getProductTire(dummyProduct
						.getTireId());
				tire.setId(dummyProduct.getTireId());
				logger.info("tire is not null and product id is: "
						+ tire.getId());
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
			logger.info("Tire is saved!");
		}

		// add new productValidatorMags here additional fields :)
		if (isMags == true) {
			// error here not deleting the tire

			// if (product.getProductTire() != null) {
			// logger.info("product tire is not null");
			// Long id = product.getProductTire().getId();
			// logger.info("product tire's id is " + id);
			// product.setProductTire(null);
			// logger.info("product tire's id should be set to null and it is: "
			// + product.getProductTire());
			// productTireService.removeProductTire(id);
			// }

			if (dummyProduct.getTireId() != null) {
				logger.info("product tire: " + product.getProductTire());
				ProductTire tire = productTireService
						.getProductTire(dummyProduct.getTireId());
				logger.info("tire is " + tire);
				product.setProductTire(null);
				productTireService.removeProductTire(tire);
			}

			ProductMagWheels mags;
			if (dummyProduct.getMagsId() != null) {
				mags = productMagWheelsService.getMagWheels(dummyProduct
						.getMagsId());
				mags.setId(dummyProduct.getMagsId());
				logger.info("mags is not null and product id is: "
						+ mags.getId());
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

			MultipartFile file = dummyProduct.getFileData();
			String fileName = null;
			String fileNameThumbnail = null;
			InputStream inputStream = null;
			OutputStream outputStream = null;

			logger.info("brandName from dummy product is: "
					+ dummyProduct.getBrandName().toString());

			try {

				if (file.getSize() > 0) {
					inputStream = file.getInputStream();
					if (file.getSize() > 10000000) {
						logger.info("Failed to upload image.");
						ModelAndView mnv = new ModelAndView(
								"admin.product.index");
						mnv.addObject("product", new DummyProduct());
						mnv.addObject("uoms", Product.UNIT_OF_MEASURE.values());
						mnv.addObject("categories",
								categoryService.getAllCategories());
						mnv.addObject("suppliers",
								supplierService.getAllSuppliers());
						mnv.addObject("products",
								productService.getAllProducts());
						mnv.addObject("fail", "failed to upload image.");
						mnv.addObject("tireconstruction",
								ProductTire.CONSTRUCTION.values());
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
							ImageIO.read(new File(fileName)).getScaledInstance(
									75, 75, Image.SCALE_SMOOTH), 0, 0, null);
					ImageIO.write(img, "png", new File(fileNameThumbnail));

				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.info(e.getMessage());
				e.printStackTrace();
			}
			mags.setImageUrl(fileName);
			mags.setThumbnailUrl(fileNameThumbnail);
			mags = productMagWheelsService.saveMagWheels(mags);
			logger.info("Mags is saved!");
		}

		if (isTire == false && isMags == false) {
			logger.info("demoted to generic product");

			try {
				// Long id = product.getProductTire().getId();
				ProductTire tire;
				ProductMagWheels mags;
				if (dummyProduct.getTireId() != null) {
					tire = productTireService.getProductTire(dummyProduct
							.getTireId());
					logger.info("tire is not null and product(tire) id is: "
							+ tire.getId());
					product.setProductTire(null);
					productTireService.removeProductTire(tire);
				}
				if (dummyProduct.getMagsId() != null) {
					mags = productMagWheelsService.getMagWheels(dummyProduct
							.getMagsId());
					logger.info("mags is not null and product(mags) id is: "
							+ mags.getId());
					product.setProductMagWheels(null);
					productMagWheelsService.removeMagWheels(dummyProduct
							.getMagsId());
				}

			} catch (Exception e) {
				// TODO: handle exception
				logger.info(e.getMessage());
				logger.info("error");
			}
		}
		product = productService.saveProduct(product);
		ModelAndView mnv = new ModelAndView("redirect:/admin/product");
		successMessage = "Successfully saved " + product.getProductName()
				+ ". Kindly check the view products section for details.";
		isTire = false;
		isMags = false;
		return mnv;
	}

	// change to magwheels only
	@RequestMapping(value = "/view-products-gallery", method = RequestMethod.GET)
	public ModelAndView viewProductsGallery(HttpServletRequest request) {
		logger.info("entering view products index");
		ModelAndView mnv = new ModelAndView("admin.viewproductsgallery.index");
		ArrayList<ImageDirectoryModel> productURLs = new ArrayList<ImageDirectoryModel>();
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
		return mnv;
	}

	@RequestMapping(value = "/view-products", method = RequestMethod.GET)
	public ModelAndView viewAllProducts(HttpServletRequest request) {
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
	public @ResponseBody
	void enableTire() {
		logger.info("tire is enabled.");
		this.isTire = true;
		this.isMags = false;
		logger.info("isTire: " + isTire);

	}

	@RequestMapping(value = "/enable-mags")
	public @ResponseBody
	void enableMags() {
		logger.info("mags is enabled.");
		this.isMags = true;
		this.isTire = false;
		logger.info("isMags: " + isMags);

	}

	@RequestMapping(value = "/disable-all")
	public @ResponseBody
	void disableAll() {
		logger.info("disabled all.");
		this.isTire = false;
		this.isMags = false;
		logger.info("isTire: " + isTire);
		logger.info("isMags: " + isMags);

	}

	@RequestMapping(value = "/barcode/{barcodeName}", method = RequestMethod.GET)
	public ModelAndView getBarcode(
			@PathVariable("barcodeName") String barcodeName,
			HttpServletRequest request) {
		ModelAndView mnv = new ModelAndView("admin.barcode.index");
		mnv.addObject("barcode", "/barcode-catalogue/barcode-" + barcodeName
				+ ".jpg");
		return mnv;
	}
}
