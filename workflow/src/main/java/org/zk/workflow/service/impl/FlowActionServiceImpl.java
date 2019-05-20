package org.zk.workflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.zk.workflow.app.flow.FlowServiceScriptExecute;
import org.zk.workflow.service.NetLoanService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service("FlowActionServiceImpl")
public class FlowActionServiceImpl implements NetLoanService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public JsonNode handle(JsonNode requestNode) {
		String taskNo = requestNode.get("taskNo").asText();
		String isFromArtificial = requestNode.get("isFromArtificial").asText();
		String phaseOpinion = requestNode.get("phaseOpinion").asText();
		String phaseAction = requestNode.get("phaseAction").asText();
		String toNext = FlowServiceScriptExecute.action(jdbcTemplate, taskNo, isFromArtificial, phaseOpinion, phaseAction);
		ObjectNode returnNode = new ObjectMapper().createObjectNode();
		returnNode.put("FlowResult", toNext);
		return returnNode;
	}
}