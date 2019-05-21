package org.zk.workflow.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface FlowService {
	
	/**
	 * 
	 * @param relativeSerialNo 上一个流水号
	 * @return
	 */
	JsonNode getWorkFlowTaskBy(String relativeSerialNo);
	
	JsonNode getWorkFlowModelBy(String flowNo, String phaseNo);

	JsonNode getWorkFlowTask(String serialNo);
}