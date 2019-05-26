package org.zk.workflow;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	private static final Logger log = LoggerFactory.getLogger(TestController.class);
	
	@Value("${a.b}")
	private List<String> ab;
	
	@RequestMapping(path="/test",method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public String send(){
		log.info(ab + "-");
		return "---";
	}
}