package org.zk.workflow.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zk.workflow.exception.WorkFlowException;
import org.zk.workflow.node.Node;
import org.zk.workflow.service.DataService;
import org.zk.workflow.service.NodeService;
import org.zk.workflow.util.Contant;
import org.zk.workflow.util.NodeStatus;
import org.zk.workflow.util.TimeUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class NodeServiceImpl implements NodeService {
	
	private static final Logger log = LoggerFactory.getLogger(NodeServiceImpl.class);
	
	@Autowired
	private DataService flowService;
	
	@Override
	public NodeStatus handleCurrentNode(JsonNode currentNode) {
		JsonNode taskNode = flowService.getWorkFlowTask(currentNode.path("taskNo").asText());
		if(log.isDebugEnabled()){
			log.debug("当前节点的状态是{}", taskNode.path("phasePinion2").asText());
		}
		if(NodeStatus.SUCCESS.id.equals(taskNode.path("phasePinion2").asText())){//如果当前节点已经成功，直接返回，不重复执行
			if(taskNode.path("phaseNo").asText().equals(NodeStatus.SUCCESS_END.id) 
					|| taskNode.path("phaseNo").asText().equals(NodeStatus.FAIL_END.id)){
				return NodeStatus.TO_END;
			}else {
				return NodeStatus.TO_NEXT;
			}
		}
		JsonNode modelNode = flowService.getWorkFlowModelBy(taskNode.path("flowNo").asText(), taskNode.path("phaseNo").asText());
		if(!modelNode.path("attribute10").asText().isEmpty()){//表示执行节点
			try {
				Node currentNodeClass = (Node) BeanUtils.instantiateClass(Class.forName(modelNode.path("attribute10").asText()));
				NodeStatus executeNodeStatus = currentNodeClass.run();
				flowService.updateWorkFlowTask(TimeUtil.now(), executeNodeStatus.id, 
						executeNodeStatus.describe, currentNode.path("taskNo").asText());
			} catch (ClassNotFoundException e) {
				log.error("执行节点的类的全限定名错误", e);
				throw new WorkFlowException("执行节点的类的全限定名错误");
			} catch (Exception e) {
				log.error("执行节点的类内部出错", e);
				flowService.updateWorkFlowTask(TimeUtil.now(), NodeStatus.EXCEPTION.id, e.getMessage(), 
						currentNode.path("taskNo").asText());
			}
		}else {//没有可执行节点，直接赋值结果成功
			flowService.updateWorkFlowTask(TimeUtil.now(), NodeStatus.NULLNODE.id, NodeStatus.NULLNODE.describe, 
					currentNode.path("taskNo").asText());
		}
		return NodeStatus.SUCCESS;
	}
	
	@Override
	public NodeStatus initNextNode(JsonNode currentNode) {
		JsonNode taskNode = flowService.getWorkFlowTask(currentNode.path("taskNo").asText());
		JsonNode nextModelNode = flowService.getNextWorkFlowModelBy(taskNode.path("phaseNo").asText());
		if(nextModelNode == null){
			//do nothing.
			return NodeStatus.TO_END;
		}else {
			if(nextModelNode.path("phaseNo").asText().equals(NodeStatus.SUCCESS_END.id) 
					|| nextModelNode.path("phaseNo").asText().equals(NodeStatus.FAIL_END.id)){
				ObjectNode dataNode = Contant.om.createObjectNode();
				dataNode.put("objectNo", taskNode.path("objectNo").asText());
				dataNode.put("objectType", taskNode.path("objectType").asText());
				dataNode.put("relativeSerialNo", taskNode.path("serialNo").asText());
				dataNode.put("flowNo", taskNode.path("flowNo").asText());
				dataNode.put("flowName", taskNode.path("flowName").asText());
				dataNode.put("phaseNo", nextModelNode.path("phaseNo").asText());
				dataNode.put("phaseName", nextModelNode.path("phaseName").asText());
				dataNode.put("phaseType", nextModelNode.path("phaseType").asText());
				dataNode.put("beginTime", TimeUtil.now());
				dataNode.put("endTime", TimeUtil.now());
				dataNode.put("phasePinion", NodeStatus.AutoFinish.describe);//处理成功
				dataNode.put("phasePinion2", NodeStatus.AutoFinish.id);//SUCCESS
				dataNode.put("applyType", taskNode.path("applyType").asText());
				flowService.insertWorkFlowTask(dataNode);
				return NodeStatus.TO_END;
			}else {
				ObjectNode dataNode = Contant.om.createObjectNode();
				dataNode.put("objectNo", taskNode.path("objectNo").asText());
				dataNode.put("objectType", taskNode.path("objectType").asText());
				dataNode.put("relativeSerialNo", taskNode.path("serialNo").asText());
				dataNode.put("flowNo", taskNode.path("flowNo").asText());
				dataNode.put("flowName", taskNode.path("flowName").asText());
				dataNode.put("phaseNo", nextModelNode.path("phaseNo").asText());
				dataNode.put("phaseName", nextModelNode.path("phaseName").asText());
				dataNode.put("phaseType", nextModelNode.path("phaseType").asText());
				dataNode.put("beginTime", TimeUtil.now());
//				dataNode.put("endTime", );
//				dataNode.put("phasePinion", );//处理成功
//				dataNode.put("phasePinion2", );//SUCCESS
				dataNode.put("applyType", taskNode.path("applyType").asText());
				flowService.insertWorkFlowTask(dataNode);
				return NodeStatus.TO_NEXT;
			}
		}
	}
}