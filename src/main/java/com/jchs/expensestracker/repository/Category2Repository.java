package com.jchs.expensestracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;

public interface Category2Repository extends JpaRepository<Category2, Long> {

	@Query("SELECT c FROM Category2 c ORDER BY c.parent.description, c.description")
	List<Category2> findAll();

	Category2 findByParentAndDescription(Category1 parent, String description);

	@Query("SELECT c FROM Category2 c WHERE c.parent = :parent ORDER BY c.parent.description, c.description")
	List<Category2> findAllByParent(@Param("parent") Category1 parent);
	
}
