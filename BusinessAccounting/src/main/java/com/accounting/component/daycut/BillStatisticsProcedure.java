package com.accounting.component.daycut;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.util.DB;

public class BillStatisticsProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(BillStatisticsProcedure.class);
	
	@SuppressWarnings({ "deprecation", "unused", "rawtypes" })
	public void run(Map<String, Object> map){
		String businessDate = (String) map.get("businessDate");
		String batchDate = (String) map.get("batchDate");
		Session session = null;
		try {
			session = DB.getSession();
			List list = session
					.createNativeQuery("select sum(loanPrincipal),sum(paidAmount) from Loan where finishDate is not null")
					.unwrap(NativeQuery.class)
					.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					.getResultList();
			List<?> lastDayList = session.createNativeQuery("select sum(loanPrincipal),sum(paidAmount) from Loan where finishDate is not null").getResultList();
			
			
			
			
		} catch (Exception e) {
			log.error("", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
}