package com.accounting.component.repay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.exception.AccountBalanceLackException;
import com.accounting.exception.MistakeReapyModeException;
import com.accounting.model.Account;
import com.accounting.model.Loan;
import com.accounting.model.RepayFlow;
import com.accounting.model.RepayPlan;
import com.accounting.util.Constant;

public class TQJQCurrentProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(TQJQCurrentProcedure.class);
	
	/**
	 * 
	 * @param session
	 * @param map loanId businessDate
	 */
	public void run(Session session,Map<String, Object> map){
		int loanId = (int) map.get("loanId");
		String businessDate = (String) map.get("businessDate");
		@SuppressWarnings("unchecked")
		List<RepayPlan> repayPlans = session
				.createQuery("from RepayPlan r where r.loanId = :loanId and r.startDate <= :ddate and r.endDate > :ddate and r.finishDate is null")
				.setParameter("loanId", loanId)
				.setParameter("ddate", businessDate)
				.list();
		if(repayPlans.size() !=1){
			throw new MistakeReapyModeException("不符合提前结清当期");
		}else {
			run(map, session, loanId);
		}
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
		.createQuery("from RepayPlan r where r.loanId = :loanId and r.startDate <= :ddate and r.endDate > :ddate and r.finishDate is null")
		.setParameter("loanId", loan.getId())
		.setParameter("ddate", bizDate)
		.list();
		Account account = (Account) session.get(Account.class, loan.getAccountId(), LockMode.PESSIMISTIC_WRITE);
		for(int i=0;i<repayPlans.size();i++){
			RepayPlan repayPlan = repayPlans.get(i);
			BigDecimal repayInterest = repayPlan.getAccrueInterest();//累计计提利息
			BigDecimal repayAmount = repayPlan.getRepayPrincipal().add(repayInterest);
			if(account.getAmount().compareTo(repayAmount) >= 0){
				log.info("客户银行卡余额[{}]充足，偿还[{}]成功", account.getAmount(), repayAmount);
				account.setAmount(account.getAmount().subtract(repayAmount));
				session.persist(account);
				repayPlan.setFinishDate(bizDate);
				repayPlan.setRemark("提前结清当期成功");
				repayPlan.setRepayInterest(repayInterest);
				repayPlan.setRepayAmount(repayAmount);
				session.persist(repayPlan);
				//增加还款流水
				RepayFlow repayFlow = new RepayFlow();
				repayFlow.setLoanId(loan.getId());
				repayFlow.setRepayDate(bizDate);
				repayFlow.setRepayMode(Constant.repaymode_tqjqCur);
				repayFlow.setPaidPrincipal(repayPlan.getRepayPrincipal());
				repayFlow.setPaidInterest(repayInterest);
				repayFlow.setPaidPrincipalPenalty(new BigDecimal(0));
				repayFlow.setPaidInterestPenalty(new BigDecimal(0));
				repayFlow.setPaidAmount(repayAmount);
				session.persist(repayFlow);
				//增加借据实还金额等
				loan.setPaidAmount(loan.getPaidAmount().add(repayFlow.getPaidAmount()));
				loan.setPaidInterest(loan.getPaidInterest().add(repayFlow.getPaidInterest()));
				loan.setPaidPrincipal(loan.getPaidPrincipal().add(repayFlow.getPaidPrincipal()));
				if(repayPlan.getCurrentTerm() == loan.getTerm()){//结清借据
					log.info("客户最后一期还款成功，借据提前结清");
					loan.setFinishDate(bizDate);
					loan.setEndDate(bizDate);//重置贷款到期日
					loan.setLoanStatus(Constant.loanstatus_tqjq);
				}
				session.persist(loan);
			}else {
				log.info("客户银行卡余额[{}]不足，提前还当期[{}]失败", account.getAmount(), repayAmount);
				throw new AccountBalanceLackException("银行卡余额不足，提前还当期["+repayAmount+"]失败");
			}
		}
	}
}