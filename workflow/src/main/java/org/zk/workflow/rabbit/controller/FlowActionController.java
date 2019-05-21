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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.zk.workflow.service.NetLoanService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit.queue.flow}")
	private String queue;
	
	private ObjectMapper om = new ObjectMapper();
	
	@Autowired
	@Qualifier("FlowActionServiceImpl")
	private NetLoanService netLoanService;
	
	/**
	 * 第一个任务回事整个Task对象，另外设置了两个参数，TaskNo=task.主键,IsFromArtificial=0
	 * @param request
	 */
	@RabbitHandler
	public void consume(String request){
		log.info("[rabbit-message]-begin-{}:{}",this.getClass().getSimpleName(), request);
		JsonNode requestNode = null;
		try {
			requestNode = om.readValue(request, JsonNode.class);
		} catch (IOException e) {
			log.error("解析请求信息为JsonNode出错，废弃当前消息", e);
			return;
		}
		try {
			handleNode(requestNode);//这里提供所有节点的统一事务管理
		} catch (Exception e) {
			log.error("", e);
		}
		log.info("[rabbit-message]-end-{}:{}",this.getClass().getSimpleName(), request);
	}
	
	public void handleNode(JsonNode requestNode) {
		JsonNode boReturn = netLoanService.handle(requestNode);
		log.info(boReturn.toString());
		if("TONEXT".equals(boReturn.get("FlowResult").asText().split("@")[0])){//to next
			SqlRowSet task = jdbcTemplate.queryForRowSet(
					"select * from WorkFlowTask where relativeSerialNo = '"+requestNode.path("taskNo").asText()+"'");
			if(task.next()){
				SqlRowSet model = jdbcTemplate.queryForRowSet(
						"select * from WorkFlowModel where flowNo = '"+task.getString("flowNo")+"' and phaseNo = '"+task.getString("phaseNo")+"'");
				model.next();
				ObjectNode transReq = om.createObjectNode();
				transReq.put("taskNo", task.getString("serialNo"));
				transReq.put("isFromArtificial", "0");
				if("MQ".equals(model.getString("attribute9"))){
					rabbitTemplate.convertAndSend(queue, transReq.toString());
				}else {
					handleNode(transReq);
				}
			}
		}
	}
}