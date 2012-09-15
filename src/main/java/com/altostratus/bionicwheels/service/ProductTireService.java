package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.ProductTire;
import com.altostratus.bionicwheels.model.Supplier;

public interface ProductTireService {

	public ProductTire saveProductTire(ProductTire productTire);

	public ProductTire getProductTire(Long id);

	public List<ProductTire> getAllProductTires();

	public void removeProductTire(Long id);

	public List<ProductTire> getProductTiresByCrossSectionWidthProfileDiameter(
			String crossSectionWidth, Integer profile, String diameter);

	public List<ProductTire> getProductTiresByBrandTotalQty(Brand brand,
			Double totalQty);

	public List<ProductTire> getProductTiresByBrand(Brand brand);

	public List<ProductTire> getProductTiresBySupplier(Supplier supplier);

	public List<ProductTire> getProductTireByCrossSectionWidth(
			String crossSectionWidth);

	public List<ProductTire> getProductTireByProfile(Integer profile);

	public List<ProductTire> getProductTireByDiameter(String diameter);

}
