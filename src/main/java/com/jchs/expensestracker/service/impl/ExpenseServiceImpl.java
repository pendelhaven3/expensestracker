package com.jchs.expensestracker.service.impl;

import static com.jchs.expensestracker.model.search.BaseSpecifications.build;
import static com.jchs.expensestracker.model.search.ExpenseSpecifications.*;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.model.ExpenseSearchCriteria;
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

	@Override
	public List<Expense> searchExpenses(ExpenseSearchCriteria criteria) {
		Specifications<Expense> specifications = build();
		
        if (criteria.getCategory1() != null) {
            specifications = specifications.and(withCategory1(criteria.getCategory1()));
        }
        
        if (criteria.getCategory2() != null) {
            specifications = specifications.and(withCategory2(criteria.getCategory2()));
        }
        
        if (criteria.getCategory3() != null) {
            specifications = specifications.and(withCategory3(criteria.getCategory3()));
        }
        
        if (criteria.isCategory3Null()) {
            specifications = specifications.and(withCategory3Null());
        }
        
		if (criteria.getFromDate() != null) {
			specifications = specifications.and(withDateOfTransactionGreaterThanOrEqual(criteria.getFromDate()));
		}
		
		if (criteria.getToDate() != null) {
			specifications = specifications.and(withDateOfTransactionLessThanOrEqual(criteria.getToDate()));
		}
		
		return expenseRepository.findAll(specifications, new Sort(Direction.DESC, "dateOfTransaction"));
	}
	
}
