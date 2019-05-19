package org.zk.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SendController {
	
	private static final Logger log = LoggerFactory.getLogger(SendController.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit.queue.flow}")
	private String queue;
	
	@RequestMapping(path="/send",method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public String send(){
		log.info("{}准备发送消息,{}", Thread.currentThread().getName());
		rabbitTemplate.convertAndSend(queue, "zuokai");
		return "{\"result\":\"1\"}";
	}
}