package com.myelin.builder.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HivePartitionUtil {
	
	public static String getCurrentTime() {
		Date date = new Date();
		String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		
		return dateStr;
	}
	

	public static String getCurrentTimeFullDisplayHmmss() {
		Date date = new Date();
		String dateStr = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		
		return dateStr;
	}
	


}
