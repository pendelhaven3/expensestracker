package com.jchs.expensestracker.model.search;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.jchs.expensestracker.model.Expense;

public class ExpenseSpecifications extends BaseSpecifications {

	public static Specification<Expense> withDateOfTransactionGreaterThanOrEqual(Date date) {
		return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("dateOfTransaction"), date);
	}
	
	public static Specification<Expense> withDateOfTransactionLessThanOrEqual(Date date) {
		return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("dateOfTransaction"), date);
	}
	
}
