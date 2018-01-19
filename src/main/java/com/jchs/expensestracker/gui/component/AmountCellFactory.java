package com.jchs.expensestracker.gui.component;

import java.math.BigDecimal;

import com.jchs.expensestracker.util.FormatterUtil;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class AmountCellFactory <T> implements Callback<TableColumn<T, BigDecimal>, TableCell<T, BigDecimal>> {

	@Override
	public TableCell<T, BigDecimal> call(TableColumn<T, BigDecimal> param) {
		return new TableCell<T, BigDecimal>() {

			@Override
			protected void updateItem(BigDecimal item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
				} else {
					setText(FormatterUtil.formatAmount(item));
				}
			}
		};
	}

}
