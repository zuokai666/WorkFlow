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
import com.accounting.model.OriginalRepayPlan;

/**
 * 
 * @author King
 * 
 */
public class OriginalRepayPlanInitProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(OriginalRepayPlanInitProcedure.class);
	
	public void run(Session session,Map<String, Object> map){
		BigDecimal dayInterestRate = (BigDecimal) map.get("dayInterestRate");
		Integer loanTerm = (Integer) map.get("loanTerm");
		BigDecimal loanAmount = (BigDecimal) map.get("loanAmount");
		String repaymethodName = (String) map.get("repaymethod");
		Loan initLoan = (Loan) map.get("initLoan");
		String bizDate = (String) map.get("businessDate");
		
		List<RepaySchedule> repaySchedules = new ArrayList<>();
		for(int i=0;i<loanTerm;i++){
			OriginalRepayPlan plan = new OriginalRepayPlan();
			plan.setLoanId(initLoan.getId());
			repaySchedules.add(plan);
		}
		RepayMethod repayMethod = RepayMethodFactory.getInstance().getRepayMethod(repaymethodName);
		if(repayMethod == null){
			throw new UnSupportRepayMethodException("不支持的还款方式:" + repaymethodName);
		}
		repayMethod.fire(repaySchedules, bizDate, dayInterestRate, loanTerm, loanAmount);
		for(int i=0;i<repaySchedules.size();i++){
			OriginalRepayPlan plan = (OriginalRepayPlan) repaySchedules.get(i);
			session.persist(plan);
		}
		log.info("原始还款计划表数据插入成功");
	}
}