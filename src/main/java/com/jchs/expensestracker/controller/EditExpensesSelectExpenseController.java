package com.jchs.expensestracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.Parameter;
import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.model.ExpenseSearchCriteria;
import com.jchs.expensestracker.service.ExpenseService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

@Controller
public class EditExpensesSelectExpenseController extends AbstractController {

	@Autowired
	private ExpenseService expenseService;
	
	@FXML
	private AppTableView<Expense> expensesTable;
	
    @FXML
    private ToolBar toolBar;
    
	@Parameter
	private Category1 category1;

    @Parameter
    private Category2 category2;

    @Parameter
    private Category3 category3;

	@Override
	public void updateDisplay() {
		stageController.setTitle("Edit Expenses - Select Expense");
		
        expensesTable.setItemsThenFocus(getExpenses()); 
		expensesTable.setDoubleClickAndEnterKeyAction(event -> selectExpense());
		Platform.runLater(() -> expensesTable.refresh());
		
        Button category1ToolBarButton = new Button(category1.getDescription());
        category1ToolBarButton.setOnMouseClicked(e -> {doOnBack(); doOnBack();});
        toolBar.getItems().add(category1ToolBarButton);
        
        Button category2ToolBarButton = new Button(category2.getDescription());
        category2ToolBarButton.setOnMouseClicked(e -> doOnBack());
        toolBar.getItems().add(category2ToolBarButton);
        
        Button category3ToolBarButton = new Button(category3.getDescription());
        toolBar.getItems().add(category3ToolBarButton);
	}

	private List<Expense> getExpenses() {
        ExpenseSearchCriteria criteria = new ExpenseSearchCriteria();
        criteria.setCategory1(category1);
        criteria.setCategory2(category2);
        if (category3 != null) {
            if (category3.getId() != null) {
                criteria.setCategory3(category3);
            } else {
                criteria.setCategory3Null(true);
            }
        }
        
	    return expenseService.searchExpenses(criteria);
    }

    private void selectExpense() {
		stageController.showEditExpenseScreen(expensesTable.getSelectedItem());
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

}