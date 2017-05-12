package com.jchs.expensestracker.gui.component;

import java.util.Date;

import com.jchs.expensestracker.util.FormatterUtil;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DateCellFactory <T> implements Callback<TableColumn<T, Date>, TableCell<T, Date>> {

	@Override
	public TableCell<T, Date> call(TableColumn<T, Date> param) {
		return new TableCell<T, Date>() {

			@Override
			protected void updateItem(Date item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
				} else {
					setText(FormatterUtil.formatDate(item));
				}
			}
		};
	}

}
