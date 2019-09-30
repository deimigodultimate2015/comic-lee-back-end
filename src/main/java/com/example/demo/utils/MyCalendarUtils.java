package com.example.demo.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.demo.dto.MyDate;

public class MyCalendarUtils {
	private MyCalendarUtils() {};
	
	public static List<MyDate> getPrevious7Days() {
		Calendar calendar = new GregorianCalendar();
		List<MyDate> data7PreviousDays = new ArrayList<>();
		
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		
		for(int i = 0; i < 7; i++) {
			calendar.add(Calendar.DATE, -1);
			MyDate dateToAdd = new MyDate();
			
			dateToAdd.setViewDay(calendar.get(Calendar.DAY_OF_MONTH));
			dateToAdd.setViewYear(calendar.get(Calendar.YEAR));
			dateToAdd.setViewMonth(calendar.get(Calendar.MONTH));

			data7PreviousDays.add(dateToAdd);
		}
		
		return data7PreviousDays;
	}
}
