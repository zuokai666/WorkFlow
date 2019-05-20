package org.zk.workflow.model;

/**
 * 工作流任务结果
 * 
 * @author King
 *
 */
@SuppressWarnings("unused")
public class WorkFlowObject {
	
	private String objectNo;
	private String objectType;
	private String applyType;//申请类型
	private String flowNo;//当前流程号
	private String flowName;//当前流程名称
	private String phaseNo;//当前阶段号
	private String phaseType;//当前阶段类型
	private String phaseName;//当前阶段名称
	private String update_time;
}