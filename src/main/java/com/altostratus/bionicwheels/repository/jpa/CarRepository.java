package com.altostratus.bionicwheels.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.altostratus.bionicwheels.model.Car;

@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
	List<Car> findByCarMake(String carMake);

	List<Car> findByNextChangeOilBetween(Date startDate, Date endDate);
}
