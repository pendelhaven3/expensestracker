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
import com.jchs.expensestracker.gui.component.CustomAction;
import com.jchs.expensestracker.gui.component.DeleteExpenseButton;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.service.CategoryService;
import com.jchs.expensestracker.service.ExpenseService;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;

@Controller
//@PersistentScene
public class ExpensesListController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ExpensesListController.class);
	
	@Autowired private ExpenseService expenseService;
	@Autowired private CategoryService categoryService;
	
	@FXML private AppTableView<Expense> officeStoreMaterialsExpensesTable;
	@FXML private AppTableView<Expense> dieselNkr420ExpensesTable;
	@FXML private AppTableView<Expense> dieselL300ExpensesTable;
	@FXML private AppTableView<Expense> truckRepairL300ExpensesTable;
	@FXML private AppTableView<Expense> truckRepairNkr420ExpensesTable;
	@FXML private AppTableView<Expense> companyUtilitiesMeralcoExpensesTable;
	@FXML private AppTableView<Expense> companyUtilitiesMayniladExpensesTable;
	@FXML private AppTableView<Expense> companyUtilitiesTelephoneExpensesTable;
	@FXML private AppTableView<Expense> companySalariesExpensesTable;
	@FXML private AppTableView<Expense> governmentDutiesExpensesTable;
	@FXML private AppTableView<Expense> governmentBenefitsExpensesTable;
	@FXML private AppTableView<Expense> repairExpensesTable;
	@FXML private AppTableView<Expense> companyMiscellaneousExpensesTable;
	@FXML private AppTableView<Expense> withdrawalsExpensesTable;
	@FXML private AppTableView<Expense> personalDieselExpensesTable;
	@FXML private AppTableView<Expense> groceriesExpensesTable;
	@FXML private AppTableView<Expense> personalUtilitiesMeralcoExpensesTable;
	@FXML private AppTableView<Expense> personalUtilitiesMayniladExpensesTable;
	@FXML private AppTableView<Expense> personalUtilitiesCableExpensesTable;
	@FXML private AppTableView<Expense> personalSalariesExpensesTable;
	@FXML private AppTableView<Expense> personalMiscellaneousExpensesTable;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Expenses List");
		
		List<Expense> expenses = expenseService.getAllExpenses();
		officeStoreMaterialsExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "OFFICE/STORE MATERIALS", null));
		dieselL300ExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "DIESEL", "L300"));
		dieselNkr420ExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "DIESEL", "NKR 420"));
		truckRepairL300ExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "TRUCK REPAIR AND MAINTENANCE", "L300"));
		truckRepairNkr420ExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "TRUCK REPAIR AND MAINTENANCE", "NKR 420"));
		companyUtilitiesMeralcoExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "UTILITIES", "MERALCO"));
		companyUtilitiesMayniladExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "UTILITIES", "MAYNILAD"));
		companyUtilitiesTelephoneExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "UTILITIES", "TELEPHONE/MOBILE"));
		companySalariesExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "SALARIES", null));
		governmentDutiesExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "GOVERNMENT DUTIES", null));
		governmentBenefitsExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "GOVERNMENT BENEFITS (SSS/PAG-IBIG/PH)", null));
		repairExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "REPAIR AND MAINTENANCE", null));
		companyMiscellaneousExpensesTable.getItems().setAll(
				filterExpenses(expenses, "COMPANY EXPENSE", "MISCELLANEOUS", null));
		withdrawalsExpensesTable.getItems().setAll(
				filterExpenses(expenses, "PERSONAL EXPENSE", "WITHDRAWALS", null));
		personalDieselExpensesTable.getItems().setAll(
				filterExpenses(expenses, "PERSONAL EXPENSE", "DIESEL", null));
		groceriesExpensesTable.getItems().setAll(
				filterExpenses(expenses, "PERSONAL EXPENSE", "GROCERIES", null));
		personalUtilitiesMeralcoExpensesTable.getItems().setAll(
				filterExpenses(expenses, "PERSONAL EXPENSE", "UTILITIES", "MERALCO"));
		personalUtilitiesMayniladExpensesTable.getItems().setAll(
				filterExpenses(expenses, "PERSONAL EXPENSE", "UTILITIES", "MAYNILAD"));
		personalUtilitiesCableExpensesTable.getItems().setAll(
				filterExpenses(expenses, "PERSONAL EXPENSE", "UTILITIES", "CABLE"));
		personalSalariesExpensesTable.getItems().setAll(
				filterExpenses(expenses, "PERSONAL EXPENSE", "SALARIES (YAYA)", null));
		personalMiscellaneousExpensesTable.getItems().setAll(
				filterExpenses(expenses, "PERSONAL EXPENSE", "MISCELLANEOUS", null));
		
		setTablesDoubleClickAndEnterKeyActions();
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
					return expense.getCategory3() == null 
							|| category3Description.equals(expense.getCategory3().getDescription());
				})
				.collect(Collectors.toList());
	}

	private void setTablesDoubleClickAndEnterKeyActions() {
		CustomAction action = event -> selectExpense(event);
		
		for (Field f : ExpensesListController.class.getDeclaredFields()) {
			if (f.getType().equals(AppTableView.class)) {
				f.setAccessible(true);
				try {
					@SuppressWarnings("unchecked")
					AppTableView<Expense> tableView = (AppTableView<Expense>)f.get(this);
					tableView.setDoubleClickAndEnterKeyAction(action);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					ShowDialog.unexpectedError();
					return;
				}
			}
		}
	}

	private void selectExpense(Event event) {
		@SuppressWarnings("unchecked")
		AppTableView<Expense> tableView = (AppTableView<Expense>)event.getSource();
		stageController.showEditExpenseScreen(tableView.getSelectedItem());
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
