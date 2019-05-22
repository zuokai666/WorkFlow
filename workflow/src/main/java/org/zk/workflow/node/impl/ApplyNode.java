package org.zk.workflow.node.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.workflow.node.Node;
import org.zk.workflow.util.NodeStatus;

public class ApplyNode implements Node {
	
	private static final Logger log = LoggerFactory.getLogger(ApplyNode.class);
	
	@Override
	public NodeStatus run() {
		log.info("申请节点调用");
		if("1".equals("1")){
//			return NodeStatus.ERROR;
		}
		return NodeStatus.SUCCESS;
	}
}