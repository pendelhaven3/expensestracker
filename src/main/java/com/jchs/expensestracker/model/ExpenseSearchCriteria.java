package com.jchs.expensestracker.model;

import java.util.Date;

public class ExpenseSearchCriteria {

	private Date fromDate;
	private Date toDate;
	private Category1 category1;
	private Category2 category2;
	private Category3 category3;
    private boolean category3Null;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Category1 getCategory1() {
		return category1;
	}

	public void setCategory1(Category1 category1) {
		this.category1 = category1;
	}

	public Category2 getCategory2() {
		return category2;
	}

	public void setCategory2(Category2 category2) {
		this.category2 = category2;
	}

	public Category3 getCategory3() {
		return category3;
	}

	public void setCategory3(Category3 category3) {
		this.category3 = category3;
	}

    public boolean isCategory3Null() {
        return category3Null;
    }

    public void setCategory3Null(boolean category3Null) {
        this.category3Null = category3Null;
    }

}
