package org.zk.workflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zk.workflow.dao.FlowDao;
import org.zk.workflow.service.FlowService;
import org.zk.workflow.util.Assert;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class FlowServiceImpl implements FlowService {
	
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
}