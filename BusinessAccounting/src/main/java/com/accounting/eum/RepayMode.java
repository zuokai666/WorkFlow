package com.accounting.eum;

public enum RepayMode {
	
	ZH(1, "正常还款");
	
	public final int value;
	public final String zh;
	private RepayMode(int value,String zh){
		this.value = value;
		this.zh = zh;
	}
}