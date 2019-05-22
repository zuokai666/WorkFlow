package org.zk.workflow.node.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.workflow.node.Node;
import org.zk.workflow.util.NodeStatus;

public class CfcaNode implements Node {
	
	private static final Logger log = LoggerFactory.getLogger(CfcaNode.class);
	
	@Override
	public NodeStatus run() {
		log.info("CFCA签章调用");
		return NodeStatus.SUCCESS;
	}
}