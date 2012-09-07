package com.altostratus.bionicwheels.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

}
