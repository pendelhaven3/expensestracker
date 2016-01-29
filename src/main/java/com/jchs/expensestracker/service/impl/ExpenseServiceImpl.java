package com.jchs.expensestracker.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.repository.ExpenseRepository;
import com.jchs.expensestracker.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired private ExpenseRepository expenseRepository;
	
	@Override
	public List<Expense> getAllExpenses() {
		return expenseRepository.findAll();
	}

	@Override
	public Expense getExpense(Long id) {
		return expenseRepository.findOne(id);
	}

	@Transactional
	@Override
	public void save(Expense expense) {
		expenseRepository.save(expense);
	}

	@Transactional
	@Override
	public void delete(Expense expense) {
		expenseRepository.delete(expense);
	}

}
