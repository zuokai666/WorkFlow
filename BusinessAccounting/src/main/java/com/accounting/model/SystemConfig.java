package com.accounting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SystemConfig")
public class SystemConfig {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=10)
	private String businessDate;//业务日期
	@Column(length=10)
	private String batchFlag;//批量标志
	@Column(length=10)
	private String batchDate;//批量日期
	
	public String getBatchFlag() {
		return batchFlag;
	}
	public void setBatchFlag(String batchFlag) {
		this.batchFlag = batchFlag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public String getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
}