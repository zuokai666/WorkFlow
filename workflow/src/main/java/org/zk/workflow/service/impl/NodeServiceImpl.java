package org.zk.workflow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.zk.workflow.exception.WorkFlowException;
import org.zk.workflow.node.Node;
import org.zk.workflow.service.FlowService;
import org.zk.workflow.service.NodeService;
import org.zk.workflow.util.NodeStatus;
import org.zk.workflow.util.TimeUtil;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class NodeServiceImpl implements NodeService {
	
	@Autowired
	private FlowService flowService;
	
	@Override
	public NodeStatus handleCurrentNode(JsonNode currentNode) {
		JsonNode taskNode = flowService.getWorkFlowTask(currentNode.path("taskNo").asText());
		
		
		
		
		
		
		return null;
	}
	
	@Override
	public NodeStatus initNextNode(JsonNode currentNode) {
		return null;
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