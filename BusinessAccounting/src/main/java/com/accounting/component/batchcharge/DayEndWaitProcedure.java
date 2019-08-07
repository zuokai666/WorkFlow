package com.accounting.component.batchcharge;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.exception.UnEndDayException;
import com.accounting.model.SystemConfig;
import com.accounting.util.DB;

public class DayEndWaitProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(DayEndWaitProcedure.class);
	
	public void run(Map<String, Object> map){
		Session session = DB.getSession();
		try {
			SystemConfig config = session.get(SystemConfig.class, 1);
			if(config.getBusinessDate().equals(config.getBatchDate())){
				log.info("日终批通过，进行下一步");
			}else {
				log.info("日终批不通过");
				throw new UnEndDayException("日终批不通过");
			}
			map.put("businessDate", config.getBusinessDate());
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
}