package com.accounting.component.daycut;

import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.Loan;
import com.accounting.util.DB;
import com.accounting.util.TM;

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
			
			
			
			@SuppressWarnings("unchecked")
			List<Integer> loanIds = session
					.createQuery("select id from Loan where loanStatus in ('zc','yq') and handleDate < :handleDate")
					.setParameter("handleDate", businessDate)
					.list();//只查询主键，减小对内存的压力
			for(int i=0;i<loanIds.size();i++){
				try {
					int loanId = loanIds.get(i);
					session.getTransaction().begin();
					Loan loan = session.get(Loan.class, loanId, LockMode.PESSIMISTIC_WRITE);//锁定借据
					while(loan.getHandleDate().compareTo(businessDate) < 0){//循环往前推进处理日期
						loan.setHandleDate(TM.addDay(loan.getHandleDate(), 1));//设置为下一日
						AccrueInterestProcedure accrueInterestProcedure = new AccrueInterestProcedure();
						accrueInterestProcedure.run(session, loan, map);
					}
					session.persist(loan);
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