package com.altostratus.bionicwheels.service;

import java.util.List;

import com.altostratus.bionicwheels.model.Category;

public interface CategoryService {
	public Category saveCategory(Category category);

	public Category getCategory(Long id);

	public List<Category> getAllCategories();

	public void removeCategory(Long id);

	public Category getCategoryByName(String categoryName);

}
