package org.zk.workflow.util;

/**
 * 节点状态
 * 
 * @author King
 *
 */
public enum NodeStatus {
	
	FAIL_CONTINUE("FAIL_CONTINUE", "执行失败，继续执行"),
	ERROR("ERROR", "处理错误"),//有待恢复解决
	EXCEPTION("EXCEPTION", "节点处理异常"),//节点抛出异常
	
	SUCCESS("SUCCESS", "处理成功"),//第一次执行，允许创建下一个节点
	NULLNODE("SUCCESS", "空节点，处理成功"),
	FAIL("FAIL", "处理失败"),
	TO_NEXT("TO_NEXT", "流向下一个节点"),
	TO_END("TO_END", "流程任务结束"),//多次执行，流程结束
	
	SUCCESS_END("1000", "成功结束"),
	FAIL_END("8000", "失败结束"),
	
	AutoFinish("AutoFinish", "自动结束"),
	;
	
	public String id;
	public String describe;
	
	private NodeStatus(String id,String describe){
		this.id = id;
		this.describe = describe;
	}
}