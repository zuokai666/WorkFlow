package com.accounting.exception;

/**
 * 错误的还款方式
 * 
 */
public class MistakeReapyModeException extends RuntimeException{
	
	private static final long serialVersionUID = -7699562247870590530L;
	
	public MistakeReapyModeException(String message) {
        super(message);
    }
}