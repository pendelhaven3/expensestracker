package com.jchs.expensestracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jchs.expensestracker.model.Category1;

public interface Category1Repository extends JpaRepository<Category1, Long> {

	@Query("SELECT c FROM Category1 c ORDER BY c.description")
	List<Category1> findAll();
	
	Category1 findByDescription(String description);

}
