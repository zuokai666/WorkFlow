package org.zk.workflow.rabbit.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zk.workflow.service.FlowService;
import org.zk.workflow.service.NetLoanService;
import org.zk.workflow.util.Contant;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 不能自动启动
 * 
 * @author King
 *
 */
@Component
@RabbitListener(queues={"${rabbit.queue.flow}"}, autoStartup="false")
public class FlowActionController {
	
	private static final Logger log = LoggerFactory.getLogger(FlowActionController.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit.queue.flow}")
	private String queue;
	
	@Autowired
	@Qualifier("FlowActionServiceImpl")
	private NetLoanService netLoanService;
	
	@Autowired
	private FlowService flowService;
	
	/**
	 * 第一个任务回事整个Task对象，另外设置了两个参数，taskNo=task.主键,isFromArtificial=0 非人工
	 */
	@RabbitHandler
	public void consume(String request){
		log.info("[rabbit-message]-begin-{}:{}",this.getClass().getSimpleName(), request);
		JsonNode requestNode = null;
		try {
			requestNode = Contant.om.readValue(request, JsonNode.class);
		} catch (IOException e) {
			log.error("解析请求信息为JsonNode出错，废弃当前消息", e);
			return;
		}
		try {
			handleNode(requestNode);
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("[rabbit-message]-end-{}:{}",this.getClass().getSimpleName(), request);
	}
	
	public void handleNode(JsonNode requestNode) {
		JsonNode boReturn = netLoanService.handle(requestNode);
		log.info(boReturn.toString());
		if("TONEXT".equals(boReturn.get("FlowResult").asText().split("@")[0])){//to next
			JsonNode taskNode = flowService.getWorkFlowTaskBy(requestNode.path("taskNo").asText());
			JsonNode modelNode = flowService.getWorkFlowModelBy(taskNode.path("flowNo").asText(), taskNode.path("phaseNo").asText());
			if("MQ".equals(modelNode.path("attribute9").asText())){
				rabbitTemplate.convertAndSend(queue, createRequestNode(taskNode.path("serialNo").asText(), "0").toString());
			}else {
				handleNode(createRequestNode(taskNode.path("serialNo").asText(), "0"));
			}
		}
	}
	
	private ObjectNode createRequestNode(String taskNo,String isFromArtificial){
		ObjectNode objectNode = Contant.om.createObjectNode();
		objectNode.put("taskNo", taskNo);
		objectNode.put("isFromArtificial", isFromArtificial);
		return objectNode;
	}
}