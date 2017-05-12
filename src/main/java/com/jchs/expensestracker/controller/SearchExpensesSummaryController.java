package com.jchs.expensestracker.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.Parameter;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.service.CategoryService;
import com.jchs.expensestracker.util.FormatterUtil;
import com.jchs.expensestracker.util.LayoutUtil;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

@Controller
public class SearchExpensesSummaryController extends AbstractController {

	@Autowired private CategoryService categoryService;
	
	@FXML private TabPane tabPane;
	@FXML private Text expensesTotalText;
	
	@Parameter private List<Expense> expenses;
	
	@Override
	public void updateDisplay() {
		stageController.setTitle("Expenses Summary");
		
		for (Category1 category1 : categoryService.getAllLevel1Categories()) {
			Tab tab = new Tab(category1.getDescription());
			tab.setClosable(false);
			tabPane.getTabs().add(tab);
			
			VBox tabVBox = new VBox(5);
			
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setPrefViewportWidth(tabPane.getWidth());
			
			VBox vBox = new VBox();
			for (Category2 category2 : categoryService.findAllCategory2ByParent(category1)) {
				HBox categoryHBox = new HBox();
				categoryHBox.setAlignment(Pos.CENTER_LEFT);
				categoryHBox.getChildren().add(new Text(category2.getDescription()));
				
				GridPane gridPane = new GridPane();
				gridPane.setMaxWidth(350);
				gridPane.getColumnConstraints().add(new ColumnConstraints(250));
				gridPane.getColumnConstraints().add(new ColumnConstraints(100));
				
				int currentRow = 0;
				
				List<Category3> category3s = categoryService.findAllCategory3ByParent(category2);
				category3s.add(null);
				for (Category3 category3 : category3s) {
					gridPane.getRowConstraints().add(new RowConstraints(25));
					
					Text categoryText = new Text(category3 != null ? category3.getDescription() : "(no category)");
					gridPane.add(categoryText, 0, currentRow);
					
					Text totalAmountText = new Text(FormatterUtil.formatAmount(getTotalAmount(category1, category2, category3)));
					GridPane.setHalignment(totalAmountText, HPos.RIGHT);
					gridPane.add(totalAmountText, 1, currentRow);
					
					currentRow++;
				}
				
				if (!category3s.isEmpty()) {
					gridPane.setStyle("-fx-border-style: solid hidden solid hidden");
				} else {
					gridPane.setStyle("-fx-border-style: solid hidden hidden hidden");
				}
				
				GridPane totalGridPane = new GridPane();
				totalGridPane.getColumnConstraints().add(new ColumnConstraints(50));
				totalGridPane.getColumnConstraints().add(new ColumnConstraints(80));
				
				totalGridPane.add(new Text("Total:"), 0, 0);
				
				Text totalAmountText = new Text(FormatterUtil.formatAmount(getTotalAmount(category1, category2)));
				GridPane.setHalignment(totalAmountText, HPos.RIGHT);
				totalGridPane.add(totalAmountText, 1, 0);
				
				HBox totalHBox = new HBox();
				totalHBox.setMaxWidth(350);
				totalHBox.setPadding(new Insets(5, 0, 15, 0));
				totalHBox.setAlignment(Pos.CENTER_RIGHT);
				
				totalHBox.getChildren().add(totalGridPane);
				
				vBox.getChildren().add(categoryHBox);
				vBox.getChildren().add(gridPane);
				vBox.getChildren().add(totalHBox);
			}
			scrollPane.setContent(LayoutUtil.wrapInHBoxThenCenterAlign(vBox));
			
			HBox category1TotalHBox = new HBox();
			category1TotalHBox.setAlignment(Pos.CENTER_RIGHT);
			category1TotalHBox.getChildren().add(new Label("Total: "));
			category1TotalHBox.getChildren().add(new Text(FormatterUtil.formatAmount(getTotalAmount(category1))));
			
			tabVBox.getChildren().add(scrollPane);
			tabVBox.getChildren().add(category1TotalHBox);
			
			tab.setContent(tabVBox);
		}
		
		expensesTotalText.setText(FormatterUtil.formatAmount(getTotal(expenses)));
	}

	private BigDecimal getTotal(List<Expense> expenses2) {
		return expenses.stream()
				.map(expense -> expense.getAmount())
				.reduce(BigDecimal.ZERO, (x,y) -> x.add(y));
	}

	private BigDecimal getTotalAmount(Category1 category1, Category2 category2, Category3 category3) {
		return filterExpenses(expenses, category1, category2, category3)
				.stream()
				.map(expense -> expense.getAmount())
				.reduce(BigDecimal.ZERO, (x,y) -> x.add(y));
	}
	
	private BigDecimal getTotalAmount(Category1 category1, Category2 category2) {
		return filterExpenses(expenses, category1, category2)
				.stream()
				.map(expense -> expense.getAmount())
				.reduce(BigDecimal.ZERO, (x,y) -> x.add(y));
	}
	
	private BigDecimal getTotalAmount(Category1 category1) {
		return filterExpenses(expenses, category1)
				.stream()
				.map(expense -> expense.getAmount())
				.reduce(BigDecimal.ZERO, (x,y) -> x.add(y));
	}
	
	@FXML 
	public void doOnBack() {
		stageController.back();
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
					return (category3 == null && expense.getCategory3() == null)
							|| (category3 != null && category3.equals(expense.getCategory3()));
				})
				.collect(Collectors.toList());
	}
	
	private static List<Expense> filterExpenses(List<Expense> expenses, Category1 category1, Category2 category2) {
		return expenses.stream()
				.filter(expense -> {
					return category1.equals(expense.getCategory1());
				})
				.filter(expense -> {
					return category2.equals(expense.getCategory2());
				})
				.collect(Collectors.toList());
	}
	
	private static List<Expense> filterExpenses(List<Expense> expenses, Category1 category1) {
		return expenses.stream()
				.filter(expense -> {
					return category1.equals(expense.getCategory1());
				})
				.collect(Collectors.toList());
	}
	
}
