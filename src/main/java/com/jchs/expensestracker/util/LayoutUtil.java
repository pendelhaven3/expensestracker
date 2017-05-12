package com.jchs.expensestracker.util;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class LayoutUtil {

	public static HBox wrapInHBoxThenCenterAlign(Pane pane) {
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.getChildren().add(pane);
		return hbox;
	}
	
}
