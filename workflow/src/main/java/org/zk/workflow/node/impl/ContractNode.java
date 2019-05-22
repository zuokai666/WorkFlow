package org.zk.workflow.node.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zk.workflow.node.Node;
import org.zk.workflow.util.NodeStatus;

public class ContractNode implements Node {
	
	private static final Logger log = LoggerFactory.getLogger(ContractNode.class);
	
	@Override
	public NodeStatus run() {
		log.info("合同生成节点调用");
		if("1".equals("1")){
//			return NodeStatus.ERROR;
		}
		return NodeStatus.SUCCESS;
	}
}