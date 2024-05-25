package com.jchs.expensestracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.gui.component.AppDatePicker;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.ExpenseSearchCriteria;
import com.jchs.expensestracker.service.CategoryService;
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
	
	@Autowired private CategoryService categoryService;

	@Override
	public void updateDisplay() {
		category1ComboBox.getItems().add(new Category1());
		category1ComboBox.getItems().addAll(categoryService.getAllLevel1Categories());
		
		category1ComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			category2ComboBox.getItems().clear();
			category3ComboBox.getItems().clear();
			
			if (newValue.getId() != null) {
				category2ComboBox.getItems().addAll(categoryService.findAllCategory2ByParent(newValue));				
			} else {
				category2ComboBox.getItems().clear();
			}
		});
		
		category2ComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			category3ComboBox.getItems().clear();
			category3ComboBox.getItems().addAll(categoryService.findAllCategory3ByParent(newValue));				
		});
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
		
		Category1 category1 = category1ComboBox.getValue();
		if (category1 != null && category1.getId() != 0) {
			criteria.setCategory1(category1);
			criteria.setCategory2(category2ComboBox.getValue());
			criteria.setCategory3(category3ComboBox.getValue());
		}
		
		criteria.setReport(true);
		
		stageController.showSearchExpensesListScreen(criteria);
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

}
