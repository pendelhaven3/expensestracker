package com.jchs.expensestracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;

public interface Category3Repository extends JpaRepository<Category3, Long> {

	@Query("SELECT c FROM Category3 c ORDER BY c.parent.parent.description, c.parent.description, c.description")
	List<Category3> findAll();

	Category3 findByParentAndDescription(Category2 parent, String description);
	
}
