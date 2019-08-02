package com.accounting.component.normalrepay;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.util.DB;

/**
 * 批量扣款
 */
public class BatchChargeProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(BatchChargeProcedure.class);
	
	/**
	 * @param map 必要属性：businessDate
	 */
	public void run(Map<String, Object> map){
		String businessDate = (String) map.get("businessDate");
		Session session = null;
		try {
			session = DB.getSession();
			@SuppressWarnings("unchecked")
			List<Integer> loanIds = session
					.createQuery("select l.id from Loan l,RepayPlan r where l.id = r.loanId and r.endDate = :ddate and r.finishDate is null")//查询符合批扣条件的借据
					.setParameter("ddate", businessDate)
					.list();//只查询主键，减小对内存的压力
			DueRepayProcedure dueRepayProcedure = new DueRepayProcedure();
			for(int i=0;i<loanIds.size();i++){
				int loanId = loanIds.get(i);
				dueRepayProcedure.run(map, session, loanId);
			}
		} catch (Exception e) {
			log.error("", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
}