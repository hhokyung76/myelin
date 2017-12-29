package com.ltmh.app.calculator.minute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.facebook.presto.jdbc.internal.joda.time.DateTimeUtils;
import com.ltmh.framework.util.OpenStringUtils;

/**
 * @author redskin
 *
 */
public class CalculMinute {

	public static void main(String[] args) {
//		DateTimeUtils.add(new Date(), 30);
		Calendar cal = Calendar.getInstance(); // creates calendar
		Date currDate = new Date();
	    cal.setTime(currDate); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_MONTH, 30); // adds one hour
	    Date tomorrow = cal.getTime(); // returns new date object, one hour in the future
	    
	    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    String currDateStr = df2.format(currDate);
	    System.out.println(currDateStr);
	    String tomorrowDateStr = df2.format(tomorrow);
	    System.out.println(tomorrowDateStr);
	    
	    System.out.println("get10MinuteLater: "+OpenStringUtils.get10MinuteLaterHivePartition(OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute()));

	    System.out.println("get1DayLater: "+OpenStringUtils.get2HourLaterHivePartition(OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute()));
	    System.out.println("get1DayLater: "+OpenStringUtils.get1DayLaterHivePartition(OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute()));
	    System.out.println("get7DayLater: "+OpenStringUtils.get7DayLaterHivePartition(OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute()));
	    System.out.println("get30DayLater: "+OpenStringUtils.get30DayLaterHivePartition(OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute()));
	    System.out.println("get6MonthLater: "+OpenStringUtils.get6MonthLaterHivePartition(OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute()));  
	}
}
