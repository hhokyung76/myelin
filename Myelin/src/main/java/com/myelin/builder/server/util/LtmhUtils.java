package com.myelin.builder.server.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.facebook.presto.jdbc.internal.jetty.http2.parser.PingBodyParser;
import com.myelin.builder.framework.util.OpenStringUtils;


public class LtmhUtils {

	private static int[] pibonacciArr = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765};
	
	public static void main(String[] args) throws ParseException {
//		int prevTime = 0;
//		for (int ii = 0; ii < pibonacciArr.length; ii++) {
//			System.out.println("pibonacciArr["+ii+"] ==> "+pibonacciArr[ii]);
//			if (pibonacciArr.length > (ii+1)) {
//				System.out.println(pibonacciArr[ii+1]);
//			}
//		}
		
		List<Map<String, String>> ltmhPartitions = new ArrayList<Map<String, String>>();

		Date currDate = new Date();
	    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
	    String currDateStr = dateFormat.format(currDate);
	    System.out.println(OpenStringUtils.getCurrentTimeFullDisplayHmmss());
	    for (int jj = 0; jj < 100000; jj++) {
		    List<Map<String, String>> myelinPartitions = getMyelinTimeMaps(currDateStr);
//		    for (int ii = 0; ii < myelinPartitions.size(); ii++) {
//		    	Map<String, String> info = myelinPartitions.get(ii);
//		    	System.out.println("Temp Info: "+info);
//		    }
	    }
	    System.out.println(OpenStringUtils.getCurrentTimeFullDisplayHmmss());
//	    System.out.println(currDateStr);
//		int a1 = 1;
//		
//		Calendar cal = Calendar.getInstance(); // creates calendar
//	    cal.setTime(currDate); // sets calendar time/date
//	    cal.add(Calendar.HOUR, 1); // adds one hour
//	    Date oneHourAfter = cal.getTime(); // returns new date object, one hour in the future
//
//	    System.out.println("oneHourAfter= "+dateFormat.format(oneHourAfter));
//	    getMyelinTimes(dateFormat.format(oneHourAfter));
//        int a2 = 1;
//        cal.setTime(currDate); // sets calendar time/date
//	    cal.add(Calendar.DAY_OF_YEAR, 1); // adds one hour
//	    Date oneDayAfter = cal.getTime(); // returns new date object, one hour in the future
//
//	    System.out.println("oneDayAfter= "+dateFormat.format(oneDayAfter));
//	    getMyelinTimes(dateFormat.format(oneDayAfter));
//	    
//        int a3;
//        
//        System.out.println(a1);
//        System.out.println(a2);
//        Date nextDayAfter = oneDayAfter;
//        System.out.println("======================================================================");
//        
//        for(int i=1; i<=14; i++){
//            a3=a1+a2;
//            System.out.println(a3);
//            
//            cal.setTime(nextDayAfter); // sets calendar time/date
//    	    cal.add(Calendar.DAY_OF_YEAR, a3); // adds one hour
//    	    nextDayAfter = cal.getTime(); // returns new date object, one hour in the future
//    	    System.out.println("nextDayAfter= "+dateFormat.format(nextDayAfter));
//    	    getMyelinTimes(dateFormat.format(nextDayAfter));
//    	    
//            a1=a2;
//            a2=a3;
//        }      
	}
	
	
	public static List<Map<String, String>> getMyelinTimeMaps(String currTimeStr) throws ParseException {
//		int prevTime = 0;
//		for (int ii = 0; ii < pibonacciArr.length; ii++) {
//			System.out.println("pibonacciArr["+ii+"] ==> "+pibonacciArr[ii]);
//			if (pibonacciArr.length > (ii+1)) {
//				System.out.println(pibonacciArr[ii+1]);
//			}
//		}
		
		List<Map<String, String>> myelinPartitions = new ArrayList<Map<String, String>>();

		//Date currDate = new Date();
	    DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
	    Date currDate = dateFormat.parse(currTimeStr);
//	    System.out.println(currTimeStr);
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(currDate); // sets calendar time/date
	    cal.add(Calendar.HOUR, 1); // adds one hour
	    Date oneHourAfter = cal.getTime(); // returns new date object, one hour in the future
//	    System.out.println("oneHourAfter= "+dateFormat.format(oneHourAfter));
	    myelinPartitions.add(getMyelinTimes("0-1H", dateFormat.format(oneHourAfter)));

		int a1 = 1;

        cal.setTime(currDate); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_YEAR, 1); // adds one hour
	    Date oneDayAfter = cal.getTime(); // returns new date object, one hour in the future

//	    System.out.println("oneDayAfter= "+dateFormat.format(oneDayAfter));
	    myelinPartitions.add(getMyelinTimes("0-1", dateFormat.format(oneDayAfter)));
	    
        int a2 = 1;
        cal.setTime(currDate); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_YEAR, 2); // adds one hour
	    Date twoDayAfter = cal.getTime(); // returns new date object, one hour in the future

//	    System.out.println("twoDayAfter= "+dateFormat.format(twoDayAfter));
	    myelinPartitions.add(getMyelinTimes("1-1", dateFormat.format(twoDayAfter)));
	    
        int a3;
        
//        System.out.println(a1);
//        System.out.println(a2);
        Date nextDayAfter = twoDayAfter;
//        System.out.println("======================================================================");
        
        for(int i=1; i<=14; i++){
            a3=a1+a2;
//            System.out.println(a3);
            
            cal.setTime(nextDayAfter); // sets calendar time/date
    	    cal.add(Calendar.DAY_OF_YEAR, a3); // adds one hour
    	    nextDayAfter = cal.getTime(); // returns new date object, one hour in the future
//    	    System.out.println("nextDayAfter= "+dateFormat.format(nextDayAfter));
    	    myelinPartitions.add(getMyelinTimes(Integer.toString(a2)+"-"+Integer.toString(a3), dateFormat.format(nextDayAfter)));
    	    
            a1=a2;
            a2=a3;
        }
		return myelinPartitions;      
	}
	

	public static Map<String, String> getMyelinTimes(String flag, String minuteTime) {

		String pYear = minuteTime.substring(0, 4);
		String pMonth = minuteTime.substring(0, 6);
		String pDay = minuteTime.substring(0, 8);
		String pHour = minuteTime.substring(0, 10);
		String pMinute  = minuteTime;
		
		Map<String, String> myelinTimesInfo = new HashMap<String, String>();
		myelinTimesInfo.put("MyelinFlag", flag);
		myelinTimesInfo.put("pYear", pYear);
		myelinTimesInfo.put("pMonth", pMonth);
		myelinTimesInfo.put("pDay", pDay);
		myelinTimesInfo.put("pHour", pHour);
		myelinTimesInfo.put("pMinute", pMinute);
		
//		System.out.println("myelinTimesInfo: "+myelinTimesInfo);
		return myelinTimesInfo;
	}
	public static Map<String, String> getLtmhPartion() {
		Date date = new Date();
		String pYear = new SimpleDateFormat("yyyy").format(date);
		String pMonth = new SimpleDateFormat("yyyyMM").format(date);
		String pDay = new SimpleDateFormat("yyyyMMdd").format(date);
		String pHour = new SimpleDateFormat("yyyyMMddHH").format(date);
		String pMinute  = new SimpleDateFormat("yyyyMMddHHmm").format(date);
		
		Map<String, String> ltmhPartionInfo = new HashMap<String, String>();
		ltmhPartionInfo.put("pYear", pYear);
		ltmhPartionInfo.put("pMonth", pMonth);
		ltmhPartionInfo.put("pDay", pDay);
		ltmhPartionInfo.put("pHour", pHour);
		ltmhPartionInfo.put("pMinute", pMinute);
		return ltmhPartionInfo;
	}
	
	public static String getRandomUUID() {
		UUID rndId = UUID.randomUUID();
		
		return rndId.toString().replaceAll("-", "").substring(1, 14)+OpenStringUtils.getCurrentTimeHHmmssSSS();
		
	}
	
	public static void main2(String[] args) {
		String temp = LtmhUtils.getRandomUUID();
		System.out.println("temp: "+temp);
		String temp2 = LtmhUtils.getRandomUUID();
		System.out.println("temp: "+temp2);
	}
	
	public static String get1HourAfterPartition(String firstTime) {
		Date firstTimeDate =null;
		try {
			firstTimeDate = new SimpleDateFormat("yyyyMMddHHmm").parse(firstTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(firstTimeDate); // sets calendar time/date
	    cal.add(Calendar.HOUR, 1); // adds one hour
	    Date tomorrow = cal.getTime(); // returns new date object, one hour in the future

	    DateFormat df2 = new SimpleDateFormat("yyyy/yyyyMM/yyyyMMdd/yyyyMMddHH/yyyyMMddHHmm");
	    String tomorrowDateStr = df2.format(tomorrow);
	    
	    return tomorrowDateStr;
	}
	
	public static String get1DayAfterPartition(String firstTime) {
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
}
