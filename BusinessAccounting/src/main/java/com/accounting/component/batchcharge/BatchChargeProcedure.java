package com.accounting.component.batchcharge;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.util.DB;

/**
 * 批量扣款(到期还款/逾期还款)
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
//			List<Integer> loanIds = session
//					.createQuery("select l.id from Loan l,RepayPlan r where l.id = r.loanId and r.endDate <= :ddate and r.finishDate is null")//查询符合批扣条件的借据
//					.setParameter("ddate", businessDate)
//					.list();//只查询主键，减小对内存的压力
			String sql = "select l.id from Loan l where EXISTS(select 1 from RepayPlan r where l.id = r.loanId and r.endDate <= :ddate and r.finishDate is null);";
			@SuppressWarnings("unchecked")
			List<Integer> loanIds = session.createNativeQuery(sql)
					.setParameter("ddate", businessDate)
					.getResultList();//只查询主键，减小对内存的压力
			log.info("批量扣款,符合条件借据[{}]条", loanIds.size());
			DueRepayProcedure dueRepayProcedure = new DueRepayProcedure();
			OverdueRepayProcedure overdueRepayProcedure = new OverdueRepayProcedure();
			for(int i=0;i<loanIds.size();i++){
				int loanId = loanIds.get(i);
				try {
					session.getTransaction().begin();
					overdueRepayProcedure.run(map, session, loanId);//先扣逾期还款
					dueRepayProcedure.run(map, session, loanId);//再扣正常还款
					session.getTransaction().commit();//释放借据
				} catch (Exception e) {
					log.error("", e);
					session.getTransaction().rollback();
				}
				session.clear();//help gc
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