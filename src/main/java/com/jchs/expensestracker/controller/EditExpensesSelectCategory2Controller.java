package com.jchs.expensestracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.Parameter;
import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.service.CategoryService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

@Controller
public class EditExpensesSelectCategory2Controller extends AbstractController {

	@Autowired
	private CategoryService categoryService;
	
	@FXML
	private AppTableView<Category2> categoriesTable;
	
    @FXML
    private ToolBar toolBar;
    
	@Parameter
	private Category1 category1;

	@Override
	public void updateDisplay() {
		stageController.setTitle("Edit Expenses - Select Category 2");
		categoriesTable.setItemsThenFocus(categoryService.findAllCategory2ByParent(category1));
		categoriesTable.setDoubleClickAndEnterKeyAction(event -> selectCategory2());
		
		Button category1ToolBarButton = new Button(category1.getDescription());
		toolBar.getItems().add(category1ToolBarButton);
	}

	private void selectCategory2() {
		stageController.showEditExpensesSelectCategory3Screen(category1, categoriesTable.getSelectedItem());
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

}