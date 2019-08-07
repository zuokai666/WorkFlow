package com.accounting.component.normalrepay;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.exception.AccountBalanceLackException;
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
		Loan loan = session.get(Loan.class, loanId, LockMode.PESSIMISTIC_WRITE);//锁定借据，得到之后有可能是已经主动还款成功的
		if(loan.getFinishDate() != null){//DCL 双重检查锁
			log.info("用户插空的时间结清了借据，那系统就直接释放借据");
		}else {
			run(session, loan, map);
		}
	}
	
	private void run(Session session, Loan loan, Map<String, Object> map){
		String bizDate = (String) map.get("businessDate");
		Integer couponAccountId = (Integer) map.get("couponAccountId");
		@SuppressWarnings("unchecked")
		List<RepayPlan> repayPlans = session
		.createQuery("from RepayPlan where loanId = :loanId and endDate = :ddate and finishDate is null")
		.setParameter("loanId", loan.getId())
		.setParameter("ddate", bizDate)
		.list();
		Account account = (Account) session.get(Account.class, loan.getAccountId(), LockMode.PESSIMISTIC_WRITE);
		for(int i=0;i<repayPlans.size();i++){
			RepayPlan repayPlan = repayPlans.get(i);
			BigDecimal payPrincipal = repayPlan.getRepayPrincipal().subtract(repayPlan.getWaivePrincipal());
			BigDecimal payInterest = repayPlan.getRepayInterest().subtract(repayPlan.getWaiveInterest());
			BigDecimal payPrincipalPenalty = repayPlan.getRepayPrincipalPenalty().subtract(repayPlan.getWaivePrincipalPenalty());
			BigDecimal payInterestPenalty = repayPlan.getRepayInterestPenalty().subtract(repayPlan.getWaiveInterestPenalty());
			BigDecimal repayAmount = payPrincipal.add(payInterest).add(payPrincipalPenalty).add(payInterestPenalty);
			//个人账户扣款
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
				repayFlow.setPaidPrincipal(payPrincipal);
				repayFlow.setPaidInterest(payInterest);
				repayFlow.setPaidPrincipalPenalty(payPrincipalPenalty);
				repayFlow.setPaidInterestPenalty(payInterestPenalty);
				repayFlow.setPaidAmount(repayAmount);
				session.persist(repayFlow);
				//增加借据实还金额等
				loan.setPaidAmount(loan.getPaidAmount().add(repayAmount));
				loan.setPaidInterest(loan.getPaidInterest().add(payInterest));
				loan.setPaidPrincipal(loan.getPaidPrincipal().add(payPrincipal));
				loan.setPaidInterestPenalty(loan.getPaidInterestPenalty().add(payInterestPenalty));
				loan.setPaidPrincipalPenalty(loan.getPaidPrincipalPenalty().add(payPrincipalPenalty));
				if(repayPlan.getCurrentTerm() == loan.getTerm()){//结清借据
					log.info("客户最后一期还款成功，借据结清");
					loan.setFinishDate(bizDate);
					loan.setLoanStatus(Constant.loanstatus_zcjq);
				}
				session.persist(loan);
				//只有在个人账户扣款成功，才能对公账户优惠券账户扣款
				dedutionCouponAccount(session, couponAccountId, repayPlan);
			}else {
				log.info("客户[{}]银行卡余额[{}]不足，批扣[{}]失败", account.getId(), account.getAmount(), repayAmount);
			}
		}
	}
	
	//对公账户优惠券账户扣款，余额不足时抛出异常
	public void dedutionCouponAccount(Session session, Integer couponAccountId, RepayPlan repayPlan) {
		BigDecimal waiveAmount = repayPlan.getWaivePrincipal()
				.add(repayPlan.getWaiveInterest())
				.add(repayPlan.getWaivePrincipalPenalty())
				.add(repayPlan.getWaiveInterestPenalty());
		if(waiveAmount.compareTo(BigDecimal.valueOf(0.00)) <= 0){
			//不做处理
		}else {
			Account couponAccount = (Account) session.get(Account.class, couponAccountId, LockMode.PESSIMISTIC_WRITE);
			if(couponAccount.getAmount().compareTo(waiveAmount) >= 0){
				log.info("优惠券对公账户余额[{}]充足，批扣[{}]成功", couponAccount.getAmount(), waiveAmount);
				couponAccount.setAmount(couponAccount.getAmount().subtract(waiveAmount));
				session.persist(couponAccount);
			}else {
				throw new AccountBalanceLackException("优惠券对公账户["+couponAccountId+"]余额不足");
			}
		}
	}
}