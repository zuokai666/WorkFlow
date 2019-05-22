package org.zk.workflow.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface DataService {
	
	/**
	 * 
	 * @param relativeSerialNo 上一个流水号
	 * @return
	 */
	JsonNode getWorkFlowTaskBy(String relativeSerialNo);
	
	/**
	 * 废弃，更换为Cache中获取
	 * @param flowNo
	 * @param phaseNo
	 * @return
	 */
	@Deprecated
	JsonNode getWorkFlowModelBy(String flowNo, String phaseNo);
	List<JsonNode> allWorkFlowModels();
	
	JsonNode getWorkFlowObjectBy(String objectNo,String objectType);
	void executeWorkFlowObject(JsonNode node);
	
	JsonNode getWorkFlowTask(String serialNo);
	
	void updateWorkFlowTask(String endTime,String phasePinion2,String phasePinion,String serialNo);
	
	JsonNode getNextWorkFlowModelBy(String currentphaseNo);
	
	void insertWorkFlowTask(JsonNode node);
	
	int update(String sql);
}