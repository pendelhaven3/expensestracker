package com.jchs.expensestracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.Parameter;
import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.service.CategoryService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

@Controller
public class EditExpensesSelectCategory3Controller extends AbstractController {

	@Autowired
	private CategoryService categoryService;
	
	@FXML
	private AppTableView<Category3> categoriesTable;
	
    @FXML
    private ToolBar toolBar;
	
	@Parameter
	private Category1 category1;

    @Parameter
    private Category2 category2;

	@Override
	public void updateDisplay() {
		stageController.setTitle("Edit Expenses - Select Category 3");
		categoriesTable.setItemsThenFocus(getCategories());
		categoriesTable.setDoubleClickAndEnterKeyAction(event -> selectCategory3());
		
        Button category1ToolBarButton = new Button(category1.getDescription());
        category1ToolBarButton.setOnMouseClicked(e -> doOnBack());
        toolBar.getItems().add(category1ToolBarButton);
        
        Button category2ToolBarButton = new Button(category2.getDescription());
        toolBar.getItems().add(category2ToolBarButton);
	}

	private List<Category3> getCategories() {
	    List<Category3> categories = categoryService.findAllCategory3ByParent(category2);
	    categories.add(noCategory3());
	    return categories;
	}

    private Category3 noCategory3() {
        Category3 category = new Category3();
        category.setParent(category2);
        category.setDescription("(No Category)");
        return category;
    }

    private void selectCategory3() {
		stageController.showEditExpensesSelectScreen(category1, category2, categoriesTable.getSelectedItem());
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

}