package com.pj.expensestracker.controller;

import org.springframework.stereotype.Controller;
import javafx.fxml.FXML;

@Controller
public class MainMenuController extends AbstractController {

	@Override
	public void updateDisplay() {
		stageController.setTitle("Main Menu");
	}

	@FXML 
	public void goToCategory1ListScreen() {
		stageController.showCategory1ListScreen();
	}

}
