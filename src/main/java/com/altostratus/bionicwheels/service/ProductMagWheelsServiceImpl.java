package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.ProductMagWheels;
import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.repository.jpa.ProductMagWheelsRepository;

@Service("productMagWheelService")
@Transactional
public class ProductMagWheelsServiceImpl implements ProductMagWheelsService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	ProductMagWheelsRepository productMagWheelsRepository;

	@Override
	public ProductMagWheels getMagWheels(Long id) {
		// TODO Auto-generated method stub
		return productMagWheelsRepository.findOne(id);
	}

	@Override
	public ProductMagWheels saveMagWheels(ProductMagWheels productMagWheels) {
		// TODO Auto-generated method stub
		return productMagWheelsRepository.save(productMagWheels);
	}

	@Override
	public List<ProductMagWheels> getAllMagWheels() {
		// TODO Auto-generated method stub
		return productMagWheelsRepository.findAll();
	}

	@Override
	public List<ProductMagWheels> getAllMagWheelsByBrand(Brand brand) {
		// TODO Auto-generated method stub
		return productMagWheelsRepository.findByProduct_Brand(brand);
	}

	@Override
	public List<ProductMagWheels> getAllMagWheelsBySupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		return productMagWheelsRepository.findByProduct_Supplier(supplier);
	}

	@Override
	public void removeMagWheels(Long id) {
		// TODO Auto-generated method stub
		productMagWheelsRepository.delete(id);
	}

	@Override
	public List<ProductMagWheels> getAllMagWheelsByPCD(String pcd) {
		// TODO Auto-generated method stub
		return productMagWheelsRepository.findByPcd(pcd);
	}

	@Override
	public List<ProductMagWheels> getAllMagWheelsBySize(String size) {
		// TODO Auto-generated method stub
		return productMagWheelsRepository.findBySize(size);
	}

	@Override
	public List<ProductMagWheels> getAllMagWheelsBySizeAndPCD(String size,
			String pcd) {
		// TODO Auto-generated method stub
		return productMagWheelsRepository.findBySizeAndPcd(size, pcd);
	}
}
