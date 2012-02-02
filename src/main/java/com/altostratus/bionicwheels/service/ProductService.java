package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.model.Product;

public interface ProductService
{
	public Product saveProduct(Product product);
	public Product getProduct(Long id);
	public List<Product> getAllProducts();
	public List<Product> getProductsByCode(String code);
	public List<Product> getProductsByCategory(Category category);
	public void removeProduct(Long id);
}
