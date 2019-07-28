package com.accounting.component.daycut;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.SystemConfig;
import com.accounting.util.Db;
import com.accounting.util.TM;

public class DayCutProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(DayCutProcedure.class);
	
	public void run(Session session,Map<String, Object> map){
		SystemConfig config = Db.getSystemConfig(session);
		config.setBusinessDate(TM.addDay(config.getBusinessDate(), 1));
		config.setBatchDate(TM.addDay(config.getBatchDate(), 1));
		session.persist(config);
		log.info("日切成功,当前业务日期:[{}],批量日期:[{}]", config.getBusinessDate(), config.getBatchDate());
		map.put("businessDate", config.getBusinessDate());
	}
}