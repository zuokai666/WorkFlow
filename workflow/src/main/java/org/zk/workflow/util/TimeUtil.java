package org.zk.workflow.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	
	public static String now(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}
	
	public static String key(){
		return "" + System.currentTimeMillis();
	}
}