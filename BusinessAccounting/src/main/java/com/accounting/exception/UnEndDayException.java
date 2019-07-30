package com.accounting.exception;

/**
 * 没有通过日终批
 */
public class UnEndDayException extends RuntimeException{
	
	private static final long serialVersionUID = -7699562247870590530L;
	
	public UnEndDayException(String message) {
        super(message);
    }
}