package com.jchs.expensestracker.model.search;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;

public class ExpenseSpecifications extends BaseSpecifications {

	public static Specification<Expense> withDateOfTransactionGreaterThanOrEqual(Date date) {
		return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("dateOfTransaction"), date);
	}
	
	public static Specification<Expense> withDateOfTransactionLessThanOrEqual(Date date) {
		return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("dateOfTransaction"), date);
	}
	
    public static Specification<Expense> withCategory1(Category1 category1) {
        return (root, query, builder) -> builder.equal(root.get("category1"), category1);
    }
    
    public static Specification<Expense> withCategory2(Category2 category2) {
        return (root, query, builder) -> builder.equal(root.get("category2"), category2);
    }
    
    public static Specification<Expense> withCategory3(Category3 category3) {
        return (root, query, builder) -> builder.equal(root.get("category3"), category3);
    }
    
    public static Specification<Expense> withCategory3Null() {
        return (root, query, builder) -> builder.isNull(root.get("category3"));
    }
    
}
