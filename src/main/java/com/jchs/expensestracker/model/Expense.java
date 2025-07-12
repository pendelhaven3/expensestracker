package com.jchs.expensestracker.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Expense {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private Category1 category1;

	@OneToOne
	private Category2 category2;

	@OneToOne
	private Category3 category3;

	private Date dateEntered;

	@Column(columnDefinition = "date")
	private Date dateOfTransaction;

	private String particulars;
	private String tin;
	private String supplier;
	private String address;
	private BigDecimal amount;
	private String salesInvoiceNumber;
	private String typeOfReceipt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDateEntered() {
		return dateEntered;
	}

	public void setDateEntered(Date dateEntered) {
		this.dateEntered = dateEntered;
	}

	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSalesInvoiceNumber() {
		return salesInvoiceNumber;
	}

	public void setSalesInvoiceNumber(String salesInvoiceNumber) {
		this.salesInvoiceNumber = salesInvoiceNumber;
	}

	public String getTypeOfReceipt() {
		return typeOfReceipt;
	}

	public void setTypeOfReceipt(String typeOfReceipt) {
		this.typeOfReceipt = typeOfReceipt;
	}
	
}
