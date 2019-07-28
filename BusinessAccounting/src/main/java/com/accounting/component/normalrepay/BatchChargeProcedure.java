package com.accounting.component.normalrepay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.Account;
import com.accounting.model.Loan;
import com.accounting.model.RepayPlan;
import com.accounting.util.Db;

/**
 * 批量扣款
 * 
 */
public class BatchChargeProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(BatchChargeProcedure.class);
	
	public void run(Session session, Map<String, Object> map){
		String bizDate = Db.getBusinessDate(session);
		@SuppressWarnings("unchecked")
		List<RepayPlan> repayPlans = session
		.createQuery("from RepayPlan where endDate = :ddate")
		.setParameter("ddate", bizDate)
		.list();
		for(int i=0;i<repayPlans.size();i++){
			RepayPlan repayPlan = repayPlans.get(i);
			BigDecimal repayAmount = repayPlan.getRepayAmount();
			Loan loan = (Loan) session.createQuery("from Loan where id = :id")
					.setParameter("id", repayPlan.getLoanId()).list().get(0);
			Account account = (Account) session.createQuery("from Account where id = :id")
					.setParameter("id", loan.getAccountId()).list().get(0);
			if(account.getAmount().compareTo(repayAmount) >= 0){
				log.info("客户银行卡余额[{}]充足，批扣[{}]成功", account.getAmount(), repayAmount);
				account.setAmount(account.getAmount().subtract(repayAmount));
				session.persist(account);
				if(repayPlan.getCurrentTerm() == loan.getTerm()){
					log.info("客户最后一期还款成功，借据结清");
					loan.setFinishDate(bizDate);
					session.persist(loan);
				}
			}else {
				log.info("客户银行卡余额[{}]不足，批扣[{}]失败", account.getAmount(), repayAmount);
			}
		}
	}
}