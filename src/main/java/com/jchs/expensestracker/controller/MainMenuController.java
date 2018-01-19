package com.jchs.expensestracker.controller;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;

@Controller
public class MainMenuController extends AbstractController {

	@Override
	public void updateDisplay() {
		stageController.setTitle("Main Menu");
	}

	@FXML 
	public void goToExpensesListScreen() {
		stageController.showExpensesListScreen();
	}

	@FXML 
	public void goToExpensesList2Screen() {
		stageController.showExpensesListScreen();
	}

	@FXML 
	public void goToCategory1ListScreen() {
		stageController.showCategory1ListScreen();
	}

	@FXML 
	public void goToCategory2ListScreen() {
		stageController.showCategory2ListScreen();
	}

	@FXML 
	public void goToCategory3ListScreen() {
		stageController.showCategory3ListScreen();
	}
	
	@FXML
	public void goToSearchExpensesCriteriaScreen() {
		stageController.showSearchExpensesCriteriaScreen();
	}

    @FXML
    public void goToExpensesList3Screen() {
        stageController.showEditExpenseSelectCategory1Screen();
    }

}
