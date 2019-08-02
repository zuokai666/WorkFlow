package com.accounting.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.accounting.bean.BizResponse;
import com.accounting.util.DB;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private HttpServletRequest request;
	
	@SuppressWarnings("unused")
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/login")
	public ModelAndView login(){
		return new ModelAndView("user/login");
	}
	
	@RequestMapping(value="/loginAction",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BizResponse loginAction(
			@RequestParam("username") String username, 
			@RequestParam("password") String password){
		BizResponse.Builder builder = new BizResponse.Builder();
		Session session = DB.getSession();
		@SuppressWarnings("unchecked")
		List<Integer> accountIds = session
				.createQuery("select id from Account where username = :username and password = :password")
				.setParameter("username", username)
				.setParameter("password", password)
				.list();//只查询主键，减小对内存的压力
		if(accountIds.size() == 1){
			request.getSession().setAttribute("accoundId", accountIds.get(0));
			return builder.success().tip("登录成功").build();
		}else {
			return builder.fail().tip("登录失败").build();
		}
	}
	
	@RequestMapping(value="/index")
	public ModelAndView index(){
		return new ModelAndView("user/index");
	}
	
	@RequestMapping(value="/about")
	public ModelAndView about(){
		return new ModelAndView("user/about");
	}
}