package com.jchs.expensestracker.gui.component;

import javafx.beans.NamedArg;
import javafx.scene.control.Button;

public class DeleteExpenseButton extends Button {

	private String tableId;
	
	public DeleteExpenseButton(@NamedArg("tableId") String tableId) {
		this.tableId = tableId;
	}

	public String getTableId() {
		return tableId;
	}
	
}
