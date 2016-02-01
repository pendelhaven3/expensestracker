package com.jchs.expensestracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	@Query("select e from Expense e"
			+ " order by e.category1.description,"
			+ " e.category2.description,"
			+ " e.category3.description,"
			+ " e.dateEntered desc")
	List<Expense> findAll();

	List<Expense> findByCategory3(Category3 category3);
	
}
