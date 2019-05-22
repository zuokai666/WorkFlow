package org.zk.workflow.node.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.workflow.node.Node;
import org.zk.workflow.util.NodeStatus;

public class FicoNode implements Node {
	
	private static final Logger log = LoggerFactory.getLogger(FicoNode.class);
	
	@Override
	public NodeStatus run() {
		log.info("FICO节点调用");
		return NodeStatus.SUCCESS;
	}
}