package org.zk.workflow.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zk.workflow.cache.Cache;
import org.zk.workflow.exception.NotSupportedNodeStatusException;
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
	private DataService dataService;
	
	@Override
	public NodeStatus handleCurrentNode(JsonNode currentNode) {
		JsonNode taskNode = dataService.getWorkFlowTask(currentNode.path("taskNo").asText());
		if(NodeStatus.SUCCESS.id.equals(taskNode.path("phasePinion2").asText())){//如果当前节点已经成功，直接返回，不重复执行
			if(taskNode.path("phaseNo").asText().equals(NodeStatus.SUCCESS_END.id) 
					|| taskNode.path("phaseNo").asText().equals(NodeStatus.FAIL_END.id)){
				return NodeStatus.TO_END;
			}else {
				return NodeStatus.TO_NEXT;
			}
		}
		JsonNode modelNode = Cache.getWorkFlowModelBy(taskNode.path("flowNo").asText(), taskNode.path("phaseNo").asText());
		NodeStatus result = NodeStatus.SUCCESS;
		if(!modelNode.path("attribute10").asText().isEmpty()){//表示执行节点
			try {
				Node currentNodeClass = (Node) BeanUtils.instantiateClass(Class.forName(modelNode.path("attribute10").asText()));
				NodeStatus executeNodeStatus = currentNodeClass.run();
				dataService.updateWorkFlowTask(TimeUtil.now(), executeNodeStatus.id, 
						executeNodeStatus.describe, currentNode.path("taskNo").asText());
				if(executeNodeStatus == NodeStatus.SUCCESS){
					result = NodeStatus.SUCCESS;
				}else if(executeNodeStatus == NodeStatus.ERROR_HOLD){
					result = NodeStatus.FAIL;
				}else if(executeNodeStatus == NodeStatus.ERROR_CONTINUE){
					result = NodeStatus.SUCCESS;
				}else {
					throw new NotSupportedNodeStatusException("不支持的节点返回类型");
				}
			} catch (ClassNotFoundException e) {
				log.error("执行节点的类的全限定名错误", e);
				throw new WorkFlowException("执行节点的类的全限定名错误");
			} catch (WorkFlowException e) {
				throw new WorkFlowException(e.getMessage());
			} catch (Exception e) {
				log.error("执行节点的类内部出错", e);
				dataService.updateWorkFlowTask(TimeUtil.now(), NodeStatus.EXCEPTION.id, e.getMessage(), 
						currentNode.path("taskNo").asText());
				result = NodeStatus.FAIL;
			}
		}else {//没有可执行节点，直接赋值结果成功
			dataService.updateWorkFlowTask(TimeUtil.now(), NodeStatus.NULLNODE.id, NodeStatus.NULLNODE.describe, 
					currentNode.path("taskNo").asText());
		}
		setWorkFlowObjectResult(taskNode);
		return result;
	}
	
	@Override
	public NodeStatus initNextNode(JsonNode currentNode) {
		JsonNode taskNode = dataService.getWorkFlowTask(currentNode.path("taskNo").asText());
		JsonNode nextModelNode = dataService.getNextWorkFlowModelBy(taskNode.path("phaseNo").asText());
		if(nextModelNode == null){
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
				dataNode.put("phasePinion", NodeStatus.SUCCESS.describe);//处理成功
				dataNode.put("phasePinion2", NodeStatus.SUCCESS.id);//SUCCESS
				dataNode.put("applyType", taskNode.path("applyType").asText());
				dataService.insertWorkFlowTask(dataNode);
				setWorkFlowObjectResult(dataNode);
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
				dataService.insertWorkFlowTask(dataNode);
				return NodeStatus.TO_NEXT;
			}
		}
	}
	
	private void setWorkFlowObjectResult(JsonNode taskNode){
		ObjectNode objectNode = Contant.om.createObjectNode();
		objectNode.put("objectNo", taskNode.path("objectNo").asText());
		objectNode.put("objectType", taskNode.path("objectType").asText());
		objectNode.put("applyType", taskNode.path("applyType").asText());
		objectNode.put("flowNo", taskNode.path("flowNo").asText());
		objectNode.put("flowName", taskNode.path("flowName").asText());
		objectNode.put("phaseNo", taskNode.path("phaseNo").asText());
		objectNode.put("phaseName", taskNode.path("phaseName").asText());
		objectNode.put("phaseType", taskNode.path("phaseType").asText());
		dataService.executeWorkFlowObject(objectNode);
	}
}