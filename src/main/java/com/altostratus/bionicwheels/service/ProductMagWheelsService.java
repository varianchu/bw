package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.ProductMagWheels;
import com.altostratus.bionicwheels.model.Supplier;

public interface ProductMagWheelsService {

	public ProductMagWheels saveMagWheels(ProductMagWheels productMagWheels);

	public ProductMagWheels getMagWheels(Long id);

	public void removeMagWheels(Long id);

	public List<ProductMagWheels> getAllMagWheels();

	public List<ProductMagWheels> getAllMagWheelsBySizeAndPCD(String size,
			String pcd);

	public List<ProductMagWheels> getAllMagWheelsByPCD(String pcd);

	public List<ProductMagWheels> getAllMagWheelsBySize(String size);

	public List<ProductMagWheels> getAllMagWheelsByBrand(Brand brand);

	public List<ProductMagWheels> getAllMagWheelsBySupplier(Supplier supplier);

}
