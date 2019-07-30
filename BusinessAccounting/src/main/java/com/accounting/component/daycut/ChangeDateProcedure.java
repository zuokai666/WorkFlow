package com.accounting.component.daycut;

import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.SystemConfig;
import com.accounting.util.Constant;
import com.accounting.util.DB;
import com.accounting.util.TM;

/**
 * 改变日期，增加到下一日，它是独立个体，不需要统一事务管理
 * 
 * @author King
 * 
 */
public class ChangeDateProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(ChangeDateProcedure.class);
	
	public void run(Map<String, Object> map) throws Exception{
		Session session = DB.getSession();
		session.beginTransaction();
		SystemConfig config = session.get(SystemConfig.class, 1, LockMode.PESSIMISTIC_WRITE);
		config.setBusinessDate(TM.addDay(config.getBusinessDate(), 1));
		config.setBatchFlag(Constant.batchflag_eod);
		session.persist(config);
		session.getTransaction().commit();
		log.info("日切成功,当前业务日期:[{}],批量日期:[{}],状态:[{}]", config.getBusinessDate(), config.getBatchDate(), config.getBatchFlag());
		map.put("businessDate", config.getBusinessDate());
	}
}