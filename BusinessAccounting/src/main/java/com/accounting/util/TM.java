package com.accounting.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TM {
	
	public static final String tmp = "yyyy-MM-dd";
	
	public static String addMonth(String date, int num){
		return addTM(date, num, Calendar.MONTH);
	}
	public static String addDay(String date, int num){
		return addTM(date, num, Calendar.DAY_OF_MONTH);
	}
	private static String addTM(String date, int num, int flag){
		SimpleDateFormat sdf = new SimpleDateFormat(tmp);
		try {
			Date sourceDate = sdf.parse(date);
			Calendar sourceCalendar = Calendar.getInstance();
			sourceCalendar.setTime(sourceDate);
			sourceCalendar.add(flag, num);
			return sdf.format(sourceCalendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}
	public static int intervalDate(String dateS1,String dateS2){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(tmp);
			Date date1 = sdf.parse(dateS1);
			Date date2 = sdf.parse(dateS2);
			int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
			return days;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
}