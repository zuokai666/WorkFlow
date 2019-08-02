package com.accounting.service;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.bean.BizResponse;
import com.accounting.component.loan.LoanCheckUnClearProcedure;
import com.accounting.component.loan.LoanInitProcedure;
import com.accounting.component.loan.LoanInterceptProcedure;
import com.accounting.component.loan.OriginalRepayPlanInitProcedure;
import com.accounting.component.loan.RepayPlanInitProcedure;
import com.accounting.component.normalrepay.DayEndWaitProcedure;
import com.accounting.component.repay.AdaptiveRepayProcedure;
import com.accounting.component.repay.LoanCheckClearProcedure;
import com.accounting.util.DB;

public class AccountingServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(AccountingServiceImpl.class);
	
	public BizResponse loan(Map<String, Object> map){
		BizResponse.Builder builder = new BizResponse.Builder();
		Session session = null;
		try {
			session = DB.getSession();
			session.getTransaction().begin();
//			TableClearProcedure tableClearProcedure = new TableClearProcedure();
//			tableClearProcedure.run(session, map);
			LoanCheckUnClearProcedure loanCheckProcedure = new LoanCheckUnClearProcedure();
			loanCheckProcedure.run(session, map);
			LoanInitProcedure loanInitProcedure = new LoanInitProcedure();
			loanInitProcedure.run(session, map);
			LoanInterceptProcedure loanInterceptProcedure = new LoanInterceptProcedure();
			loanInterceptProcedure.run(session, map);
			RepayPlanInitProcedure repayPlanInitProcedure = new RepayPlanInitProcedure();
			repayPlanInitProcedure.run(session, map);
			OriginalRepayPlanInitProcedure originalRepayPlanInitProcedure = new OriginalRepayPlanInitProcedure();
			originalRepayPlanInitProcedure.run(session, map);
			session.getTransaction().commit();
			return builder.success().tip("贷款成功").build();
		} catch (Exception e) {
			log.error("", e);
			session.getTransaction().rollback();
			return builder.fail().tip("贷款失败:" + e.getMessage()).build();
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
	
	public BizResponse repay(Map<String, Object> map){
		BizResponse.Builder builder = new BizResponse.Builder();
		Session session = null;
		try {
			session = DB.getSession();
			session.getTransaction().begin();
			DayEndWaitProcedure dayEndWaitProcedure = new DayEndWaitProcedure();
			dayEndWaitProcedure.run(map);
			LoanCheckClearProcedure loanCheckClearProcedure = new LoanCheckClearProcedure();
			loanCheckClearProcedure.run(session, map);
			AdaptiveRepayProcedure adaptiveRepayProcedure = new AdaptiveRepayProcedure();
			adaptiveRepayProcedure.run(session, map);
			session.getTransaction().commit();
			return builder.success().tip("还款成功").build();
		} catch (Exception e) {
			log.error("", e);
			session.getTransaction().rollback();
			return builder.fail().tip("还款失败:" + e.getMessage()).build();
		} finally {
			if(session != null){
				session.close();
			}
		}
	}
}