package com.pj.expensestracker.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.expensestracker.model.Category1;
import com.pj.expensestracker.repository.Category1Repository;
import com.pj.expensestracker.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired private Category1Repository category1Repository;
	
	@Override
	public List<Category1> getAllLevel1Categories() {
		return category1Repository.findAll();
	}

	@Override
	public Category1 getCategory1(Long id) {
		return category1Repository.findOne(id);
	}

	@Transactional
	@Override
	public void save(Category1 category1) {
		category1Repository.save(category1);
	}

	@Override
	public Category1 findCategory1ByDescription(String description) {
		return category1Repository.findByDescription(description);
	}

	@Transactional
	@Override
	public void delete(Category1 category1) {
		category1Repository.delete(category1);
	}

}
