package com.accounting.component.loan;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.Loan;
import com.accounting.util.Constant;

/**
 * 拦截借据，进行一些特殊处理
 * @author King
 * 
 */
public class LoanInterceptProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(LoanInterceptProcedure.class);
	
	public void run(Session session,Map<String, Object> map){
		Loan initLoan = (Loan) map.get("initLoan");
		if(initLoan.getRepayMethod().equals(Constant.repaymethod_one)){
			initLoan.setTerm(1);
			initLoan.setOriginalTerm(1);
			session.persist(initLoan);
			log.info("一次性还本付息改变期数为1");
		}
	}
}