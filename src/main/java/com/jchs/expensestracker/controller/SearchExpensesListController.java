package com.jchs.expensestracker.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jchs.expensestracker.Parameter;
import com.jchs.expensestracker.excel.ExpensesListExcelGenerator;
import com.jchs.expensestracker.gui.ShowDialog;
import com.jchs.expensestracker.gui.component.AppTableView;
import com.jchs.expensestracker.model.Expense;
import com.jchs.expensestracker.model.ExpenseSearchCriteria;
import com.jchs.expensestracker.service.ExpenseService;
import com.jchs.expensestracker.util.ExcelUtil;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SearchExpensesListController extends AbstractController {

	@FXML
	private AppTableView<Expense> expensesTable;
	
	@Parameter
	private ExpenseSearchCriteria criteria;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Override
	public void updateDisplay() {
		expensesTable.getItems().addAll(searchExpenses());
	}

	private List<Expense> searchExpenses() {
		return expenseService.searchExpenses(criteria);
	}
	
	@FXML
	public void doOnBack() {
		stageController.back();
	}

	@FXML
	public void generateExcel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialDirectory(Paths.get(System.getProperty("user.home"), "Desktop").toFile());
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Excel files", "*.xlsx"));
        fileChooser.setInitialFileName(getExcelFilename());
        File file = fileChooser.showSaveDialog(stageController.getStage());
        if (file == null) {
        	return;
        }
		
		try (
			Workbook workbook = new ExpensesListExcelGenerator().generate(searchExpenses());
			FileOutputStream out = new FileOutputStream(file);
		) {
			workbook.write(out);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		if (ShowDialog.confirm("Excel file generated.\nDo you wish to open the file?")) {
			ExcelUtil.openExcelFile(file);
		}
	}

	private String getExcelFilename() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return "expenses_report_" + dateFormatter.format(new Date());
	}

}
