package org.zk.workflow.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface DataService {
	
	/**
	 * 
	 * @param relativeSerialNo 上一个流水号
	 * @return
	 */
	JsonNode getWorkFlowTaskBy(String relativeSerialNo);
	
	JsonNode getWorkFlowModelBy(String flowNo, String phaseNo);
	
	JsonNode getWorkFlowObjectBy(String objectNo,String objectType);
	void executeWorkFlowObject(JsonNode node);
	
	JsonNode getWorkFlowTask(String serialNo);
	
	void updateWorkFlowTask(String endTime,String phasePinion2,String phasePinion,String serialNo);
	
	JsonNode getNextWorkFlowModelBy(String currentphaseNo);
	
	void insertWorkFlowTask(JsonNode node);
	
	int update(String sql);
}