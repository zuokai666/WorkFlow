package org.zk.workflow.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface NetLoanService {
	
	JsonNode handle(JsonNode requestNode);
}