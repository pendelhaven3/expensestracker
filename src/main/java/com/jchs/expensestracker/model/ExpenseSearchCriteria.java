package com.jchs.expensestracker.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseSearchCriteria {

	private Date fromDate;
	private Date toDate;
	private Category1 category1;
	private Category2 category2;
	private Category3 category3;
    private boolean category3Null;
    private boolean report;

}
