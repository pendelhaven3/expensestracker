package com.jchs.expensestracker.gui.component;

import javafx.beans.NamedArg;
import javafx.scene.control.Button;

public class AddExpenseButton extends Button {

	private String category1Description;
	private String category2Description;
	private String category3Description;
	
	public AddExpenseButton(
			@NamedArg("category1") String category1Description,
			@NamedArg("category2") String category2Description,
			@NamedArg("category3") String category3Description
	) {
		this.category1Description = category1Description;
		this.category2Description = category2Description;
		this.category3Description = category3Description;
	}

	public String getCategory1Description() {
		return category1Description;
	}

	public String getCategory2Description() {
		return category2Description;
	}

	public String getCategory3Description() {
		return category3Description;
	}
	
}
