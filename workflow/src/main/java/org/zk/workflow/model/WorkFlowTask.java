package org.zk.workflow.model;

/**
 * 工作流任务记录表
 * 
 * @author King
 */
public class WorkFlowTask extends Entity{
	
	/**
	 * 上一个任务记录流水号
	 */
	private int backTask;
	/**
	 * 任务流水号
	 */
	private String taskSerialNo;
	/**
	 * 当前阶段号
	 */
	private int nowPhaseNo;
	/**
	 * 结果(码值:TaskResult)
	 */
	private String result;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 开始时间
	 */
	private String beginTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getBackTask() {
		return backTask;
	}
	public void setBackTask(int backTask) {
		this.backTask = backTask;
	}
	public String getTaskSerialNo() {
		return taskSerialNo;
	}
	public void setTaskSerialNo(String taskSerialNo) {
		this.taskSerialNo = taskSerialNo;
	}
	public int getNowPhaseNo() {
		return nowPhaseNo;
	}
	public void setNowPhaseNo(int nowPhaseNo) {
		this.nowPhaseNo = nowPhaseNo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}