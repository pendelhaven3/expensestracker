package com.jchs.expensestracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.jchs.expensestracker.Parameter;
import com.jchs.expensestracker.gui.ShowDialog;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.service.CategoryService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@Controller
@Scope("prototype")
public class Category2Controller extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(Category2Controller.class);
	
	@Autowired private CategoryService categoryService;
	
	@Parameter private Category2 category2;
	
	@FXML private ComboBox<Category1> parentComboBox;
	@FXML private TextField descriptionField;
	@FXML private Button deleteButton;
	
	@Override
	public void updateDisplay() {
		setTitle();
		parentComboBox.getItems().setAll(categoryService.getAllLevel1Categories());
		
		if (category2 != null) {
			category2 = categoryService.getCategory2(category2.getId());
			parentComboBox.setValue(category2.getParent());
			descriptionField.setText(category2.getDescription());
			deleteButton.setDisable(false);
		}
		
		parentComboBox.requestFocus();
	}

	private void setTitle() {
		if (category2 != null) {
			stageController.setTitle("Edit Category 2");
		} else {
			stageController.setTitle("Add New Category 2");
		}
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

	@FXML
	public void saveCategory2() {
		if (!validateFields()) {
			return;
		}
		
		if (category2 == null) {
			category2 = new Category2();
		}
		category2.setParent(parentComboBox.getValue());
		category2.setDescription(descriptionField.getText());
		
		try {
			categoryService.save(category2);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Saved");
		doOnBack();
	}

	private boolean validateFields() {
		if (isParentNotSpecified()) {
			ShowDialog.error("Parent Category 1 must be specified");
			parentComboBox.requestFocus();
			return false;
		}
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

	private boolean isParentNotSpecified() {
		return parentComboBox.getValue() == null;
	}

	private boolean isDescriptionAlreadyUsed() {
		Category2 existing = categoryService.findCategory2ByParentAndDescription(
				parentComboBox.getValue(), descriptionField.getText());
		if (existing != null) {
			if (category2 == null) {
				return true;
			} else {
				return !existing.equals(category2);
			}
		}
		return false;
	}

	private boolean isDescriptionNotSpecified() {
		return StringUtils.isEmpty(descriptionField.getText());
	}

	@FXML 
	public void deleteCategory2() {
		if (!ShowDialog.confirm("Delete record?")) {
			return;
		}
		
		try {
			categoryService.delete(category2);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Record deleted");
		doOnBack();
	}

}
