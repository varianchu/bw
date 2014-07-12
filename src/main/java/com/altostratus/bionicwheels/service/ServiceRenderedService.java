package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.ServiceRendered;

public interface ServiceRenderedService {
	public ServiceRendered getServiceRendered(Long id);

	public void removeServiceRendered(Long id);

	public ServiceRendered saveServiceRendered(ServiceRendered serviceRendered);

	public List<ServiceRendered> getAllServiceRendered();
	
	public List<ServiceRendered> getServiceRenderedName(String serviceRenderedName);
	
}
