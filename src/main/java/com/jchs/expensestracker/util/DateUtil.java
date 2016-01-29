package com.jchs.expensestracker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.jchs.expensestracker.Constants;

public class DateUtil {

	public static Date toDate(LocalDate localDate) {
		if (localDate == null) {
			return null;
		}
		
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate toLocalDate(Date date) {
		if (date == null) {
			return null;
		}
		
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date toDate(String dateString) {
		try {
			return new SimpleDateFormat(Constants.DATE_FORMAT).parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}