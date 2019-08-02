package com.accounting.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accounting.bean.BizResponse;
import com.accounting.model.Account;
import com.accounting.model.Loan;
import com.accounting.model.RepayFlow;
import com.accounting.model.RepayPlan;
import com.accounting.service.AccountingServiceImpl;
import com.accounting.util.DB;

@RestController
@RequestMapping("/loan")
public class LoanController {
	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value="/doloan")
	public ModelAndView login(){
		return new ModelAndView("loan/doloan");
	}
	
	@RequestMapping(value="/doLoanAction",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BizResponse loginAction(
			@RequestParam("loanAmount") String loanAmount,
			@RequestParam("loanTerm") int loanTerm,
			@RequestParam("dayInterestRate") String dayInterestRate,
			@RequestParam("repaymethod") String repaymethod){
		Integer accountId = (Integer) request.getSession().getAttribute("accoundId");
		if(accountId == null){
			BizResponse.Builder builder = new BizResponse.Builder();
			return builder.fail().tip("账户未登录，贷款失败").build();
		}
		AccountingServiceImpl accountingService = new AccountingServiceImpl();
		Map<String, Object> map = new HashMap<>();
		map.put("loanAmount", new BigDecimal(loanAmount));
		map.put("loanTerm", loanTerm);
		map.put("dayInterestRate", new BigDecimal(dayInterestRate));
		map.put("accountId", accountId);
		map.put("repaymethod", repaymethod);
		return accountingService.loan(map);
	}
	
	@RequestMapping(value="/loans")
	public ModelAndView loans(){
		return new ModelAndView("loan/loans");
	}
	
	@RequestMapping(value="/listLoans",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Loan> listLoans(){
		Integer accountId = (Integer) request.getSession().getAttribute("accoundId");
		Session session = DB.getSession();
		@SuppressWarnings("unchecked")
		List<Loan> accountIds = session
				.createQuery("from Loan where accountId = :accountId")
				.setParameter("accountId", accountId)
				.list();
		return accountIds;
	}
	
	@RequestMapping(value="/repayscheduleAction",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<RepayPlan> repayscheduleAction(@RequestParam("loanId") int loanId){
		Session session = DB.getSession();
		@SuppressWarnings("unchecked")
		List<RepayPlan> accountIds = session
				.createQuery("from RepayPlan where loanId = :loanId")
				.setParameter("loanId", loanId)
				.list();
		return accountIds;
	}
	
	@RequestMapping(value="/repayflowAction",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<RepayFlow> repayflowAction(@RequestParam("loanId") int loanId){
		Session session = DB.getSession();
		@SuppressWarnings("unchecked")
		List<RepayFlow> accountIds = session
				.createQuery("from RepayFlow where loanId = :loanId")
				.setParameter("loanId", loanId)
				.list();
		return accountIds;
	}
	
	@RequestMapping(value="/accountAction",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Account accountAction(){
		Integer accountId = (Integer) request.getSession().getAttribute("accoundId");
		Session session = DB.getSession();
		@SuppressWarnings("unchecked")
		List<Account> accountIds = session
				.createQuery("from Account where id = :id")
				.setParameter("id", accountId)
				.list();
		if(accountIds.size() == 0){
			return new Account();
		}else {
			return accountIds.get(0);
		}
	}
}