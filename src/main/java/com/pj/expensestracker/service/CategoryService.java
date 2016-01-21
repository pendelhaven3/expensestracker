package com.pj.expensestracker.service;

import java.util.List;

import com.pj.expensestracker.model.Category1;

public interface CategoryService {

	List<Category1> getAllLevel1Categories();

	Category1 getCategory1(Long id);

	void save(Category1 category1);

	Category1 findCategory1ByDescription(String description);

	void delete(Category1 category1);
	
}
