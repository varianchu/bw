package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.repository.jpa.BrandRepository;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	BrandRepository brandRepository;

	@Override
	public Brand saveBrand(Brand brand) {
		// TODO Auto-generated method stub
		return brandRepository.save(brand);
	}

	@Override
	public Brand getBrand(Long id) {
		// TODO Auto-generated method stub
		return brandRepository.findOne(id);
	}

	@Override
	public List<Brand> getAllBrands() {
		// TODO Auto-generated method stub
		return brandRepository.findAll();
	}

	@Override
	public void removeBrand(Long id) {
		// TODO Auto-generated method stub
		brandRepository.delete(id);
	}

	@Override
	public List<Brand> getAllBrandSuppliers(Supplier supplier) {
		// TODO Auto-generated method stub
		return brandRepository.findBySupplier(supplier);
	}

	@Override
	public Brand getBrandByBrandName(String brandName) {
		// TODO Auto-generated method stub
		return brandRepository.findByBrandName(brandName);
	}
}
