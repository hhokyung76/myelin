package com.myelin.builder.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OpenStringUtils {
	
	public static String getCurrentTime() {
		Date date = new Date();
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		
		return dateStr;
	}
	
	
	
	

	public static String getCurrentTime2() {
		Date date = new Date();
		String dateStr = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
		
		return dateStr;
	}
	
	public static String getCurrentTimeOfLog() {
		Date date = new Date();
		String dateStr = new SimpleDateFormat("yyyyMMddHHmmss.SSS").format(date);
		
		return dateStr;
	}
	
	public static String getCurrentTimeHHmmss() {
		Date date = new Date();
		String dateStr = new SimpleDateFormat("HHmmss").format(date);
		
		return dateStr;
	}

	public static String getCurrentTimeFullDisplayHmmss() {
		Date date = new Date();
		String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		
		return dateStr;
	}

	public static String getCurrentTimeFullDisplayHivePartitionMinute() {
		Date date = new Date();
		String dateStr = new SimpleDateFormat("yyyyMMddHHmm").format(date);
		
		return dateStr;
	}
	

	public static String getCurrentTimeHHmmssSSS() {
		Date date = new Date();
		String dateStr = new SimpleDateFormat("HHmmss.SSS").format(date);
		
		return dateStr;
	}
	
	
	public static Map<String, String> getHivePartitionMap(String partitionCurrTime) {
		String[] parts = partitionCurrTime.split("/");
		Map<String, String> partMap = new HashMap<String, String>();
		for (int ii = 0; ii < parts.length; ii++) {
			System.out.println("parts["+ii+"]= "+parts[ii]);
			if (ii == 0) {
				partMap.put("pYear", parts[ii]);
			}else if (ii == 1) {
				partMap.put("pMonth", parts[ii]);
			}else if (ii == 2) {
				partMap.put("pDay", parts[ii]);
			}else if (ii == 3) {
				partMap.put("pHour", parts[ii]);
			}else if (ii == 4) {
				partMap.put("pMinute", parts[ii]);
			}
		}
		
		return partMap;
	}
	/**
	 *
	 * @author redskin
	 *
	 */
	public static String get10MinuteLaterHivePartition(String firstTime) {
		Date firstTimeDate =null;
		try {
			firstTimeDate = new SimpleDateFormat("yyyyMMddHHmm").parse(firstTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("firstTime: "+firstTime);
		System.out.println(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
		Calendar cal = Calendar.getInstance(); // creates calendar
//		Date currDate = new Date();
	    cal.setTime(firstTimeDate); // sets calendar time/date
	    cal.add(Calendar.MINUTE, 10); // adds one hour
	    Date tomorrow = cal.getTime(); // returns new date object, one hour in the future

		System.out.println(new SimpleDateFormat("yyyyMMddHHmm").format(tomorrow));
		
	    DateFormat df2 = new SimpleDateFormat("yyyy/yyyyMM/yyyyMMdd/yyyyMMddHH/yyyyMMddHHmm");
	    String tomorrowDateStr = df2.format(tomorrow);
	    
	    return tomorrowDateStr;
	}

	public static String get2HourLaterHivePartition(String firstTime) {
		Date firstTimeDate =null;
		try {
			firstTimeDate = new SimpleDateFormat("yyyyMMddHHmm").parse(firstTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(firstTimeDate); // sets calendar time/date
	    cal.add(Calendar.HOUR, 2); // adds one hour
	    Date tomorrow = cal.getTime(); // returns new date object, one hour in the future

	    DateFormat df2 = new SimpleDateFormat("yyyy/yyyyMM/yyyyMMdd/yyyyMMddHH/yyyyMMddHHmm");
	    String tomorrowDateStr = df2.format(tomorrow);
	    
	    return tomorrowDateStr;
	}


	public static String get1DayLaterHivePartition(String firstTime) {
		Date firstTimeDate =null;
		try {
			firstTimeDate = new SimpleDateFormat("yyyyMMddHHmm").parse(firstTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(firstTimeDate); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_YEAR, 1); // adds one hour
	    Date tomorrow = cal.getTime(); // returns new date object, one hour in the future

	    DateFormat df2 = new SimpleDateFormat("yyyy/yyyyMM/yyyyMMdd/yyyyMMddHH/yyyyMMddHHmm");
	    String tomorrowDateStr = df2.format(tomorrow);
	    
	    return tomorrowDateStr;
	}


	public static String get7DayLaterHivePartition(String firstTime) {
		Date firstTimeDate =null;
		try {
			firstTimeDate = new SimpleDateFormat("yyyyMMddHHmm").parse(firstTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(firstTimeDate); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_YEAR, 7); // adds one hour
	    Date tomorrow = cal.getTime(); // returns new date object, one hour in the future

	    DateFormat df2 = new SimpleDateFormat("yyyy/yyyyMM/yyyyMMdd/yyyyMMddHH/yyyyMMddHHmm");
	    String tomorrowDateStr = df2.format(tomorrow);
	    
	    return tomorrowDateStr;
	}
	
	public static String get30DayLaterHivePartition(String firstTime) {
		Date firstTimeDate =null;
		try {
			firstTimeDate = new SimpleDateFormat("yyyyMMddHHmm").parse(firstTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(firstTimeDate); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_YEAR, 30); // adds one hour
	    Date tomorrow = cal.getTime(); // returns new date object, one hour in the future

	    DateFormat df2 = new SimpleDateFormat("yyyy/yyyyMM/yyyyMMdd/yyyyMMddHH/yyyyMMddHHmm");
	    String tomorrowDateStr = df2.format(tomorrow);
	    
	    return tomorrowDateStr;
	}

	public static String get6MonthLaterHivePartition(String firstTime) {
		Date firstTimeDate =null;
		try {
			firstTimeDate = new SimpleDateFormat("yyyyMMddHHmm").parse(firstTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(firstTimeDate); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_YEAR, 180); // adds one hour
	    Date tomorrow = cal.getTime(); // returns new date object, one hour in the future

	    DateFormat df2 = new SimpleDateFormat("yyyy/yyyyMM/yyyyMMdd/yyyyMMddHH/yyyyMMddHHmm");
	    String tomorrowDateStr = df2.format(tomorrow);
	    
	    return tomorrowDateStr;
	}

}
