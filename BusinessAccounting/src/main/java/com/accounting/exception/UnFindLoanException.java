package com.accounting.exception;

/**
 * 结清的借据
 * 
 */
public class UnFindLoanException extends RuntimeException{
	
	private static final long serialVersionUID = -7699562247870590530L;
	
	public UnFindLoanException(String message) {
        super(message);
    }
}