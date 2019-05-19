package org.zk.workflow.rabbit.consume;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FlowAction {
	
	private static final Logger log = LoggerFactory.getLogger(FlowAction.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit.queue.flow}")
	private String queue;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@RabbitListener(queues={"${rabbit.queue.flow}"},autoStartup="true")
	public void consume(String message){
		log.info("{}接收到消息,{}", Thread.currentThread().getName(), message);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			log.error("", e);
		}
		rabbitTemplate.convertAndSend(queue, dateFormat.format(new Date()));
	}
}