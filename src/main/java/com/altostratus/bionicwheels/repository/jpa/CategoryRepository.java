package com.altostratus.bionicwheels.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altostratus.bionicwheels.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}