package com.accounting.rabbit.config;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 不使用自动启动监听功能，等系统加载完成，再通过这个类启动监听，保证数据库等数据库等初始化完成后再监听处理数据
 * 
 * @author King
 * 
 */
@Configuration
@Order(9)
public class RabbitStartupRunner implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(RabbitStartupRunner.class);
	
	@Resource
	private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("服务rabbitListener启动......");
		rabbitListenerEndpointRegistry.start();
	}
	
	@PreDestroy
	public void destroy(){
		log.info("服务rabbitListener销毁......");
		rabbitListenerEndpointRegistry.stop();
	}
}