package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.Supplier;

public interface SupplierService {
	public Supplier saveSupplier(Supplier supplier);

	public Supplier getSupplier(Long id);

	public List<Supplier> getAllSuppliers();

	public void removeSupplier(Long id);

	public Supplier getSupplierBySupplierName(String supplierName);
}
