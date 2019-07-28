package com.accounting.exception;

/**
 * 未结清的借据
 * 
 */
public class UnClearedLoanException extends RuntimeException{
	
	private static final long serialVersionUID = -7699562247870590530L;
	
	public UnClearedLoanException(String message) {
        super(message);
    }
}