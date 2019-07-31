package com.accounting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BillStatistics")
public class BillStatistics {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false, length=10)
	private String totalLoanAmount;//贷款总额
	@Column(nullable=false, length=18)
	private String totalRepayAmount;//还款总额
	@Column(nullable=false, length=10)
	private String lastDayLoanAmount;//昨日贷款总额
	@Column(nullable=false, length=18)
	private String lastDayRepayAmount;//昨日还款总额
	@Column(nullable=false,length=10)
	private String dataDate;//数据日期
}