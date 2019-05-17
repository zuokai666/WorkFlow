package org.zk.workflow.model;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * 所有任务的父类
 * 
 * @author King
 *
 */
public abstract class Task {
	
	public TaskResult run(JsonNode contentNode) throws Exception{
		return TaskResult.fail;
	}
}