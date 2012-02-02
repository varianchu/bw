package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.repository.jpa.ProductRepository;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService
{
	@PersistenceContext(unitName="persistenceUnit")
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
		productRepository.delete(id);
	}

}
