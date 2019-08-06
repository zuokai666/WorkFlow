package com.accounting.component.daycut;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import com.accounting.model.Loan;
import com.accounting.util.DB;
import com.accounting.util.TM;

public class EodProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(EodProcedure.class);
	
	public void run(Map<String, Object> map){
		String businessDate = (String) map.get("businessDate");
		StopWatch stopWatch = new StopWatch("EodProcedureBatch");
		Session session = null;
		try {
			stopWatch.start("queryDbTask");
			session = DB.getSession();
			@SuppressWarnings("unchecked")
			List<Integer> loanIds = session
					.createQuery("select id from Loan where loanStatus in ('zc','yq') and handleDate < :handleDate")
					.setParameter("handleDate", businessDate)
//					.setFirstResult(0)
//					.setMaxResults(100_0000)
					.list();//只查询主键，减小对内存的压力
			stopWatch.stop();
			stopWatch.start("loanTask("+loanIds.size()+")");
			ThreadPoolExecutor executor=
					new ThreadPoolExecutor(40, 50, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
			CountDownLatch latch = new CountDownLatch(loanIds.size());
			for(int i=0;i<loanIds.size();i++){
				final int _i = i;
				executor.execute(() -> handleOneLoan(map, businessDate, loanIds.get(_i), latch));
			}
			latch.await(60, TimeUnit.MINUTES);//最长等待60分钟
			stopWatch.stop();
			log.info(stopWatch.prettyPrint());
		} catch (Exception e) {
			log.error("", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
	
	public void handleOneLoan(Map<String, Object> map, String businessDate, int loanId, CountDownLatch latch) {
		Session session = null;
		try {
			log.debug("处理借据loanId=[{}]", loanId);
			session = DB.getSession();
			session.getTransaction().begin();
			Loan loan = session.get(Loan.class, loanId, LockMode.PESSIMISTIC_WRITE);//锁定借据
			while(loan.getHandleDate().compareTo(businessDate) < 0){//循环往前推进处理日期
				loan.setHandleDate(TM.addDay(loan.getHandleDate(), 1));//设置为下一日
				AccrueInterestProcedure accrueInterestProcedure = new AccrueInterestProcedure();
				accrueInterestProcedure.run(session, loan, map);
				OverdueProcedure overdueProcedure = new OverdueProcedure();
				overdueProcedure.run(session, loan, map);
			}
			session.persist(loan);
			session.getTransaction().commit();//释放借据
		} catch (Exception e) {
			log.error("处理单个借据", e);
			session.getTransaction().rollback();
		} finally {
			if(session != null){
				session.close();
			}
			if(latch != null){
				latch.countDown();
			}
		}
	}
}