package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.model.InventoryTransactionProduct;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.repository.jpa.ProductRepository;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	private ProductRepository productRepository;

	@Override
	@Transactional
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product getProduct(Long id) {
		return productRepository.findOne(id);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCode(String code) {
		return productRepository.findByCode(code);
	}

	@Override
	public List<Product> getProductsByCategory(Category category) {
		return productRepository.findByCategory(category);
	}

	@Override
	public void removeProduct(Long id) {
		Product product = productRepository.findOne(id);
		List<InventoryTransactionProduct> itp = product
				.getInventoryTransactionProducts();
		if (itp.size() == 0) {
			productRepository.delete(id);
		} else {
			for (InventoryTransactionProduct inventoryTransactionProduct : itp) {
				inventoryTransactionProduct.setProduct(null);
			}
			productRepository.delete(id);
		}
	}

	@Override
	public List<Product> getProductsBySupplier(Supplier supplier) {
		return productRepository.findBySupplier(supplier);
	}

	@Override
	public List<Product> getProductsBelowNumber(Double number) {
		return productRepository.findByTotalQtyLessThan(number);
	}

	@Override
	public List<Product> getProductsAboveNumber(Double number) {
		return productRepository.findByTotalQtyGreaterThan(number);
	}

	@Override
	public List<Product> getProductsByBrand(Brand brand) {
		// TODO Auto-generated method stub
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByBrandAboveNumber(Brand brand,
			Double number) {
		// TODO Auto-generated method stub
		return productRepository.findByBrandAndTotalQtyGreaterThan(brand,
				number);
	}

	@Override
	public List<Product> getProductsByBrandBelowNumber(Brand brand,
			Double number) {
		// TODO Auto-generated method stub
		return productRepository.findByBrandAndTotalQtyLessThan(brand, number);
	}

	@Override
	public List<Product> getProductsByCategoryAboveNumber(Category category,
			Double number) {
		// TODO Auto-generated method stub
		return productRepository.findByCategoryAndTotalQtyGreaterThan(category,
				number);
	}

	@Override
	public List<Product> getProductsByCategoryBelowNumber(Category category,
			Double number) {
		// TODO Auto-generated method stub
		return productRepository.findByCategoryAndTotalQtyLessThan(category,
				number);
	}

	@Override
	public List<Product> getProductsBySupplierAboveNumber(Supplier supplier,
			Double number) {
		// TODO Auto-generated method stub
		return productRepository.findBySupplierAndTotalQtyGreaterThan(supplier,
				number);
	}

	@Override
	public List<Product> getProductsBySupplierBelowNumber(Supplier supplier,
			Double number) {
		// TODO Auto-generated method stub
		return productRepository.findBySupplierAndTotalQtyLessThan(supplier,
				number);
	}

	@Override
	public List<Product> getProductsByProductName(String productName) {
		// TODO Auto-generated method stub
		return productRepository.findByProductName(productName);
	}
}
