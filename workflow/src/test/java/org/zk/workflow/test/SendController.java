package org.zk.workflow.test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SendController {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit.queue.flow}")
	private String queue;
	
	@RequestMapping(path="send",method=RequestMethod.GET,produces = "text/plain")
	public String send(){
		rabbitTemplate.convertAndSend(queue, "zuokai");
		return "success";
	}
}