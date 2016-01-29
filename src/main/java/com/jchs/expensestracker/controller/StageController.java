package com.jchs.expensestracker.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jchs.expensestracker.ControllerFactory;
import com.jchs.expensestracker.NavigationHistory;
import com.jchs.expensestracker.NavigationHistoryItem;
import com.jchs.expensestracker.Parameter;
import com.jchs.expensestracker.model.Category1;
import com.jchs.expensestracker.model.Category2;
import com.jchs.expensestracker.model.Category3;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class StageController {

	private static final double WIDTH = 1200d;
	private static final double HEIGHT = 640d;
	
	@Autowired private ControllerFactory controllerFactory;
	
	private Stage stage;
	private NavigationHistory history = new NavigationHistory();
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void loadSceneFromFXML(String file) {
		loadSceneFromFXML(file, null);
	}
	
	private void loadSceneFromFXML(String file, Map<String, Object> model) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(controllerFactory);
		
		Parent root = null;
		try {
			root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/" + file + ".fxml"));
		} catch (IOException e) {	
			throw new RuntimeException(e);
		}
		
		if (stage.getScene() != null) {
			stage.setScene(new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight()));
		} else {
			stage.setScene(new Scene(root, WIDTH, HEIGHT));
		}
		
		stage.getScene().getStylesheets().add("css/application.css");
		
		if (fxmlLoader.getController() instanceof AbstractController) {
			AbstractController controller = (AbstractController)fxmlLoader.getController();
			if (model != null && !model.isEmpty()) {
				mapParameters(controller, model);
			}
			controller.updateDisplay();
		}
		
		saveHistory(file, model);
	}

	private void mapParameters(AbstractController controller, Map<String, Object> model) {
		Class<? extends AbstractController> clazz = controller.getClass();
		for (String key : model.keySet()) {
			try {
				Field field = clazz.getDeclaredField(key);
				if (field != null && field.getAnnotation(Parameter.class) != null) {
					field.setAccessible(true);
					field.set(controller, model.get(key));
				}
			} catch (Exception e) {
				System.out.println("Error setting parameter " + key);
			}
		}
	}

	private void saveHistory(String sceneName, Map<String, Object> model) {
		history.add(new NavigationHistoryItem(sceneName, model));
	}
	
	public void back() {
		NavigationHistoryItem previousScreen = history.getPreviousScreen();
		loadSceneFromFXML(previousScreen.getSceneName(), previousScreen.getModel());
	}
	
	public void setTitle(String title) {
		stage.setTitle("Expenses Tracker - " + title);
	}

	public void showMainMenuScreen() {
		loadSceneFromFXML("mainMenu");
	}

	public void showCategory1ListScreen() {
		loadSceneFromFXML("category1List");
	}

	public void showAddCategory1Screen() {
		loadSceneFromFXML("category1");
	}

	public void showEditCategory1Screen(Category1 category1) {
		loadSceneFromFXML("category1", Collections.singletonMap("category1", category1));
	}

	public void showCategory2ListScreen() {
		loadSceneFromFXML("category2List");
	}

	public void showEditCategory2Screen(Category2 category2) {
		loadSceneFromFXML("category2", Collections.singletonMap("category2", category2));
	}

	public void showAddCategory2Screen() {
		loadSceneFromFXML("category2");
	}

	public void showAddCategory3Screen() {
		loadSceneFromFXML("category3");
	}

	public void showEditCategory3Screen(Category3 category3) {
		loadSceneFromFXML("category3", Collections.singletonMap("category3", category3));
	}

	public void showCategory3ListScreen() {
		loadSceneFromFXML("category3List");
	}

}