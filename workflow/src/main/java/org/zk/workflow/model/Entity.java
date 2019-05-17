package org.zk.workflow.model;

public abstract class Entity {
	
	private int id;
	//格式：yyyy-MM-dd HH:mm:ss 24小时制
	private String updateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}