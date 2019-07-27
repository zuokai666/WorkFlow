package com.accounting.component.loan;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 表清理过程
 * 
 * @author King
 *
 */
public class TableClearProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(TableClearProcedure.class);
	
	public void run(Session session,Map<String, Object> map){
		int rows = session.createSQLQuery("delete from Loan").executeUpdate();
		log.info("影响行数:[{}]", rows);
		
		rows = session.createSQLQuery("delete from RepayFlow").executeUpdate();
		log.info("影响行数:[{}]", rows);
		
		rows = session.createSQLQuery("delete from RepayPlan").executeUpdate();
		log.info("影响行数:[{}]", rows);
	}
}