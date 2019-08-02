package com.accounting.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.component.daycut.BillStatisticsProcedure;
import com.accounting.component.daycut.ChangeDateProcedure;
import com.accounting.component.daycut.EodProcedure;
import com.accounting.component.normalrepay.BatchChargeProcedure;
import com.accounting.component.normalrepay.DayEndWaitProcedure;
import com.accounting.component.daycut.EodCompleteProcedure;

/**
 * 计划任务服务
 * 
 * @author King
 *
 */
public class ScheduleServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	
	/**
	 * 每日凌晨00:00进行日切，日终批
	 */
	public void dayCut(){
		try {
			Map<String, Object> map = new HashMap<>();
			ChangeDateProcedure dayCutProcedure = new ChangeDateProcedure();
			dayCutProcedure.run(map);
			EodProcedure eodProcedure = new EodProcedure();
			eodProcedure.run(map);
			BillStatisticsProcedure billStatisticsProcedure = new BillStatisticsProcedure();
			billStatisticsProcedure.run(map);
			EodCompleteProcedure eodCompleteProcedure = new EodCompleteProcedure();
			eodCompleteProcedure.run(map);
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	public void batchCharge(){
		try {
			Map<String, Object> map = new HashMap<>();
			DayEndWaitProcedure dayEndWaitProcedure = new DayEndWaitProcedure();
			dayEndWaitProcedure.run(map);
			BatchChargeProcedure batchChargeProcedure = new BatchChargeProcedure();
			batchChargeProcedure.run(map);
		} catch (Exception e) {
			log.error("", e);
		}
	}
}