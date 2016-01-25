package com.jchs.expensestracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.service.CategoryService;

import javafx.fxml.FXML;

@Controller
public class Category2ListController extends AbstractController {

	@Autowired private CategoryService categoryService;
	
	@FXML private AppTableView<Category2> categoriesTable;

	@Override
	public void updateDisplay() {
		stageController.setTitle("Category 2 List");
		categoriesTable.getItems().setAll(categoryService.getAllLevel2Categories());
		categoriesTable.setDoubleClickAndEnterKeyAction(() -> editSelectedCategory1());
	}

	private void editSelectedCategory1() {
		stageController.showEditCategory2Screen(categoriesTable.getSelectedItem());
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

	@FXML
	public void addCategory2() {
		stageController.showAddCategory2Screen();
	}

}