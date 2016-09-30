package com.jchs.expensestracker.controller;

import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.gui.component.AppDatePicker;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.ExpenseSearchCriteria;
import com.jchs.expensestracker.util.DateUtil;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

@Controller
public class SearchExpensesCriteriaController extends AbstractController {

	@FXML private AppDatePicker fromDateDatePicker;
	@FXML private AppDatePicker toDateDatePicker;
	@FXML private ComboBox<Category1> category1ComboBox;
	@FXML private ComboBox<Category2> category2ComboBox;
	@FXML private ComboBox<Category3> category3ComboBox;
	
	@Override
	public void updateDisplay() {
	}

	@FXML
	public void searchExpenses() {
		ExpenseSearchCriteria criteria = new ExpenseSearchCriteria();
		if (fromDateDatePicker.getValue() != null) {
			criteria.setFromDate(DateUtil.toDate(fromDateDatePicker.getValue()));
		}
		if (toDateDatePicker.getValue() != null) {
			criteria.setToDate(DateUtil.toDate(toDateDatePicker.getValue()));
		}
		stageController.showSearchExpensesListScreen(criteria);
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

}
