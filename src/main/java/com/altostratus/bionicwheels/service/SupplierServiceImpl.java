package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Supplier;
import com.altostratus.bionicwheels.repository.jpa.SupplierRepository;

@Service("supplierService")
@Transactional
public class SupplierServiceImpl implements SupplierService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	SupplierRepository supplierRepository;

	@Override
	@Transactional
	public Supplier saveSupplier(Supplier supplier) {
		// TODO Auto-generated method stub
		return supplierRepository.save(supplier);
	}

	@Override
	public List<Supplier> getAllSuppliers() {
		// TODO Auto-generated method stub
		return supplierRepository.findAll();
	}

	@Override
	public Supplier getSupplier(Long id) {
		// TODO Auto-generated method stub
		return supplierRepository.findOne(id);
	}

	@Override
	public void removeSupplier(Long id) {
		// TODO Auto-generated method stub
		supplierRepository.delete(id);
	}

	@Override
	public Supplier getSupplierBySupplierName(String supplierName) {
		// TODO Auto-generated method stub
		return supplierRepository.findBySupplierName(supplierName);
	}
}
