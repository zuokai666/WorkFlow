package com.accounting.exception;

/**
 * 不合法的优惠券
 * 
 */
public class IllegalCouponException extends RuntimeException{
	
	private static final long serialVersionUID = -7699562247870590530L;
	
	public IllegalCouponException(String message) {
        super(message);
    }
}