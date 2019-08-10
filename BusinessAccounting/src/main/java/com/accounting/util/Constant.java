package com.accounting.util;

public class Constant {
	
	public static final String dateFormat = "yyyy-MM-dd";//日期格式
	
	public static final String batchflag_eod = "end";//日终
	public static final String batchflag_day = "day";//日间
	
	public static final String loanstatus_zc = "zc";//正常
	public static final String loanstatus_yq = "yq";//逾期
	public static final String loanstatus_zcjq = "zcjq";//正常结清
	public static final String loanstatus_tqjq = "tqjq";//提前结清
	public static final String loanstatus_yqjq = "yqjq";//逾期结清
	
	public static final String repaymethod_debx = "debx";//等额本息
	public static final String repaymethod_debj = "debj";//等额本金
	public static final String repaymethod_xxhb = "xxhb";//先息后本
	public static final String repaymethod_one = "one";//一次性还本付息
	
	public static final String repaymode_dqhk = "dqhk";//到期还款
	public static final String repaymode_yqhk = "yqhk";//逾期还款
	public static final String repaymode_tqjqCur = "tqjqCur";//提前结清当期
	public static final String repaymode_tqjqAll = "tqjqAll";//提前结清全部
	
	public static final String couponuse_unuse = "unuse";//未使用
	public static final String couponuse_used = "used";//已使用
	
	public static final String accounttype_person = "person";//个人账户
	public static final String accounttype_company = "company";//公司账户
	
	public static final String batchtasklogstatus_send = "send";//发送中
	public static final String batchtasklogstatus_process = "process";//处理中
	public static final String batchtasklogstatus_success = "success";//成功
	public static final String batchtasklogstatus_fail = "fail";//失败
}