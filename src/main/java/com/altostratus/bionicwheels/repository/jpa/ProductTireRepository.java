package com.altostratus.bionicwheels.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.ProductTire;
import com.altostratus.bionicwheels.model.Supplier;

public interface ProductTireRepository extends JpaRepository<ProductTire, Long> {

	List<ProductTire> findByCrossSectionWidthAndProfileAndDiameter(
			String crossSectionWidth, Integer profile, String diameter);

	List<ProductTire> findByProduct_BrandAndProduct_TotalQtyGreaterThan(
			Brand brand, Double totalQty);

	List<ProductTire> findByProduct_Brand(Brand brand);

	List<ProductTire> findByProduct_Supplier(Supplier suppler);

	List<ProductTire> findByCrossSectionWidth(String crossSectionWidth);

	List<ProductTire> findByProfile(Integer profile);

	List<ProductTire> findByDiameter(String diameter);
}
