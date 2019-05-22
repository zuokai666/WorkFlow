package org.zk.workflow.node;

import org.zk.workflow.util.NodeStatus;

public interface Node {
	
	NodeStatus run() throws Exception;
}