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
}