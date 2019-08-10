package com.accounting.rabbit.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 日终批消息消费者
 * @author King
 *
 */
@Component
@RabbitListener(queues={"${rabbit.queue.eod}"}, autoStartup="false")
public class EodMessageConsumer {
	
	private static final Logger log = LoggerFactory.getLogger(EodMessageConsumer.class);
	
	@RabbitHandler
	public void consume(String request){
		log.info("[eod-message]-begin-{}:{}",this.getClass().getSimpleName(), request);
		log.info("[eod-message]-end-{}:{}",this.getClass().getSimpleName(), request);
	}
}