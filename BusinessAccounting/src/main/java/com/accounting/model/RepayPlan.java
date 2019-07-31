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
 * 还款计划表
 * 
 * @author King
 */
@Entity
@Table(name="RepayPlan")
public class RepayPlan implements RepaySchedule{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int currentTerm;//当前期数
	private int loanId;//借据编号
	@Column(length=10)
	private String startDate;//区间开始日期
	@Column(length=10)
	private String endDate;//区间截止日期
//	private String graceDate;//宽限日
	@Column(length=10)
	private String payDate;//应付日
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayPrincipal;//应还本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayInterest;//应还利息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal interestPrincipal;//计息本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayAmount;//应还金额-总的意思
//	private int planStatus;//期供状态
//	private BigDecimal accumulativeInterest;//累计利息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal accrueInterest;//计提利息
//	@Column(nullable=false,precision=24,scale=2)
//	private BigDecimal overdueInterest;//逾期利息
	@Column(length=10)
	private String finishDate;//当期结清日期
	@Column(length=255)
	private String remark;//备注
	
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
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
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
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
	public BigDecimal getAccrueInterest() {
		return accrueInterest;
	}
	public void setAccrueInterest(BigDecimal accrueInterest) {
		this.accrueInterest = accrueInterest;
	}
}