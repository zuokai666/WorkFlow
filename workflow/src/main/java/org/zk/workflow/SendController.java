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
import org.zk.workflow.service.DataService;
import org.zk.workflow.util.Contant;
import org.zk.workflow.util.TimeUtil;

import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class SendController {
	
	private static final Logger log = LoggerFactory.getLogger(SendController.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private DataService dataService;
	
	@Value("${rabbit.queue.flow}")
	private String queue;
	
	@RequestMapping(path="/send",method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public String send(){
		log.info("删除WorkFlowTask表，影响行数{}", dataService.update("delete from WorkFlowTask"));
		log.info("删除WorkFlowObject表，影响行数{}", dataService.update("delete from WorkFlowObject"));
		
		String objectNo = "ylyd123456";
		String objectType = "jbo.app.BUSINESS_APPLY";
		
		String serialNo = TimeUtil.key();
		ObjectNode dataNode = Contant.om.createObjectNode();
		dataNode.put("serialNo", serialNo);
		dataNode.put("objectNo", objectNo);
		dataNode.put("objectType", objectType);
		dataNode.put("flowNo", "CreditFlow");
		dataNode.put("flowName", "授信业务流程");
		dataNode.put("phaseNo", "0010");
		dataNode.put("phaseName", "进件申请");
		dataNode.put("phaseType", "1010");
		dataNode.put("beginTime", TimeUtil.now());
		dataNode.put("applyType", "CreditLineApply");
		dataService.insertWorkFlowTask(dataNode);
		
		ObjectNode objectNode = Contant.om.createObjectNode();
		objectNode.put("objectNo", objectNo);
		objectNode.put("objectType", objectType);
		objectNode.put("applyType", "CreditLineApply");
		objectNode.put("flowNo", "CreditFlow");
		objectNode.put("flowName", "授信业务流程");
		objectNode.put("phaseNo", "0010");
		objectNode.put("phaseName", "进件申请");
		objectNode.put("phaseType", "1010");
		dataService.executeWorkFlowObject(objectNode);
		
		ObjectNode taskObject = Contant.om.createObjectNode();
		taskObject.put("taskNo", serialNo);
		taskObject.put("isFromArtificial", "0");
		rabbitTemplate.convertAndSend(queue, taskObject.toString());
		
		return taskObject.toString();
	}
}