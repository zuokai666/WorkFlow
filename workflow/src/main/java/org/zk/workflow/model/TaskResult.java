package org.zk.workflow.model;

public enum TaskResult {
	
	success("success"),
	fail("fail");
	
    private final String content;
    
    private TaskResult(String content) {
        this.content = content;
    }
    
    public String getContent() {
		return content;
	}
}