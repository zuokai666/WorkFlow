package org.zk.workflow.model;

/**
 * 工作流任务结果
 * 
 * @author King
 *
 */
public class WorkFlowResult extends Entity{
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 当前阶段号
	 */
	private int phaseNo;
	/**
	 * 开始阶段号
	 */
	private int beginPhaseNo;
	/**
	 * 结束阶段号
	 */
	private int endPhaseNo;
	
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public int getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(int phaseNo) {
		this.phaseNo = phaseNo;
	}
	public int getBeginPhaseNo() {
		return beginPhaseNo;
	}
	public void setBeginPhaseNo(int beginPhaseNo) {
		this.beginPhaseNo = beginPhaseNo;
	}
	public int getEndPhaseNo() {
		return endPhaseNo;
	}
	public void setEndPhaseNo(int endPhaseNo) {
		this.endPhaseNo = endPhaseNo;
	}
}