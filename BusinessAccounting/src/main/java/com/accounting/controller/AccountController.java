package com.accounting.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accounting.model.Account;
import com.accounting.util.DB;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value="/list")
	public ModelAndView list(){
		return new ModelAndView("account/list");
	}
	
	@RequestMapping(value="/listAction",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Account> listAction(){
		Integer accountId = (Integer) request.getSession().getAttribute("accoundId");
		Session session = DB.getSession();
		@SuppressWarnings("unchecked")
		List<Account> accountIds = session
				.createQuery("from Account where id = :id")
				.setParameter("id", accountId)
				.list();
		return accountIds;
	}
}