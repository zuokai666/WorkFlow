package com.accounting.component.loan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.component.repaymethod.RepayMethod;
import com.accounting.component.repaymethod.RepayMethodFactory;
import com.accounting.component.repaymethod.RepaySchedule;
import com.accounting.exception.UnSupportRepayMethodException;
import com.accounting.model.Loan;
import com.accounting.model.RepayPlan;
import com.accounting.util.DB;

/**
 * 
 * @author King
 * 
 */
public class RepayPlanInitProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(RepayPlanInitProcedure.class);
	
	public void run(Session session,Map<String, Object> map){
		BigDecimal dayInterestRate = (BigDecimal) map.get("dayInterestRate");
		Integer loanTerm = (Integer) map.get("loanTerm");
		BigDecimal loanAmount = (BigDecimal) map.get("loanAmount");
		String repaymethodName = (String) map.get("repaymethod");
		Loan initLoan = (Loan) map.get("initLoan");
		String bizDate = DB.getBusinessDate(session);
		
		List<RepaySchedule> repaySchedules = new ArrayList<>();
		for(int i=0;i<loanTerm;i++){
			RepayPlan plan = new RepayPlan();
			plan.setLoanId(initLoan.getId());
			plan.setAccrueInterest(new BigDecimal(0));
			repaySchedules.add(plan);
		}
		RepayMethod repayMethod = RepayMethodFactory.getInstance().getRepayMethod(repaymethodName);
		if(repayMethod == null){
			throw new UnSupportRepayMethodException("不支持的还款方式:" + repaymethodName);
		}
		repayMethod.fire(repaySchedules, bizDate, dayInterestRate, loanTerm, loanAmount);
		BigDecimal totalRepayInterest = new BigDecimal(0);
		for(int i=0;i<repaySchedules.size();i++){
			RepayPlan plan = (RepayPlan) repaySchedules.get(i);
			totalRepayInterest = totalRepayInterest.add(plan.getRepayInterest());
			session.persist(plan);
		}
		log.info("还款计划表数据插入成功");
		initLoan.setRepayPrincipal(loanAmount);
		initLoan.setRepayInterest(totalRepayInterest);
		initLoan.setRepayAmount(totalRepayInterest.add(loanAmount));
		session.persist(initLoan);
		log.info("更新借据表应还金额信息");
	}
}