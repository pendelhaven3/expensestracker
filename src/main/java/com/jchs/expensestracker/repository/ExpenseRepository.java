package com.jchs.expensestracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {

	@Query("SELECT e FROM Expense e"
			+ " LEFT JOIN e.category3 c3"
			+ " ORDER BY e.category1.description,"
			+ " e.category2.description,"
			+ " c3.description,"
			+ " e.dateEntered DESC")
	List<Expense> findAll();

	List<Expense> findByCategory3(Category3 category3);
	
}
