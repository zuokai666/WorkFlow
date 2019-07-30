package com.accounting.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accounting.model.Loan;
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
	public String loginAction(){
		return null;
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
				.list();//只查询主键，减小对内存的压力
		return accountIds;
	}
	
	@RequestMapping(value="/repayscheduleAction",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String repayscheduleAction(){
		return null;
	}
	
	@RequestMapping(value="/repayflowAction",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String repayflowAction(){
		return null;
	}
}