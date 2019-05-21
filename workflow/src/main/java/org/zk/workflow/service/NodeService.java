package org.zk.workflow.service;

import org.zk.workflow.util.NodeStatus;

import com.fasterxml.jackson.databind.JsonNode;

public interface NodeService {
	
	NodeStatus handleCurrentNode(JsonNode currentNode);
	
	NodeStatus initNextNode(JsonNode currentNode);
}