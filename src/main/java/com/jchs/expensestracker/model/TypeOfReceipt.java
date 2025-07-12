package com.jchs.expensestracker.model;

import java.util.Arrays;
import java.util.List;

public class TypeOfReceipt {

	public static List<String> getValues() {
		return Arrays.asList(
				"Acknowledgement Receipt",
				"Billing Statement",
				"Cash Sales Invoice",
				"Charge Sales Invoice",
				"Collection Receipt",
				"Delivery Receipt",
				"Generic Receipt",
				"Non-Vat Invoice",
				"Official Receipt",
				"Sales Invoice",
				"Vat Invoice",
				"Order Slip",
				"Provisional Receipt"
		);
	}
	
}
