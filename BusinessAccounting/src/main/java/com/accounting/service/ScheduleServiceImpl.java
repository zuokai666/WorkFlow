package com.accounting.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.component.daycut.AccrueInterestProcedure;
import com.accounting.component.daycut.DayCutProcedure;

/**
 * 计划任务服务
 * 
 * @author King
 *
 */
public class ScheduleServiceImpl {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	
	/**
	 * 每日凌晨00:00进行日切
	 */
	public void dayCut(){
		Map<String, Object> map = new HashMap<>();
		DayCutProcedure dayCutProcedure = new DayCutProcedure();
		dayCutProcedure.run(map);
		AccrueInterestProcedure accrueInterestProcedure = new AccrueInterestProcedure();
		accrueInterestProcedure.run(map);
	}
}