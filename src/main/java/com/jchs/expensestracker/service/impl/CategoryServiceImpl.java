package com.jchs.expensestracker.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.repository.Category1Repository;
import com.jchs.expensestracker.repository.Category2Repository;
import com.jchs.expensestracker.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired private Category1Repository category1Repository;
	@Autowired private Category2Repository category2Repository;
	
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

	@Override
	public Category2 getCategory2(Long id) {
		return category2Repository.findOne(id);
	}

	@Override
	public List<Category2> getAllLevel2Categories() {
		return category2Repository.findAll();
	}

	@Transactional
	@Override
	public void save(Category2 category2) {
		category2Repository.save(category2);
	}

	@Transactional
	@Override
	public void delete(Category2 category2) {
		category2Repository.delete(category2);
	}

	@Override
	public Category2 findCategory2ByParentAndDescription(Category1 parent, String description) {
		return category2Repository.findByParentAndDescription(parent, description);
	}

}
