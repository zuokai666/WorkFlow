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
	
	//-------------------本金-------------------------
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayPrincipal;//应还本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal waivePrincipal;//减免本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal interestPrincipal;//计息本金
	
	//-------------------利息--------------------------
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayInterest;//应还利息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal waiveInterest;//减免利息
	
	//-------------------本金罚息--------------------------
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayPrincipalPenalty;//应还本金罚息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal waivePrincipalPenalty;//减免本金罚息
	
	//-------------------利息罚息--------------------------
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayInterestPenalty;//应还利息罚息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal waiveInterestPenalty;//减免利息罚息
	
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal accrueInterest;//计提利息
	@Column(length=10)
	private String finishDate;//当期结清日期
	@Column(length=255)
	private String remark;//备注
	
	public BigDecimal getWaivePrincipal() {
		return waivePrincipal;
	}
	public void setWaivePrincipal(BigDecimal waivePrincipal) {
		this.waivePrincipal = waivePrincipal;
	}
	public BigDecimal getWaiveInterest() {
		return waiveInterest;
	}
	public void setWaiveInterest(BigDecimal waiveInterest) {
		this.waiveInterest = waiveInterest;
	}
	public BigDecimal getRepayPrincipalPenalty() {
		return repayPrincipalPenalty;
	}
	public void setRepayPrincipalPenalty(BigDecimal repayPrincipalPenalty) {
		this.repayPrincipalPenalty = repayPrincipalPenalty;
	}
	public BigDecimal getWaivePrincipalPenalty() {
		return waivePrincipalPenalty;
	}
	public void setWaivePrincipalPenalty(BigDecimal waivePrincipalPenalty) {
		this.waivePrincipalPenalty = waivePrincipalPenalty;
	}
	public BigDecimal getRepayInterestPenalty() {
		return repayInterestPenalty;
	}
	public void setRepayInterestPenalty(BigDecimal repayInterestPenalty) {
		this.repayInterestPenalty = repayInterestPenalty;
	}
	public BigDecimal getWaiveInterestPenalty() {
		return waiveInterestPenalty;
	}
	public void setWaiveInterestPenalty(BigDecimal waiveInterestPenalty) {
		this.waiveInterestPenalty = waiveInterestPenalty;
	}
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
	public BigDecimal getAccrueInterest() {
		return accrueInterest;
	}
	public void setAccrueInterest(BigDecimal accrueInterest) {
		this.accrueInterest = accrueInterest;
	}
	@Override
	public void setRepayAmount(BigDecimal repayAmount) {
		//不做设置
	}
}