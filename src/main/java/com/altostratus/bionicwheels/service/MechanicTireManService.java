package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.MechanicTireMan;

public interface MechanicTireManService {
	public MechanicTireMan getMechanicTireMan(Long id);

	public MechanicTireMan saveMechanicTireMan(MechanicTireMan mechanicTireMan);

	public void removeMechanicTireMan(Long id);

	public List<MechanicTireMan> getAllMechanicTireMan();
}
