package com.altostratus.bionicwheels.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.Supplier;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	List<Brand> findBySupplier(Supplier supplier);

	Brand findByBrandName(String brandName);
}
