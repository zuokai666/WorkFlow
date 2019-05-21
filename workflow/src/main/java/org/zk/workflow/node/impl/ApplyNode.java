package org.zk.workflow.node.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.workflow.node.Node;

public class ApplyNode implements Node {
	
	private static final Logger log = LoggerFactory.getLogger(ApplyNode.class);
	
	@Override
	public String run() {
		log.info("申请节点调用");
		return "SUCCESS@处理成功";
	}
}