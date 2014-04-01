package com.altostratus.bionicwheels.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.MechanicTireMan;
import com.altostratus.bionicwheels.repository.jpa.MechanicTireManRepository;

@Service("mechanicTireManService")
@Transactional
public class MechanicTireManServiceImpl implements MechanicTireManService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	MechanicTireManRepository mechanicTireManRepository;

	@Override
	public List<MechanicTireMan> getAllMechanicTireMan() {
		// TODO Auto-generated method stub
		return mechanicTireManRepository.findAll();
	}

	@Override
	public MechanicTireMan getMechanicTireMan(Long id) {
		// TODO Auto-generated method stub
		return mechanicTireManRepository.findOne(id);
	}

	@Override
	public void removeMechanicTireMan(Long id) {
		// TODO Auto-generated method stub
		mechanicTireManRepository.delete(id);
	}

	@Override
	public MechanicTireMan saveMechanicTireMan(MechanicTireMan mechanicTireMan) {
		// TODO Auto-generated method stub
		return mechanicTireManRepository.save(mechanicTireMan);
	}

}
