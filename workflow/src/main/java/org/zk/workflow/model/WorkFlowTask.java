package org.zk.workflow.model;

/**
 * 工作流任务记录表
 * 
 * @author King
 */
@SuppressWarnings("unused")
public class WorkFlowTask {
	
	private String serialNo;//流水号
	private String objectNo;//对象编号
	private String objectType;//对象类型
	private String relativeSerialNo;//上一流水号
	private String flowNo;//流程编号
	private String flowName;//流程名称
	private String phaseNo;//阶段编号
	private String phaseName;//阶段名称
	private String phaseType;//阶段类型
	private String applyType;//申请类型
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private String phaseChoice;//阶段意见
	private String phasePinion;//意见详情
	private String phasePinion1;//意见详情1
	private String phasePinion2;//意见详情2
	private String processTaskNo;//流程任务编号
	private String flowState;//流程状态
	private String update_time;//更新时间，数据库自动更新
}