package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.ProductTire;
import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.repository.jpa.ProductTireRepository;

@Service("productTireService")
@Transactional
public class ProductTireServiceImpl implements ProductTireService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	ProductTireRepository productTireRepository;

	@Override
	public ProductTire getProductTire(Long id) {
		// TODO Auto-generated method stub
		return productTireRepository.findOne(id);
	}

	@Override
	public ProductTire saveProductTire(ProductTire productTire) {
		// TODO Auto-generated method stub
		return productTireRepository.save(productTire);
	}

	@Override
	public void removeProductTire(Long id) {
		// TODO Auto-generated method stub
		productTireRepository.delete(id);
	}

	@Override
	public List<ProductTire> getAllProductTires() {
		// TODO Auto-generated method stub
		return productTireRepository.findAll();
	}

	@Override
	public List<ProductTire> getProductTiresByCrossSectionWidthProfileDiameter(
			String crossSectionWidth, Integer profile, String diameter) {
		// TODO Auto-generated method stub
		return productTireRepository
				.findByCrossSectionWidthAndProfileAndDiameter(
						crossSectionWidth, profile, diameter);
	}

	@Override
	public List<ProductTire> getProductTiresByBrandTotalQty(Brand brand,
			Double totalQty) {
		// TODO Auto-generated method stub
		return productTireRepository.findByProduct_BrandAndProduct_TotalQty(
				brand, totalQty);
	}

	@Override
	public List<ProductTire> getProductTireByCrossSectionWidth(
			String crossSectionWidth) {
		// TODO Auto-generated method stub
		return productTireRepository.findByCrossSectionWidth(crossSectionWidth);
	}

	@Override
	public List<ProductTire> getProductTireByProfile(Integer profile) {
		// TODO Auto-generated method stub
		return productTireRepository.findByProfile(profile);
	}

	@Override
	public List<ProductTire> getProductTireByDiameter(String diameter) {
		// TODO Auto-generated method stub
		return productTireRepository.findByDiameter(diameter);
	}

	@Override
	public List<ProductTire> getProductTiresByBrand(Brand brand) {
		// TODO Auto-generated method stub
		return productTireRepository.findByProduct_Brand(brand);
	}

	@Override
	public List<ProductTire> getProductTiresBySupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		return productTireRepository.findByProduct_Supplier(supplier);
	}
}
