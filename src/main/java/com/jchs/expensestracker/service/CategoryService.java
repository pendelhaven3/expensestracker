package com.jchs.expensestracker.service;

import java.util.List;

import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;

public interface CategoryService {

	List<Category1> getAllLevel1Categories();

	Category1 getCategory1(Long id);

	void save(Category1 category1);

	Category1 findCategory1ByDescription(String description);

	void delete(Category1 category1);

	Category2 getCategory2(Long id);

	List<Category2> getAllLevel2Categories();

	void save(Category2 category2);

	void delete(Category2 category2);

	Category2 findCategory2ByParentAndDescription(Category1 parent, String description);

	Category3 getCategory3(Long id);

	List<Category3> getAllLevel3Categories();

	void save(Category3 category3);

	void delete(Category3 category3);

	List<Category2> findAllCategory2ByParent(Category1 parent);

	Category3 findCategory3ByParentAndDescription(Category2 parent, String description);

	List<Category3> findAllCategory3ByParent(Category2 parent);
	
}
