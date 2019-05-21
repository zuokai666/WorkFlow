package org.zk.workflow.util;

/**
 * 节点状态
 * 
 * @author King
 *
 */
public enum NodeStatus {
	
	SUCCESS("SUCCESS", "处理成功"),
	FAIL("FAIL", "处理失败"),
	;
	
	public String flag;
	public String describe;
	
	private NodeStatus(String flag,String describe){
		this.flag = flag;
		this.describe = describe;
	}
}