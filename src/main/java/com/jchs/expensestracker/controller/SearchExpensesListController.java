package com.jchs.expensestracker.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.Parameter;
import com.jchs.expensestracker.gui.ShowDialog;
import com.jchs.expensestracker.gui.component.AppPropertyValueFactory;
import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.model.ExpenseSearchCriteria;
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
import javafx.scene.text.Text;

@Controller
public class SearchExpensesListController extends AbstractController {

	@FXML
	private AppTableView<Expense> expensesTable;
	
	@Parameter
	private ExpenseSearchCriteria criteria;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Override
	public void updateDisplay() {
		List<Expense> expenses = expenseService.searchExpenses(criteria);
		expensesTable.getItems().addAll(expenses);
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

}
