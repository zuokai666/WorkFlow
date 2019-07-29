package com.accounting.component.daycut;

import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.SystemConfig;
import com.accounting.util.Constant;
import com.accounting.util.Db;
import com.accounting.util.TM;

public class DayCutProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(DayCutProcedure.class);
	
	public void run(Map<String, Object> map){
		Session session = Db.getSession();
		SystemConfig config = session.get(SystemConfig.class, 1, LockMode.PESSIMISTIC_WRITE);
		config.setBusinessDate(TM.addDay(config.getBusinessDate(), 1));
		config.setBatchFlag(Constant.batchflag_eod);
		session.persist(config);
		log.info("日切成功,当前业务日期:[{}],批量日期:[{}],状态:[{}]", config.getBusinessDate(), config.getBatchDate(), Constant.resolveBatchFlag(config.getBatchFlag()));
		map.put("businessDate", config.getBusinessDate());
	}
}