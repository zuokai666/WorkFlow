package org.zk.workflow.app.flow;

import org.springframework.jdbc.core.JdbcTemplate;

public class FlowServiceScriptExecute {
	
	/**
	 * 
	 * @return
	 * 
	 * FAIL@XXX
	 * SUCCESS@XXX
	 * TONEXT@XXX
	 * 
	 */
	public static String action(JdbcTemplate jdbcTemplate,String taskNo,
			String isFromArtificial,String phaseOpinion,String phaseAction){
		String flag = execute(jdbcTemplate,taskNo,isFromArtificial,phaseOpinion);
		if("SUCCESS".equals(flag.split("@")[0])){
			return commit(jdbcTemplate,taskNo,phaseOpinion,phaseAction);
		}else {
			return flag;
		}
	}
	
	private static String commit(JdbcTemplate jdbcTemplate, String taskNo, String phaseOpinion, String phaseAction) {
		return null;
	}
	
	private static String execute(JdbcTemplate jdbcTemplate,String taskNo,String isFromArtificial,String phaseOpinion) {
		return null;
	}
}