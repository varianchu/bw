package com.altostratus.bionicwheels.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.ProductMagWheels;
import com.altostratus.bionicwheels.model.Supplier;

public interface ProductMagWheelsRepository extends
		JpaRepository<ProductMagWheels, Long> {

	List<ProductMagWheels> findByPcd(String pcd);

	List<ProductMagWheels> findBySize(String size);

	List<ProductMagWheels> findBySizeAndPcd(String size, String pcd);

	List<ProductMagWheels> findByProduct_Brand(Brand brand);

	List<ProductMagWheels> findByProduct_Supplier(Supplier supplier);

}
