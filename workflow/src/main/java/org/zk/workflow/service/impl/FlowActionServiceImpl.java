package org.zk.workflow.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.zk.workflow.exception.WorkFlowException;
import org.zk.workflow.node.Node;
import org.zk.workflow.service.NetLoanService;
import org.zk.workflow.util.TimeUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service("FlowActionServiceImpl")
public class FlowActionServiceImpl implements NetLoanService {
	
	private static final Logger log = LoggerFactory.getLogger(NetLoanService.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public JsonNode handle(JsonNode requestNode) {
		String taskNo = requestNode.path("taskNo").asText();
		String isFromArtificial = requestNode.path("isFromArtificial").asText();
		String phaseOpinion = requestNode.path("phaseOpinion").asText();
		String phaseAction = requestNode.path("phaseAction").asText();
		String toNext = action(jdbcTemplate, taskNo, isFromArtificial, phaseOpinion, phaseAction);
		ObjectNode returnNode = new ObjectMapper().createObjectNode();
		returnNode.put("FlowResult", toNext);
		return returnNode;
	}
	
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
		try {
			SqlRowSet ft = jdbcTemplate.queryForRowSet("select * from WorkFlowTask where serialNo = '"+taskNo+"'");
			ft.next();
			String currentphaseNo = ft.getString("phaseNo");
			SqlRowSet fm = jdbcTemplate.queryForRowSet("SELECT * from WorkFlowModel where phaseNo > '"+currentphaseNo+"' ORDER BY phaseNo LIMIT 0,1;");
			if(fm.next()){
				String nextphaseNo = fm.getString("phaseNo");
				if(nextphaseNo.equals("1000") || nextphaseNo.equals("8000")){
					jdbcTemplate.execute(
							"INSERT INTO `workflowtask` VALUES ("
							+ "'"+TimeUtil.key()+"', '"+ft.getString("objectNo")+"', '"+ft.getString("objectType")+"', '"+taskNo+"', "
							+ "'"+ft.getString("flowNo")+"', '"+ft.getString("flowName")+"', "
							+ "'"+nextphaseNo+"', '"+fm.getString("phaseName")+"', "
							+ "'"+fm.getString("phaseType")+"', '"+ft.getString("applyType")+"', "
							+ "'"+TimeUtil.now()+"', '"+TimeUtil.now()+"', null, 'AutoFinish', null, null, null, null, '"+TimeUtil.now()+"')");
					return "SUCCESS@处理成功";
				}else {
					jdbcTemplate.execute(
							"INSERT INTO `workflowtask` VALUES ("
							+ "'"+TimeUtil.key()+"', '"+ft.getString("objectNo")+"', '"+ft.getString("objectType")+"', '"+taskNo+"', "
							+ "'"+ft.getString("flowNo")+"', '"+ft.getString("flowName")+"', "
							+ "'"+nextphaseNo+"', '"+fm.getString("phaseName")+"', "
							+ "'"+fm.getString("phaseType")+"', '"+ft.getString("applyType")+"', "
							+ "'"+TimeUtil.now()+"', null, null, null, null, null, null, null, '"+TimeUtil.now()+"')");
					return "TONEXT@处理成功";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "SUCCESS@处理成功";
	}
	
	private static String execute(JdbcTemplate jdbcTemplate,String taskNo,String isFromArtificial,String phaseOpinion) {
		String flag = "SUCCESS@处理成功";
		SqlRowSet ft = jdbcTemplate.queryForRowSet("select * from WorkFlowTask where serialNo = '"+taskNo+"'");
		if(!ft.next() || ft.getString("endTime")!=null || "SUCCESS".equals(ft.getString("phasePinion2"))){
			if(ft.getString("phaseNo").equals("1000") || ft.getString("phaseNo").equals("8000")){
				return "TONEXT@该任务已经结束";
			}
			return "TONEXT@该任务已提交至其他阶段";
		}
		String originalFlowState = ft.getString("phasePinion2");
		SqlRowSet fm = jdbcTemplate.queryForRowSet(
				"select * from WorkFlowModel where flowNo = '"+ft.getString("flowNo")+"' and phaseNo = '"+ft.getString("phaseNo")+"'");
		fm.next();
		String serviceScript = fm.getString("attribute10");
		String serviceScriptMethod = serviceScript;
		if(serviceScriptMethod!=null && !serviceScriptMethod.isEmpty()){
			//检测配置
		}
		if("HOLD".equals(originalFlowState) && !"1".equals(isFromArtificial)){
			jdbcTemplate.update(
					"update WorkFlowTask set phasePinion2='SUCCESS',phasePinion='处理成功' where serialNo='"+taskNo+"'");
		}else if(!"SUCCESS".equals(originalFlowState) && !"FAILCONTINUE".equals(originalFlowState)){
			if(serviceScriptMethod!=null && !serviceScriptMethod.isEmpty()){
				try {
					Class<?> _class = Class.forName(serviceScriptMethod);
					Object newObject = _class.newInstance();
					String anyValue = ((Node) newObject).run();
					String[] sReturn = anyValue.split("@");
					if("SUCCESS".equals(sReturn[0]) || "FAILCONTINUE".equals(sReturn[0])){
						jdbcTemplate.update(
						"update WorkFlowTask set endTime='"+TimeUtil.now()+"',phasePinion2='"+sReturn[0]+"',phasePinion='"+sReturn[1]+"' where serialNo='"+taskNo+"'");
					}else if("HOLDERROR".equals(sReturn[0])){
						throw new WorkFlowException(sReturn[1]);
					}else if("HOLD".equals(sReturn[0])){
						jdbcTemplate.update(
						"update WorkFlowTask set phasePinion2='"+sReturn[0]+"',phasePinion='"+sReturn[1]+"' where serialNo='"+taskNo+"'");
						return "FAIL@" + sReturn[1];
					}else {
						throw new WorkFlowException(serviceScriptMethod + "返回结果有误");
					}
				} catch (Exception e) {
					log.error("", e);
					jdbcTemplate.update(
					"update WorkFlowTask set phasePinion2='HOLDERROR',phasePinion='"+e.getMessage()+"' where serialNo='"+taskNo+"'");
					return "FAIL@" + e.getMessage();
				}
			}else {
				jdbcTemplate.update(
				"update WorkFlowTask set endTime='"+TimeUtil.now()+"',phasePinion2='SUCCESS',phasePinion='空节点处理成功' where serialNo='"+taskNo+"'");
			}
		}
		return flag;
	}
}