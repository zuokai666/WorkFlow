package com.accounting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BatchTaskLog")
public class BatchTaskLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int pId;//父流水
	private int currentMessageId;//当前消息Id
	private int messageNums;//消息数量
	private int successMessageNums;//成功消息数量
	@Column(length=500)
	private String message;//消息
	@Column(length=10)
	private String taskStatus;//任务状态
	@Column(nullable=false,length=10)
	private String execDate;//执行时间
	
	public int getSuccessMessageNums() {
		return successMessageNums;
	}
	public void setSuccessMessageNums(int successMessageNums) {
		this.successMessageNums = successMessageNums;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public int getCurrentMessageId() {
		return currentMessageId;
	}
	public void setCurrentMessageId(int currentMessageId) {
		this.currentMessageId = currentMessageId;
	}
	public int getMessageNums() {
		return messageNums;
	}
	public void setMessageNums(int messageNums) {
		this.messageNums = messageNums;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getExecDate() {
		return execDate;
	}
	public void setExecDate(String execDate) {
		this.execDate = execDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}