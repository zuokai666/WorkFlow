package com.accounting.component.normalrepay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.Account;
import com.accounting.model.Loan;
import com.accounting.model.RepayFlow;
import com.accounting.model.RepayPlan;
import com.accounting.util.Constant;

/**
 * 到期还款
 */
public class DueRepayProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(DueRepayProcedure.class);
	
	/**
	 * 
	 * @param map 必要属性：businessDate
	 * @param session
	 * @param loanId 需要还款的借据号
	 */
	public void run(Map<String, Object> map, Session session, int loanId) {
		try {
			session.getTransaction().begin();
			Loan loan = session.get(Loan.class, loanId, LockMode.PESSIMISTIC_WRITE);//锁定借据，得到之后有可能是已经主动还款成功的
			if(loan.getFinishDate() != null){//DCL 双重检查锁
				log.info("用户插空的时间结清了借据，那系统就直接释放借据");
			}else {
				run(session, loan, map);
			}
			session.getTransaction().commit();//释放借据
		} catch (Exception e) {
			log.error("", e);
			session.getTransaction().rollback();
		}
		session.clear();//help gc
	}
	
	private void run(Session session, Loan loan, Map<String, Object> map){
		String bizDate = (String) map.get("businessDate");
		@SuppressWarnings("unchecked")
		List<RepayPlan> repayPlans = session
		.createQuery("from RepayPlan where loanId = :loanId and endDate = :ddate and finishDate is null")
		.setParameter("loanId", loan.getId())
		.setParameter("ddate", bizDate)
		.list();
		Account account = (Account) session.createQuery("from Account where id = :id")
				.setParameter("id", loan.getAccountId()).list().get(0);
		for(int i=0;i<repayPlans.size();i++){
			RepayPlan repayPlan = repayPlans.get(i);
			BigDecimal repayAmount = repayPlan.getRepayAmount();
			if(account.getAmount().compareTo(repayAmount) >= 0){
				log.info("客户银行卡余额[{}]充足，批扣[{}]成功", account.getAmount(), repayAmount);
				account.setAmount(account.getAmount().subtract(repayAmount));
				session.persist(account);
				repayPlan.setFinishDate(bizDate);
				repayPlan.setRemark("到期还款成功");
				session.persist(repayPlan);
				//增加还款流水
				RepayFlow repayFlow = new RepayFlow();
				repayFlow.setLoanId(loan.getId());
				repayFlow.setRepayDate(bizDate);
				repayFlow.setRepayMode(Constant.repaymode_dqhk);
				repayFlow.setPaidPrincipal(repayPlan.getRepayPrincipal());
				repayFlow.setPaidInterest(repayPlan.getRepayInterest());
				repayFlow.setPaidAmount(repayAmount);
				session.persist(repayFlow);
				//增加借据实还金额等
				loan.setPaidAmount(loan.getPaidAmount().add(repayAmount));
				loan.setPaidInterest(loan.getPaidInterest().add(repayPlan.getRepayInterest()));
				loan.setPaidPrincipal(loan.getPaidPrincipal().add(repayPlan.getRepayPrincipal()));
				if(repayPlan.getCurrentTerm() == loan.getTerm()){//结清借据
					log.info("客户最后一期还款成功，借据结清");
					loan.setFinishDate(bizDate);
					loan.setLoanStatus(Constant.loanstatus_zcjq);
				}
				session.persist(loan);
			}else {
				log.info("客户银行卡余额[{}]不足，批扣[{}]失败", account.getAmount(), repayAmount);
				throw new UnsupportedOperationException("暂不支持余额不足问题");
			}
		}
	}
}