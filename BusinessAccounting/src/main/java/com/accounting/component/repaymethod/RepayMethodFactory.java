package com.accounting.component.repaymethod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.accounting.util.Constant;

public class RepayMethodFactory {

	private Map<String, RepayMethod> map = new ConcurrentHashMap<>();

	public void add(String name, RepayMethod repayMethod) {
		map.put(name, repayMethod);
	}

	public RepayMethod getRepayMethod(String name) {
		return map.get(name);
	}

	private static final RepayMethodFactory instance = new RepayMethodFactory();

	private RepayMethodFactory() {
		map.put(Constant.repaymethod_debx, new DEBXRepayMethod());
		map.put(Constant.repaymethod_debj, new DEBJRepayMethod());
		map.put(Constant.repaymethod_xxhb, new XXHBRepayMethod());
		map.put(Constant.repaymethod_one, new ONERepayMethod());
	}
	
	public static RepayMethodFactory getInstance() {
		return instance;
	}
}