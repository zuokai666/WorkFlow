package com.accounting.exception;

/**
 * 不合法的账户
 * 
 */
public class IllegalAccountException extends RuntimeException{
	
	private static final long serialVersionUID = -7699562247870590530L;
	
	public IllegalAccountException(String message) {
        super(message);
    }
}