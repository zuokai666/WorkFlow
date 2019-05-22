package org.zk.workflow.service;

import org.zk.workflow.util.NodeStatus;

import com.fasterxml.jackson.databind.JsonNode;

public interface NodeService {
	
	/**
	 * 处理当前节点逻辑
	 * 
	 * @param currentNode
	 * @return
	 * 
	 * 没有可以执行的节点逻辑，保存NULLNODE，返回SUCCESS<br/>
	 * 执行节点的类内部抛出异常，保存EXCEPTION，返回FAIL<br/>
	 * 正常节点返回：<br/>
	 * 		1.执行成功，返回SUCCESS，保存SUCCESS，返回SUCCESS<br/>
	 * 		2.执行失败，返回ERROR_HOLD，保存ERROR_HOLD，返回FAIL<br/>
	 * 		3.执行失败，返回ERROR_CONTINUE，保存ERROR_CONTINUE，返回SUCCESS<br/>
	 * 
	 */
	NodeStatus handleCurrentNode(JsonNode currentNode);
	
	NodeStatus initNextNode(JsonNode currentNode);
}