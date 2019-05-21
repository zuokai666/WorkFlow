package org.zk.workflow.node.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.workflow.node.Node;

public class FicoNode implements Node {
	
	private static final Logger log = LoggerFactory.getLogger(FicoNode.class);
	
	@Override
	public String run() {
		log.info("FICO节点调用");
//		return "HOLDERROR@处理失败";
		return "SUCCESS@处理成功";
	}
}