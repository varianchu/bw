package com.altostratus.bionicwheels.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.Brand;
import com.altostratus.bionicwheels.model.Category;
import com.altostratus.bionicwheels.model.Product;
import com.altostratus.bionicwheels.model.Supplier;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByCategory(Category category);

	List<Product> findByCode(String code);

	List<Product> findBySupplier(Supplier supplier);

	List<Product> findByTotalQtyLessThan(Double number);

	List<Product> findByTotalQtyGreaterThan(Double number);

	List<Product> findByBrand(Brand brand);
}
