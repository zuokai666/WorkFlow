package org.zk.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zk.workflow.dao.FlowDao;
import org.zk.workflow.service.DataService;
import org.zk.workflow.util.Assert;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class DataServiceImpl implements DataService {
	
	@Autowired
	private FlowDao flowDao;
	
	@Override
	public JsonNode getWorkFlowTaskBy(String relativeSerialNo){
		Assert.hasText(relativeSerialNo, "relativeSerialNo不能为空");
		List<JsonNode> rNodes = flowDao.getWorkFlowTaskBy(relativeSerialNo);
		Assert.oneData(rNodes.size());
		return rNodes.get(0);
	}
	
	@Override
	@Deprecated
	public JsonNode getWorkFlowModelBy(String flowNo,String phaseNo){
		Assert.hasText(flowNo, "flowNo不能为空");
		Assert.hasText(phaseNo, "phaseNo不能为空");
		List<JsonNode> rNodes = flowDao.getWorkFlowModelBy(flowNo, phaseNo);
		Assert.oneData(rNodes.size());
		return rNodes.get(0);
	}
	@Override
	public JsonNode getWorkFlowTask(String serialNo){
		Assert.hasText(serialNo, "serialNo不能为空");
		List<JsonNode> rNodes = flowDao.getWorkFlowTask(serialNo);
		Assert.oneData(rNodes.size());
		return rNodes.get(0);
	}

	@Override
	public void updateWorkFlowTask(String endTime, String phasePinion2, String phasePinion, String serialNo) {
		flowDao.updateWorkFlowTask(endTime, phasePinion2, phasePinion, serialNo);
	}
	
	@Override
	public JsonNode getNextWorkFlowModelBy(String currentphaseNo) {
		Assert.hasText(currentphaseNo, "currentphaseNo不能为空");
		List<JsonNode> rNodes = flowDao.getNextWorkFlowModelBy(currentphaseNo);
		if(rNodes.size() == 0){
			return null;
		}else {
			return rNodes.get(0);
		}
	}
	
	@Override
	public void insertWorkFlowTask(JsonNode node) {
		flowDao.insertWorkFlowTask(node);
	}

	@Override
	public int update(String sql) {
		return flowDao.update(sql);
	}
	
	@Override
	public JsonNode getWorkFlowObjectBy(String objectNo, String objectType) {
		return flowDao.getWorkFlowObjectBy(objectNo, objectType);
	}

	@Override
	public void executeWorkFlowObject(JsonNode node) {
		flowDao.executeWorkFlowObject(node);
	}

	@Override
	public List<JsonNode> allWorkFlowModels() {
		return flowDao.getWorkFlowModels();
	}
}