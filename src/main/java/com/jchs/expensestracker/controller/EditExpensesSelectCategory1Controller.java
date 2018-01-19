package com.jchs.expensestracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.service.CategoryService;

import javafx.fxml.FXML;

@Controller
public class EditExpensesSelectCategory1Controller extends AbstractController {

	@Autowired
	private CategoryService categoryService;
	
	@FXML
	private AppTableView<Category1> categoriesTable;

	@Override
	public void updateDisplay() {
		stageController.setTitle("Edit Expenses - Select Category 1");
		categoriesTable.setItemsThenFocus(categoryService.getAllLevel1Categories());
		categoriesTable.setDoubleClickAndEnterKeyAction(event -> selectCategory1());
	}

	private void selectCategory1() {
		stageController.showEditExpensesSelectCategory2Screen(categoriesTable.getSelectedItem());
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

}