package com.jchs.expensestracker.controller;

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
import com.jchs.expensestracker.service.CategoryService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@Controller
@Scope("prototype")
public class Category3Controller extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(Category3Controller.class);
	
	@Autowired private CategoryService categoryService;
	
	@Parameter private Category3 category3;
	
	@FXML private ComboBox<Category1> parent1ComboBox;
	@FXML private ComboBox<Category2> parent2ComboBox;
	@FXML private TextField descriptionField;
	@FXML private Button deleteButton;
	
	@Override
	public void updateDisplay() {
		setTitle();
		parent1ComboBox.getItems().setAll(categoryService.getAllLevel1Categories());
		updateParent2ComboBoxWhenParent1ComboBoxChanges();
		
		if (category3 != null) {
			category3 = categoryService.getCategory3(category3.getId());
			parent1ComboBox.setValue(category3.getParent().getParent());
			parent2ComboBox.setValue(category3.getParent());
			descriptionField.setText(category3.getDescription());
			deleteButton.setDisable(false);
		}
		
		parent1ComboBox.requestFocus();
	}

	private void updateParent2ComboBoxWhenParent1ComboBoxChanges() {
		parent1ComboBox.valueProperty().addListener((obv, oldValue, newValue) -> {
			parent2ComboBox.setValue(null);
			parent2ComboBox.getItems().setAll(categoryService.findAllCategory2ByParent(parent1ComboBox.getValue()));
		});
	}

	private void setTitle() {
		if (category3 != null) {
			stageController.setTitle("Edit Category 3");
		} else {
			stageController.setTitle("Add New Category 3");
		}
	}

	@FXML
	public void doOnBack() {
		stageController.back();
	}

	@FXML
	public void saveCategory3() {
		if (!validateFields()) {
			return;
		}
		
		if (category3 == null) {
			category3 = new Category3();
		}
		category3.setParent(parent2ComboBox.getValue());
		category3.setDescription(StringUtils.strip(descriptionField.getText()));
		
		try {
			categoryService.save(category3);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Saved");
		doOnBack();
	}

	private boolean validateFields() {
		if (isParent1NotSpecified()) {
			ShowDialog.error("Parent Category 1 must be specified");
			parent1ComboBox.requestFocus();
			return false;
		}
		if (isParent2NotSpecified()) {
			ShowDialog.error("Parent Category 2 must be specified");
			parent2ComboBox.requestFocus();
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

	private boolean isParent1NotSpecified() {
		return parent1ComboBox.getValue() == null;
	}
	
	private boolean isParent2NotSpecified() {
		return parent2ComboBox.getValue() == null;
	}

	private boolean isDescriptionNotSpecified() {
		return StringUtils.isEmpty(descriptionField.getText());
	}

	private boolean isDescriptionAlreadyUsed() {
		Category3 existing = categoryService.findCategory3ByParentAndDescription(
				parent2ComboBox.getValue(), StringUtils.strip(descriptionField.getText()));
		if (existing != null) {
			if (category3 == null) {
				return true;
			} else {
				return !existing.equals(category3);
			}
		}
		return false;
	}

	@FXML 
	public void deleteCategory3() {
		if (!ShowDialog.confirm("Delete record?")) {
			return;
		}
		
		try {
			categoryService.delete(category3);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Record deleted");
		doOnBack();
	}

}
