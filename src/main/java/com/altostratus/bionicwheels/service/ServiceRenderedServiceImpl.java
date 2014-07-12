package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altostratus.bionicwheels.model.ServiceRendered;
import com.altostratus.bionicwheels.repository.jpa.ServiceRenderedRepository;

@Service("serviceRenderedService")
public class ServiceRenderedServiceImpl implements ServiceRenderedService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	ServiceRenderedRepository serviceRenderedRepository;

	@Override
	public List<ServiceRendered> getAllServiceRendered() {
		// TODO Auto-generated method stub
		return serviceRenderedRepository.findAll();
	}

	@Override
	public void removeServiceRendered(Long id) {
		// TODO Auto-generated method stub
		serviceRenderedRepository.delete(id);
	}

	@Override
	public ServiceRendered getServiceRendered(Long id) {
		// TODO Auto-generated method stub
		return serviceRenderedRepository.findOne(id);
	}

	@Override
	public ServiceRendered saveServiceRendered(ServiceRendered serviceRendered) {
		// TODO Auto-generated method stub
		return serviceRenderedRepository.save(serviceRendered);
	}
	
	@Override
	public List<ServiceRendered> getServiceRenderedName(String serviceRenderedName) {
		// TODO Auto-generated method stub
		return serviceRenderedRepository.findByServiceMadePart(serviceRenderedName);
	}
}
