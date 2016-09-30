package com.jchs.expensestracker.service;

import java.util.List;

import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.model.ExpenseSearchCriteria;

public interface ExpenseService {

	List<Expense> getAllExpenses();

	Expense getExpense(Long id);

	void save(Expense expense);

	void delete(Expense expense);

	List<Expense> searchExpenses(ExpenseSearchCriteria criteria);
	
}
