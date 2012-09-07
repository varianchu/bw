package com.altostratus.bionicwheels.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Settings;
import com.altostratus.bionicwheels.repository.jpa.SettingsRepository;

@Service("settingsService")
@Transactional
public class SettingsServiceImpl implements SettingsService {

	@PersistenceContext(unitName = "persistenceUnit")
	private EntityManager em;

	@Autowired
	SettingsRepository settingsRepository;

	@Override
	public Settings saveSettings(Settings settings) {
		// TODO Auto-generated method stub
		return settingsRepository.save(settings);
	}

	@Override
	public Settings getSettings(Long id) {
		// TODO Auto-generated method stub
		return settingsRepository.findOne(id);
	}
}
