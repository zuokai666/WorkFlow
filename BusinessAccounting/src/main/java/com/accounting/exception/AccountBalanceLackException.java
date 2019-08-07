package com.accounting.exception;

/**
 * 账户余额不足
 * 
 */
public class AccountBalanceLackException extends RuntimeException{
	
	private static final long serialVersionUID = -7699562247870590530L;
	
	public AccountBalanceLackException(String message) {
        super(message);
    }
}