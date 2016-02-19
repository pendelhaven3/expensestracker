package com.jchs.expensestracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.service.CategoryService;

import javafx.fxml.FXML;

@Controller
public class Category1ListController extends AbstractController {

	@Autowired private CategoryService categoryService;
	
	@FXML private AppTableView<Category1> categoriesTable;

	@Override
	public void updateDisplay() {
		stageController.setTitle("Category 1 List");
		categoriesTable.getItems().setAll(categoryService.getAllLevel1Categories());
		categoriesTable.setDoubleClickAndEnterKeyAction(event -> editSelectedCategory1());
	}

	private void editSelectedCategory1() {
		stageController.showEditCategory1Screen(categoriesTable.getSelectedItem());
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

	@FXML
	public void addCategory1() {
		stageController.showAddCategory1Screen();
	}

}