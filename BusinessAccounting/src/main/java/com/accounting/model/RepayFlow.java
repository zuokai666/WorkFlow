package com.accounting.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 还款流水表
 * 
 * @author King
 */
@Entity
@Table(name="RepayFlow")
public class RepayFlow {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int loanId;//借据编号
	@Column(nullable=false,length=10)
	private String repayMode;//还款类型
	@Column(nullable=false,length=10)
	private String repayDate;//支付日
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidAmount;//实还金额
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidPrincipal;//实还本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidInterest;//实还利息
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public String getRepayMode() {
		return repayMode;
	}
	public void setRepayMode(String repayMode) {
		this.repayMode = repayMode;
	}
	public String getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(String repayDate) {
		this.repayDate = repayDate;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public BigDecimal getPaidPrincipal() {
		return paidPrincipal;
	}
	public void setPaidPrincipal(BigDecimal paidPrincipal) {
		this.paidPrincipal = paidPrincipal;
	}
	public BigDecimal getPaidInterest() {
		return paidInterest;
	}
	public void setPaidInterest(BigDecimal paidInterest) {
		this.paidInterest = paidInterest;
	}
}