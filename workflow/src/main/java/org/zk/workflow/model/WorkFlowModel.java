package org.zk.workflow.model;

public class WorkFlowModel {
	
	private String flowNo;//流程编号
	private String version;
	private String phaseNo;
	private String phaseType;
	private String phaseName;
	private String phaseDescribe;
	private String phaseAttribute;
	private String preScript;
	private String initScript;
	private String choiceDescribe;
	private String choiceScript;
	private String actionDescribe;
	private String actionScript;
	private String postScript;
	private String attribute1;
	private String attribute2;
	private String attribute3;
	private String attribute4;
	private String attribute5;
	private String attribute6;
	private String attribute7;
	private String attribute8;
	private String attribute9;//是否是MQ，是处理完一个节点就提交到MQ服务器中，否则，死循环接着处理下一个节点逻辑
	private String attribute10;
	private String standardTime1;//审批标准时间1（标准）
	private String standardTime2;//审批标准时间2（最长）
	private String update_time;
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public String getPhaseType() {
		return phaseType;
	}
	public void setPhaseType(String phaseType) {
		this.phaseType = phaseType;
	}
	public String getPhaseName() {
		return phaseName;
	}
	public void setPhaseName(String phaseName) {
		this.phaseName = phaseName;
	}
	public String getPhaseDescribe() {
		return phaseDescribe;
	}
	public void setPhaseDescribe(String phaseDescribe) {
		this.phaseDescribe = phaseDescribe;
	}
	public String getPhaseAttribute() {
		return phaseAttribute;
	}
	public void setPhaseAttribute(String phaseAttribute) {
		this.phaseAttribute = phaseAttribute;
	}
	public String getPreScript() {
		return preScript;
	}
	public void setPreScript(String preScript) {
		this.preScript = preScript;
	}
	public String getInitScript() {
		return initScript;
	}
	public void setInitScript(String initScript) {
		this.initScript = initScript;
	}
	public String getChoiceDescribe() {
		return choiceDescribe;
	}
	public void setChoiceDescribe(String choiceDescribe) {
		this.choiceDescribe = choiceDescribe;
	}
	public String getChoiceScript() {
		return choiceScript;
	}
	public void setChoiceScript(String choiceScript) {
		this.choiceScript = choiceScript;
	}
	public String getActionDescribe() {
		return actionDescribe;
	}
	public void setActionDescribe(String actionDescribe) {
		this.actionDescribe = actionDescribe;
	}
	public String getActionScript() {
		return actionScript;
	}
	public void setActionScript(String actionScript) {
		this.actionScript = actionScript;
	}
	public String getPostScript() {
		return postScript;
	}
	public void setPostScript(String postScript) {
		this.postScript = postScript;
	}
	public String getAttribute1() {
		return attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	public String getAttribute3() {
		return attribute3;
	}
	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}
	public String getAttribute4() {
		return attribute4;
	}
	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}
	public String getAttribute5() {
		return attribute5;
	}
	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}
	public String getAttribute6() {
		return attribute6;
	}
	public void setAttribute6(String attribute6) {
		this.attribute6 = attribute6;
	}
	public String getAttribute7() {
		return attribute7;
	}
	public void setAttribute7(String attribute7) {
		this.attribute7 = attribute7;
	}
	public String getAttribute8() {
		return attribute8;
	}
	public void setAttribute8(String attribute8) {
		this.attribute8 = attribute8;
	}
	public String getAttribute9() {
		return attribute9;
	}
	public void setAttribute9(String attribute9) {
		this.attribute9 = attribute9;
	}
	public String getAttribute10() {
		return attribute10;
	}
	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}
	public String getStandardTime1() {
		return standardTime1;
	}
	public void setStandardTime1(String standardTime1) {
		this.standardTime1 = standardTime1;
	}
	public String getStandardTime2() {
		return standardTime2;
	}
	public void setStandardTime2(String standardTime2) {
		this.standardTime2 = standardTime2;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
}