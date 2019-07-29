package com.accounting.util;

public class Constant {
	
	public static final String batchflag_eod = "1";//日终
	public static final String batchflag_day = "2";//日间
	
	public static String resolveBatchFlag(String v){
		if(v.equals(batchflag_day)){
			return "日间状态";
		}else {
			return "日终状态";
		}
	}
}