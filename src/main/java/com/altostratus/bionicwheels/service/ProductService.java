package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.model.Supplier;

public interface ProductService {
	public Product saveProduct(Product product);

	public Product getProduct(Long id);

	public List<Product> getAllProducts();

	public List<Product> getProductsByCode(String code);

	public List<Product> getProductsByCategory(Category category);

	public List<Product> getProductsBySupplier(Supplier supplier);

	public List<Product> getProductsBelowNumber(Double number);

	public List<Product> getProductsAboveNumber(Double number);

	public List<Product> getProductsByBrand(Brand brand);

	public void removeProduct(Long id);

	public List<Product> getProductsByCategoryBelowNumber(Category category,
			Double number);

	public List<Product> getProductsByCategoryAboveNumber(Category category,
			Double number);

	public List<Product> getProductsByBrandBelowNumber(Brand brand,
			Double number);

	public List<Product> getProductsByBrandAboveNumber(Brand brand,
			Double number);

	public List<Product> getProductsBySupplierBelowNumber(Supplier supplier,
			Double number);

	public List<Product> getProductsBySupplierAboveNumber(Supplier supplier,
			Double number);

	public List<Product> getProductsByProductName(String productName);
}
