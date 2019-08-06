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
	private int accountId;//账户
	@Column(nullable=false)
	private int originalTerm;//原始贷款总期数
	@Column(nullable=false)
	private int term;//贷款总期数
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal loanPrincipal;//贷款本金
	@Column(nullable=false,length=10)
	private String repayMethod;//还款方式
	@Column(nullable=false,length=10)
	private String loanDate;//放款日期
	@Column(nullable=false,length=10)
	private String originalEndDate;//原始截止日期
	@Column(nullable=false,length=10)
	private String endDate;//截止日期
	private int overdueDays;//逾期天数
	private int maxOverdueDays;//最高逾期天数
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidPrincipal;//已还本金
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidInterest;//已还利息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidPrincipalPenalty;//已还本金罚息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidInterestPenalty;//已还利息罚息
	@Column(nullable=false,precision=24,scale=2)
	private BigDecimal paidAmount;//已还金额
	@Column(length=10)
	private String finishDate;//结清日期
	@Column(nullable=false,precision=10,scale=2)
	private BigDecimal dayInterestRate;//执行日利率(%)
	@Column(nullable=false,length=10)
	private String handleDate;//业务处理日期
	@Column(nullable=false,length=10)
	private String loanStatus;//借据状态
	
	public int getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(int overdueDays) {
		this.overdueDays = overdueDays;
	}
	public int getMaxOverdueDays() {
		return maxOverdueDays;
	}
	public void setMaxOverdueDays(int maxOverdueDays) {
		this.maxOverdueDays = maxOverdueDays;
	}
	public BigDecimal getPaidPrincipalPenalty() {
		return paidPrincipalPenalty;
	}
	public void setPaidPrincipalPenalty(BigDecimal paidPrincipalPenalty) {
		this.paidPrincipalPenalty = paidPrincipalPenalty;
	}
	public BigDecimal getPaidInterestPenalty() {
		return paidInterestPenalty;
	}
	public void setPaidInterestPenalty(BigDecimal paidInterestPenalty) {
		this.paidInterestPenalty = paidInterestPenalty;
	}
	public int getOriginalTerm() {
		return originalTerm;
	}
	public void setOriginalTerm(int originalTerm) {
		this.originalTerm = originalTerm;
	}
	public String getOriginalEndDate() {
		return originalEndDate;
	}
	public void setOriginalEndDate(String originalEndDate) {
		this.originalEndDate = originalEndDate;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getHandleDate() {
		return handleDate;
	}
	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
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
}