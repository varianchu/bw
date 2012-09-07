package com.altostratus.bionicwheels.service;

import com.altostratus.bionicwheels.model.Settings;

public interface SettingsService {

	public Settings saveSettings(Settings settings);

	public Settings getSettings(Long id);

}
