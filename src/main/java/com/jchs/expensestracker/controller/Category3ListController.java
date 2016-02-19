package com.jchs.expensestracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.service.CategoryService;

import javafx.fxml.FXML;

@Controller
public class Category3ListController extends AbstractController {

	@Autowired private CategoryService categoryService;
	
	@FXML private AppTableView<Category3> categoriesTable;

	@Override
	public void updateDisplay() {
		stageController.setTitle("Category 3 List");
		categoriesTable.getItems().setAll(categoryService.getAllLevel3Categories());
		categoriesTable.setDoubleClickAndEnterKeyAction(event -> editSelectedCategory3());
	}

	private void editSelectedCategory3() {
		stageController.showEditCategory3Screen(categoriesTable.getSelectedItem());
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

	@FXML
	public void addCategory3() {
		stageController.showAddCategory3Screen();
	}

}