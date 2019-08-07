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
 * 
 * @author King
 * 
 */
public class EodCompleteProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(EodCompleteProcedure.class);
	
	public void run(Map<String, Object> map) throws Exception{
		Session session = DB.getSession();
		try {
			session.beginTransaction();
			SystemConfig config = session.get(SystemConfig.class, 1, LockMode.PESSIMISTIC_WRITE);
			config.setBatchDate(TM.addDay(config.getBatchDate(), 1));
			config.setBatchFlag(Constant.batchflag_day);
			session.persist(config);
			session.getTransaction().commit();
			session.close();
			log.info("日终批完成,当前业务日期:[{}],批量日期:[{}],状态:[{}]", config.getBusinessDate(), config.getBatchDate(), config.getBatchFlag());
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
}