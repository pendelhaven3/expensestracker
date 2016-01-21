package com.pj.expensestracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.pj.expensestracker.Parameter;
import com.pj.expensestracker.gui.ShowDialog;
import com.pj.expensestracker.model.Category1;
import com.pj.expensestracker.service.CategoryService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Controller
@Scope("prototype")
public class Category1Controller extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(Category1Controller.class);
	
	@Autowired private CategoryService categoryService;
	
	@Parameter private Category1 category1;
	
	@FXML private TextField descriptionField;
	@FXML private Button deleteButton;
	
	@Override
	public void updateDisplay() {
		setTitle();
		if (category1 != null) {
			category1 = categoryService.getCategory1(category1.getId());
			descriptionField.setText(category1.getDescription());
			deleteButton.setDisable(false);
		}
		descriptionField.requestFocus();
	}

	private void setTitle() {
		if (category1 != null) {
			stageController.setTitle("Edit Category 1");
		} else {
			stageController.setTitle("Add New Category 1");
		}
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

	@FXML
	public void saveCategory1() {
		if (!validateFields()) {
			return;
		}
		
		if (category1 == null) {
			category1 = new Category1();
		}
		category1.setDescription(descriptionField.getText());
		
		try {
			categoryService.save(category1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Saved");
		doOnBack();
	}

	private boolean validateFields() {
		if (isDescriptionNotSpecified()) {
			ShowDialog.error("Description must be specified");
			descriptionField.requestFocus();
			return false;
		}
		if (isDescriptionAlreadyUsed()) {
			ShowDialog.error("Description is already used by another record");
			descriptionField.requestFocus();
			return false;
		}
		return true;
	}

	private boolean isDescriptionAlreadyUsed() {
		Category1 existing = categoryService.findCategory1ByDescription(descriptionField.getText());
		if (existing != null) {
			if (category1 == null) {
				return true;
			} else {
				return !existing.equals(category1);
			}
		}
		return false;
	}

	private boolean isDescriptionNotSpecified() {
		return StringUtils.isEmpty(descriptionField.getText());
	}

	@FXML 
	public void deleteCategory1() {
		if (!ShowDialog.confirm("Delete record?")) {
			return;
		}
		
		try {
			categoryService.delete(category1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Record deleted");
		doOnBack();
	}

}
