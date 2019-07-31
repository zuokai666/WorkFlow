package com.BigDecimal.test;

import java.math.BigDecimal;

public class BigDecimalTest {

	public static void main(String[] args) {
		BigDecimal a = new BigDecimal("1.1111");//传入double应该传入字符串
		a = BigDecimal.valueOf(1.1111);
		BigDecimal b = new BigDecimal("1.1111");
		System.err.println(a);
		System.err.println(a.add(b));//添加是返回一个新值
		System.err.println(a.add(b) == a.add(b));
		System.err.println(a);
	}
}