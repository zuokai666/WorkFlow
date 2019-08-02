package com.accounting.exception;

/**
 * 结清的借据
 * 
 */
public class LoanClearException extends RuntimeException{
	
	private static final long serialVersionUID = -7699562247870590530L;
	
	public LoanClearException(String message) {
        super(message);
    }
}