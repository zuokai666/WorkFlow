package org.zk.workflow.page.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/login")
	public ModelAndView login(){
		return new ModelAndView("login");
	}
}