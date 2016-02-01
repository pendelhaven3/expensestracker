package com.jchs.expensestracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.service.ExpenseService;

import javafx.fxml.FXML;

@Controller
@Scope("prototype")
public class ExpensesListController extends AbstractController {

	@Autowired private ExpenseService expenseService;
	
	@FXML private AppTableView<Expense> expensesTable;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Expenses List");
		expensesTable.getItems().setAll(expenseService.getAllExpenses());
		expensesTable.setDoubleClickAndEnterKeyAction(() -> selectExpense());
	}
	
	private void selectExpense() {
		stageController.showEditExpenseScreen(expensesTable.getSelectedItem());
	}

	public void doOnBack() {
		stageController.back();
	}
	
	@FXML
	public void addExpense() {
		stageController.showAddExpenseScreen();
	}
	
}
