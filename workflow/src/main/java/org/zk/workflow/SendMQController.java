package org.zk.workflow;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SendMQController {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit.queue.flow}")
	private String queue;
	
	@RequestMapping(path="/mq",method=RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public String send(@RequestBody String request){
		rabbitTemplate.convertAndSend(queue, request);
		return request;
	}
}