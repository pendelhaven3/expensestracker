package com.jchs.expensestracker.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.gui.ShowDialog;
import com.jchs.expensestracker.gui.component.AppPropertyValueFactory;
import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.service.CategoryService;
import com.jchs.expensestracker.service.ExpenseService;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

@Controller
public class ExpensesList2Controller extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ExpensesList2Controller.class);
	
	private static final String[] CATEGORY_1_COLORS = {
			"category1Tab-bgColor1",	
			"category1Tab-bgColor2"	
	};
	
	private static final String[] CATEGORY_2_COLORS = {
			"category2Tab-bgColor1",	
			"category2Tab-bgColor2",	
			"category2Tab-bgColor3",	
			"category2Tab-bgColor4",	
			"category2Tab-bgColor5",	
			"category2Tab-bgColor6",	
			"category2Tab-bgColor7",	
			"category2Tab-bgColor8",	
			"category2Tab-bgColor9"	
	};
		
	private static final String[] CATEGORY_3_COLORS = {
			"category3Tab-bgColor1",	
			"category3Tab-bgColor2",	
			"category3Tab-bgColor3"
	};
		
	@Autowired private ExpenseService expenseService;
	@Autowired private CategoryService categoryService;
	
	@FXML private TabPane category1TabPane;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Expenses List 2");
		createTabs();
	}
	
	private void createTabs() {
		List<Expense> expenses = expenseService.getAllExpenses();
		
		int category1Index = 0;
		for (Category1 category1 : categoryService.getAllLevel1Categories()) {
			Tab category1Tab = new Tab();
			category1Tab.setText(category1.getDescription());
			category1Tab.setClosable(false);
			category1Tab.getStyleClass().add(CATEGORY_1_COLORS[category1Index % CATEGORY_1_COLORS.length]);
			category1TabPane.getTabs().add(category1Tab);

			VBox category1VBox = new VBox();
			category1VBox.getStyleClass().add("vbox-content");
			category1Tab.setContent(category1VBox);
			
			TabPane category2TabPane = new TabPane();
			VBox.setVgrow(category2TabPane, Priority.ALWAYS);
			category1VBox.getChildren().add(category2TabPane);
			
			int category2Index = 0;
			for (Category2 category2 : categoryService.findAllCategory2ByParent(category1)) {
				Tab category2Tab = new Tab();
				category2Tab.setText(category2.getDescription());
				category2Tab.setClosable(false);
				category2Tab.getStyleClass().add(CATEGORY_2_COLORS[category2Index % CATEGORY_2_COLORS.length]);
				category2TabPane.getTabs().add(category2Tab);

				VBox category2VBox = new VBox();
				category2Tab.setContent(category2VBox);
				
				List<Category3> category3s = categoryService.findAllCategory3ByParent(category2);
				if (category3s.isEmpty()) {
					HBox hbox = createExpenseButtonsHBox();
					category2VBox.getChildren().add(hbox);
					
					ExpensesTableView tableView = new ExpensesTableView();
					tableView.getItems().setAll(filterExpenses(expenses, category1, category2, null));
					VBox.setVgrow(tableView, Priority.ALWAYS);
					category2VBox.getChildren().add(tableView);
					
					hbox.getChildren().add(createAddExpenseButton(category1, category2, null));
					hbox.getChildren().add(createDeleteExpenseButton(tableView));
				} else {
					category2VBox.getStyleClass().add("vbox-content");
					
					TabPane category3TabPane = new TabPane();
					VBox.setVgrow(category3TabPane, Priority.ALWAYS);
					category2VBox.getChildren().add(category3TabPane);
					
					int category3Index = 0;
					for (Category3 category3 : categoryService.findAllCategory3ByParent(category2)) {
						Tab category3Tab = new Tab();
						category3Tab.setText(category3.getDescription());
						category3Tab.setClosable(false);
						category3Tab.getStyleClass().add(CATEGORY_3_COLORS[category3Index % CATEGORY_3_COLORS.length]);
						category3TabPane.getTabs().add(category3Tab);

						VBox category3VBox = new VBox();
						category3Tab.setContent(category3VBox);
						
						HBox hbox = createExpenseButtonsHBox();
						category3VBox.getChildren().add(hbox);
						
						ExpensesTableView tableView = new ExpensesTableView();
						tableView.getItems().setAll(filterExpenses(expenses, category1, category2, category3));
						VBox.setVgrow(tableView, Priority.ALWAYS);
						category3VBox.getChildren().add(tableView);
						
						hbox.getChildren().add(createAddExpenseButton(category1, category2, category3));
						hbox.getChildren().add(createDeleteExpenseButton(tableView));
						
						category3Index++;
					}
				}
				category2Index++;
			}
			category1Index++;
		}
	}

	private HBox createExpenseButtonsHBox() {
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		hbox.getStyleClass().add("controls");
		hbox.setPadding(new Insets(3, 3, 3, 3));
		return hbox;
	}
	
	private AddExpenseButton createAddExpenseButton(Category1 category1, Category2 category2, Category3 category3) {
		AddExpenseButton addButton = new AddExpenseButton("+", category1, category2, category3);
		addButton.getStyleClass().add("control-button");
		addButton.setOnAction(event -> addExpense(event));
		return addButton;
	}

	private DeleteExpenseButton createDeleteExpenseButton(ExpensesTableView tableView) {
		DeleteExpenseButton deleteButton = new DeleteExpenseButton("-", tableView);
		deleteButton.getStyleClass().add("control-button");
		deleteButton.setOnAction(event -> deleteExpense(event));
		return deleteButton;
	}
	
	private static List<Expense> filterExpenses(List<Expense> expenses, 
			Category1 category1, Category2 category2, Category3 category3) {
		return expenses.stream()
				.filter(expense -> {
					return category1.equals(expense.getCategory1());
				})
				.filter(expense -> {
					return category2.equals(expense.getCategory2());
				})
				.filter(expense -> {
					return category3 == null || category3.equals(expense.getCategory3());
				})
				.collect(Collectors.toList());
	}

	private void selectExpense(Event event) {
		@SuppressWarnings("unchecked")
		AppTableView<Expense> tableView = (AppTableView<Expense>)event.getSource();
		stageController.showEditExpenseScreen(tableView.getSelectedItem());
	}

	public void doOnBack() {
		stageController.back();
	}

	private void addExpense(ActionEvent event) {
		AddExpenseButton source = (AddExpenseButton)event.getSource();
		stageController.showAddExpenseScreen(source.getCategory1(), source.getCategory2(), source.getCategory3());
	}

	private void deleteExpense(ActionEvent event) {
		DeleteExpenseButton source = (DeleteExpenseButton)event.getSource();
		ExpensesTableView tableView = source.getTableView();
		
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
	
	private class ExpensesTableView extends AppTableView<Expense> {

		public ExpensesTableView() {
			initializeColumns();
			setDoubleClickAndEnterKeyAction(event -> selectExpense(event));
		}

		private void initializeColumns() {
			setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			TableColumn<Expense, String> dateEnteredColumn = new TableColumn<>("Date Entered");
			dateEnteredColumn.setCellValueFactory(new AppPropertyValueFactory<>("dateEntered"));
			
			TableColumn<Expense, String> dateOfTransactionColumn = new TableColumn<>("Date of Transaction");
			dateOfTransactionColumn.setCellValueFactory(new AppPropertyValueFactory<>("dateOfTransaction"));

			TableColumn<Expense, String> particularsColumn = new TableColumn<>("Particulars");
			particularsColumn.setCellValueFactory(new AppPropertyValueFactory<>("particulars"));

			TableColumn<Expense, String> amountColumn = new TableColumn<>("Amount");
			amountColumn.setCellValueFactory(new AppPropertyValueFactory<>("amount"));
			amountColumn.getStyleClass().add("right");

			getColumns().add(dateEnteredColumn);
			getColumns().add(dateOfTransactionColumn);
			getColumns().add(particularsColumn);
			getColumns().add(amountColumn);
		}
		
	}
	
	private class AddExpenseButton extends Button {
		
		private Category1 category1;
		private Category2 category2;
		private Category3 category3;
		
		public AddExpenseButton(String text, Category1 category1, Category2 category2, Category3 category3) {
			super(text);
			this.category1 = category1;
			this.category2 = category2;
			this.category3 = category3;
		}
		
		public Category1 getCategory1() {
			return category1;
		}
		
		public Category2 getCategory2() {
			return category2;
		}
		
		public Category3 getCategory3() {
			return category3;
		}
		
	}
	
	private class DeleteExpenseButton extends Button {
		
		private ExpensesTableView tableView;

		public DeleteExpenseButton(String text, ExpensesTableView tableView) {
			super(text);
			this.tableView = tableView;
		}
		
		public ExpensesTableView getTableView() {
			return tableView;
		}
		
	}
	
}
