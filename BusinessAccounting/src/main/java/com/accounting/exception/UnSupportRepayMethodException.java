package com.accounting.exception;

/**
 * 不支持的还款方式
 */
public class UnSupportRepayMethodException extends RuntimeException{
	
	private static final long serialVersionUID = -7699562247870590530L;
	
	public UnSupportRepayMethodException(String message) {
        super(message);
    }
}