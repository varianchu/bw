package com.altostratus.bionicwheels.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.ServiceRendered;

public interface ServiceRenderedRepository extends JpaRepository<ServiceRendered, Long> {
	
	List<ServiceRendered> findByServiceMadePart(String serviceRenderedName);

}
