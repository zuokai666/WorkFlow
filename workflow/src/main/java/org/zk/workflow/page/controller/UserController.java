package org.zk.workflow.page.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(path="/login",method=RequestMethod.GET,produces="text/html")
	public ModelAndView login(){
		return new ModelAndView("login");
	}
}