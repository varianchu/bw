package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.Supplier;

public interface BrandService {

	public Brand saveBrand(Brand brand);

	public Brand getBrand(Long id);

	public List<Brand> getAllBrands();

	public void removeBrand(Long id);

	public List<Brand> getAllBrandSuppliers(Supplier supplier);

	public Brand getBrandByBrandName(String brandName);

}
