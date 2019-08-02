package com.accounting.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.accounting.component.repaymethod.RepaySchedule;

/**
 * 原始还款计划表
 * 
 * @author King
 */
@Entity
@Table(name="OriginalRepayPlan")
public class OriginalRepayPlan implements RepaySchedule{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int currentTerm;//当前期数
	private int loanId;//借据编号
	@Column(length=10)
	private String startDate;//区间开始日期
	@Column(length=10)
	private String endDate;//区间截止日期
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayPrincipal;//应还本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayInterest;//应还利息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal interestPrincipal;//计息本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayAmount;//应还金额-总的意思

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCurrentTerm() {
		return currentTerm;
	}
	public void setCurrentTerm(int currentTerm) {
		this.currentTerm = currentTerm;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public BigDecimal getRepayPrincipal() {
		return repayPrincipal;
	}
	public void setRepayPrincipal(BigDecimal repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}
	public BigDecimal getRepayInterest() {
		return repayInterest;
	}
	public void setRepayInterest(BigDecimal repayInterest) {
		this.repayInterest = repayInterest;
	}
	public BigDecimal getInterestPrincipal() {
		return interestPrincipal;
	}
	public void setInterestPrincipal(BigDecimal interestPrincipal) {
		this.interestPrincipal = interestPrincipal;
	}
	public BigDecimal getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}
}