package com.jchs.expensestracker.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.Parameter;
import com.jchs.expensestracker.gui.ShowDialog;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;
import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.service.CategoryService;
import com.jchs.expensestracker.service.ExpenseService;
import com.jchs.expensestracker.util.DateUtil;
import com.jchs.expensestracker.util.FormatterUtil;
import com.jchs.expensestracker.util.NumberUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

@Controller
@Scope("prototype")
public class ExpenseController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);
	
	@Autowired private CategoryService categoryService;
	@Autowired private ExpenseService expenseService;
	
	@FXML private ComboBox<Category1> category1ComboBox;
	@FXML private ComboBox<Category2> category2ComboBox;
	@FXML private ComboBox<Category3> category3ComboBox;
	@FXML private DatePicker dateOfTransactionDatePicker;
	@FXML private TextField particularsField;
	@FXML private TextField amountField;
	@FXML private Button deleteButton;
	
	@Parameter private Expense expense;
	
	@Override
	public void updateDisplay() {
		category1ComboBox.getItems().setAll(categoryService.getAllLevel1Categories());
		updateCategory2ComboBoxWhenCategory1ComboBoxChanges();
		updateCategory3ComboBoxWhenCategory2ComboBoxChanges();
		
		if (expense != null) {
			expense = expenseService.getExpense(expense.getId());
			category1ComboBox.setValue(expense.getCategory1());
			category2ComboBox.setValue(expense.getCategory2());
			category3ComboBox.setValue(expense.getCategory3());
			dateOfTransactionDatePicker.setValue(DateUtil.toLocalDate(expense.getDateOfTransaction()));
			particularsField.setText(expense.getParticulars());
			amountField.setText(FormatterUtil.formatAmount(expense.getAmount()));
			deleteButton.setDisable(false);
		}
		
		category1ComboBox.requestFocus();
	}

	private void updateCategory2ComboBoxWhenCategory1ComboBoxChanges() {
		category1ComboBox.valueProperty().addListener((obv, oldValue, newValue) -> {
			category2ComboBox.setValue(null);
			category2ComboBox.getItems().setAll(categoryService.findAllCategory2ByParent(category1ComboBox.getValue()));
		});
	}

	private void updateCategory3ComboBoxWhenCategory2ComboBoxChanges() {
		category2ComboBox.valueProperty().addListener((obv, oldValue, newValue) -> {
			category3ComboBox.setValue(null);
			if (newValue != null) {
				category3ComboBox.getItems().setAll(
						categoryService.findAllCategory3ByParent(category2ComboBox.getValue()));
			} else {
				category3ComboBox.getItems().clear();
			}
		});
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}
	
	@FXML
	public void saveExpense() {
		if (!validateFields()) {
			return;
		}
		
		if (expense == null) {
			expense = new Expense();
		}
		expense.setCategory1(category1ComboBox.getValue());
		expense.setCategory2(category2ComboBox.getValue());
		expense.setCategory3(category3ComboBox.getValue());
		expense.setDateOfTransaction(DateUtil.toDate(dateOfTransactionDatePicker.getValue()));
		expense.setParticulars(StringUtils.strip(particularsField.getText()));
		expense.setAmount(NumberUtil.toBigDecimal(StringUtils.strip(amountField.getText())));
		expense.setDateEntered(new Date());
		
		try {
			expenseService.save(expense);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Record saved");
		updateDisplay();
	}

	private boolean validateFields() {
		if (isCategory1NotSpecified()) {
			ShowDialog.error("Category 1 must be specified");
			category1ComboBox.requestFocus();
			return false;
		}
		
		if (isCategory2NotSpecified()) {
			ShowDialog.error("Category 2 must be specified");
			category2ComboBox.requestFocus();
			return false;
		}
		
		if (isCategory3NotSpecified()) {
			ShowDialog.error("Category 3 must be specified");
			category3ComboBox.requestFocus();
			return false;
		}
		
		if (isDateOfTransactionNotSpecified()) {
			ShowDialog.error("Date of Transaction must be specified");
			dateOfTransactionDatePicker.requestFocus();
			return false;
		}
		
		if (isParticularsNotSpecified()) {
			ShowDialog.error("Particulars must be specified");
			particularsField.requestFocus();
			return false;
		}
		
		if (isAmountNotSpecified()) {
			ShowDialog.error("Amount must be specified");
			amountField.requestFocus();
			return false;
		}
		
		if (isAmountNotValid()) {
			ShowDialog.error("Amount must be a valid amount");
			amountField.requestFocus();
			return false;
		}
		
		return true;
	}

	private boolean isCategory1NotSpecified() {
		return category1ComboBox.getValue() == null;
	}
	
	private boolean isCategory2NotSpecified() {
		return category2ComboBox.getValue() == null;
	}
	
	private boolean isCategory3NotSpecified() {
		return category3ComboBox.getValue() == null;
	}
	
	private boolean isDateOfTransactionNotSpecified() {
		return dateOfTransactionDatePicker.getValue() == null;
	}
	
	private boolean isParticularsNotSpecified() {
		return StringUtils.isEmpty(particularsField.getText());
	}

	private boolean isAmountNotSpecified() {
		return StringUtils.isEmpty(amountField.getText());
	}

	private boolean isAmountNotValid() {
		return !NumberUtil.isAmount(StringUtils.strip(amountField.getText()));
	}
	
	@FXML
	public void deleteExpense() {
		if (!ShowDialog.confirm("Delete record?")) {
			return;
		}
		
		try {
			expenseService.delete(expense);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Record deleted");
		stageController.back();
	}
	
}
