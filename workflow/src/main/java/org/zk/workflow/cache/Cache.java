package org.zk.workflow.cache;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.workflow.service.DataService;

import com.fasterxml.jackson.databind.JsonNode;

public class Cache {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Cache.class);
	
	private static List<JsonNode> workFlowModelCache = new ArrayList<>();
	
	public static void obtainWorkFlowModelCacheFromDb(DataService dataService){
		workFlowModelCache.addAll(dataService.allWorkFlowModels());
	}
	public static void killWorkFlowModelCache(){
		workFlowModelCache = null;
	}
	public static JsonNode getWorkFlowModelBy(String flowNo,String phaseNo){
		for(JsonNode node:workFlowModelCache){
			if(node.path("flowNo").asText().equals(flowNo) && node.path("phaseNo").asText().equals(phaseNo)){
				return node;
			}
		}
		return null;
	}
}