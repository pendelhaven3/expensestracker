package com.jchs.expensestracker.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.gui.ShowDialog;
import com.jchs.expensestracker.gui.component.AddExpenseButton;
import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.gui.component.DeleteExpenseButton;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.service.CategoryService;
import com.jchs.expensestracker.service.ExpenseService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

@Controller
public class ExpensesListController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ExpensesListController.class);
	
	@Autowired private ExpenseService expenseService;
	@Autowired private CategoryService categoryService;
	
	@FXML private Tab companyExpensesTab;
	@FXML private AppTableView<Expense> dieselNkr420ExpensesTable;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Expenses List");
		
		List<Expense> expenses = expenseService.getAllExpenses();
		dieselNkr420ExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "DIESEL", "NKR 420"));
		
		dieselNkr420ExpensesTable.setDoubleClickAndEnterKeyAction(() -> selectExpense());
	}
	
	private void selectExpense() {
		stageController.showEditExpenseScreen(dieselNkr420ExpensesTable.getSelectedItem());
	}

	private static List<Expense> filterExpenses(List<Expense> expenses, 
			String category1Description, String category2Description, String category3Description) {
		return expenses.stream()
				.filter(expense -> {
					return category1Description.equals(expense.getCategory1().getDescription());
				})
				.filter(expense -> {
					return category2Description.equals(expense.getCategory2().getDescription());
				})
				.filter(expense -> {
					return expense.getCategory3() != null 
							&& category3Description.equals(expense.getCategory3().getDescription());
				})
				.collect(Collectors.toList());
	}

	public void doOnBack() {
		stageController.back();
	}

	@FXML
	public void addExpense(ActionEvent event) {
		AddExpenseButton source = (AddExpenseButton)event.getSource();
		Category1 category1 = categoryService.findCategory1ByDescription(source.getCategory1Description());
		Category2 category2 = categoryService.findCategory2ByParentAndDescription(category1, 
				source.getCategory2Description());
		Category3 category3 = categoryService.findCategory3ByParentAndDescription(category2, 
				source.getCategory3Description());
		
		stageController.showAddExpenseScreen(category1, category2, category3);
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void deleteExpense(ActionEvent event) {
		DeleteExpenseButton source = (DeleteExpenseButton)event.getSource();
		
		AppTableView<Expense> tableView = null;
		try {
			Field field = ExpensesListController.class.getDeclaredField(source.getTableId());
			field.setAccessible(true);
			tableView = (AppTableView<Expense>)field.get(this);
		} catch (Exception e) {
			logger.error("Error retrieving property " + source.getTableId() + " from ExpensesListController", e);
			ShowDialog.unexpectedError();
			return;
		}
		
		if (!tableView.hasSelectedItem()) {
			ShowDialog.error("No selected record");
			return;
		}
		
		if (!ShowDialog.confirm("Delete selected record?")) {
			return;
		}
		
		try {
			expenseService.delete(tableView.getSelectedItem());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Record deleted");
		updateDisplay();
	}
	
}
