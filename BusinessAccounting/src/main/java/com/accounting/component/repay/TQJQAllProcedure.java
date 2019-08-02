package com.accounting.component.repay;

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

public class TQJQAllProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(TQJQAllProcedure.class);
	
	/**
	 * 
	 * @param session
	 * @param map loanId businessDate
	 */
	public void run(Session session,Map<String, Object> map){
		int loanId = (int) map.get("loanId");
		run(map, session, loanId);
	}
	
	/**
	 * 
	 * @param map 必要属性：businessDate
	 * @param session
	 * @param loanId 需要还款的借据号
	 */
	public void run(Map<String, Object> map, Session session, int loanId) {
		Loan loan = session.get(Loan.class, loanId, LockMode.PESSIMISTIC_WRITE);//锁定借据，得到之后有可能是已经主动还款成功的
		if(loan.getFinishDate() != null){//DCL 双重检查锁
			log.info("用户插空的时间结清了借据，那系统就直接释放借据");
		}else {
			run(session, loan, map);
		}
	}
	
	private void run(Session session, Loan loan, Map<String, Object> map){
		String bizDate = (String) map.get("businessDate");
		@SuppressWarnings("unchecked")
		List<RepayPlan> repayPlans = session
		.createQuery("from RepayPlan r where r.loanId = :loanId and r.finishDate is null")//找到未结清的所有还款计划
		.setParameter("loanId", loan.getId())
		.list();
		Account account = (Account) session.createQuery("from Account where id = :id")
				.setParameter("id", loan.getAccountId()).list().get(0);
		//计算利息+本金
		BigDecimal interest = new BigDecimal(0);
		BigDecimal principal = new BigDecimal(0);
		for(int i=0;i<repayPlans.size();i++){
			RepayPlan repayPlan = repayPlans.get(i);
			interest = interest.add(repayPlan.getAccrueInterest());
			principal = principal.add(repayPlan.getRepayPrincipal());
			
			repayPlan.setFinishDate(bizDate);
			repayPlan.setRemark("提前结清所有成功");
			session.persist(repayPlan);
		}
		BigDecimal repayAmount = principal.add(interest);
		if(account.getAmount().compareTo(repayAmount ) >= 0){
			log.info("客户银行卡余额[{}]充足，偿还[{}]成功", account.getAmount(), repayAmount);
			account.setAmount(account.getAmount().subtract(repayAmount));
			session.persist(account);
			//增加还款流水
			RepayFlow repayFlow = new RepayFlow();
			repayFlow.setLoanId(loan.getId());
			repayFlow.setRepayDate(bizDate);
			repayFlow.setRepayMode(Constant.repaymode_tqjqAll);
			repayFlow.setPaidPrincipal(principal);
			repayFlow.setPaidInterest(interest);
			repayFlow.setPaidAmount(repayAmount);
			session.persist(repayFlow);
			//增加借据实还金额等
			loan.setPaidAmount(loan.getPaidAmount().add(repayAmount));
			loan.setPaidInterest(loan.getPaidInterest().add(interest));
			loan.setPaidPrincipal(loan.getPaidPrincipal().add(principal));
			log.info("客户还款成功，借据提前结清");
			loan.setFinishDate(bizDate);
			loan.setLoanStatus(Constant.loanstatus_tqjq);
			session.persist(loan);
		}else {
			log.info("客户银行卡余额[{}]不足，批扣[{}]失败", account.getAmount(), repayAmount);
			throw new UnsupportedOperationException("暂不支持余额不足问题");
		}
	}
}