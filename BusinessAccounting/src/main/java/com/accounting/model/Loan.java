package com.accounting.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Loan")
public class Loan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false)
	private int term;//贷款期数
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal loanPrincipal;//贷款本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayPrincipal;//应还本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayInterest;//应还利息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal repayAmount;//应还金额-总的意思
	@Column(nullable=false)
	private String repayMethod;//还款方式-暂定等额本息
	@Column(nullable=false,length=10)
	private String loanDate;//放款日期
	@Column(nullable=false,length=10)
	private String startDate;//开始日期
	@Column(nullable=false,length=10)
	private String endDate;//截止日期
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidPrincipal;//已还本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidInterest;//已还利息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidAmount;//已还金额
	@Column(length=10)
	private String finishDate;//结清日期
	@Column(nullable=false,precision=10,scale=2)
	private BigDecimal dayInterestRate;//执行日利率(%)
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public BigDecimal getLoanPrincipal() {
		return loanPrincipal;
	}
	public void setLoanPrincipal(BigDecimal loanPrincipal) {
		this.loanPrincipal = loanPrincipal;
	}
	public String getRepayMethod() {
		return repayMethod;
	}
	public void setRepayMethod(String repayMethod) {
		this.repayMethod = repayMethod;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
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
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	public BigDecimal getDayInterestRate() {
		return dayInterestRate;
	}
	public void setDayInterestRate(BigDecimal dayInterestRate) {
		this.dayInterestRate = dayInterestRate;
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
	public BigDecimal getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}
}