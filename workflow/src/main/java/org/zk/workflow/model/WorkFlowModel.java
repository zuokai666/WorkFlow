package org.zk.workflow.model;

/**
 * 记录流程节点
 * 
 * @author King
 */
public class WorkFlowModel extends Entity{
	
	/**
	 * 业务服务器必须传入类型
	 */
	private String type;
	/**
	 * 版本
	 */
	private int version;
	/**
	 * 阶段号
	 */
	private int phaseNo;
	/**
	 * 阶段名称
	 */
	private int phaseName;
	/**
	 * 类的全限定名
	 */
	private String className;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(int phaseNo) {
		this.phaseNo = phaseNo;
	}
	public int getPhaseName() {
		return phaseName;
	}
	public void setPhaseName(int phaseName) {
		this.phaseName = phaseName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
}