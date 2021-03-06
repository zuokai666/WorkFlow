package org.zk.workflow.dao;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.zk.workflow.util.DbUtil;
import org.zk.workflow.util.TimeUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Repository
public class FlowDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<JsonNode> getWorkFlowTaskBy(String objectNo,String objectType){
		String sql = "select * from WorkFlowTask where objectNo = '"+objectNo+"' and objectType = '"+objectType+"'";
		return DbUtil.getEntities(jdbcTemplate, sql);
	}
	public List<JsonNode> getWorkFlowTaskBy(String relativeSerialNo){
		String sql = "select * from WorkFlowTask where relativeSerialNo = '"+relativeSerialNo+"'";
		return DbUtil.getEntities(jdbcTemplate, sql);
	}
	public List<JsonNode> getWorkFlowTask(String serialNo){
		String sql = "select * from WorkFlowTask where serialNo = '"+serialNo+"'";
		return DbUtil.getEntities(jdbcTemplate, sql);
	}
	@Deprecated
	public List<JsonNode> getWorkFlowModelBy(String flowNo,String phaseNo){
		String sql = "select * from WorkFlowModel where flowNo = '"+flowNo+"' and phaseNo = '"+phaseNo+"'";
		return DbUtil.getEntities(jdbcTemplate, sql);
	}
	public List<JsonNode> getWorkFlowModels(){
		String sql = "select * from WorkFlowModel";
		return DbUtil.getEntities(jdbcTemplate, sql);
	}
	public void updateWorkFlowTask(String endTime,String phasePinion2,String phasePinion,String serialNo){
		jdbcTemplate.update("update WorkFlowTask set endTime='"+endTime+"',phasePinion2='"+phasePinion2
				+"',phasePinion='"+phasePinion+"' where serialNo='"+serialNo+"'");
	}
	public List<JsonNode> getNextWorkFlowModelBy(String currentphaseNo){
		String sql = "SELECT * from WorkFlowModel where phaseNo > '"+currentphaseNo+"' ORDER BY phaseNo LIMIT 0,1;";
		return DbUtil.getEntities(jdbcTemplate, sql);
	}
	public void insertWorkFlowTask(JsonNode node){
		JsonNode vNode = node;
		if(vNode.path("serialNo").asText().isEmpty()){
			((ObjectNode)node).put("serialNo", TimeUtil.key());
		}
		StringBuilder ziduan = new StringBuilder();
		StringBuilder zhi = new StringBuilder();
		Iterator<String> iterator = vNode.fieldNames();
		while(iterator.hasNext()){
			String field = iterator.next();
			String value = vNode.path(field).asText();
			ziduan.append(field);
			ziduan.append(",");
			zhi.append("'");
			zhi.append(value);
			zhi.append("',");
		}
		String _ziduan = ziduan.substring(0, ziduan.length() - 1);//去掉最后一个逗号
		String _zhi = zhi.substring(0, zhi.length() - 1);//去掉最后一个逗号
		jdbcTemplate.update("INSERT INTO WorkFlowTask("+_ziduan+")VALUES("+_zhi+")");
	}
	public int update(String sql){
		return jdbcTemplate.update(sql);
	}
	public JsonNode getWorkFlowObjectBy(String objectNo,String objectType){
		String sql = "select * from WorkFlowObject where objectNo = '"+objectNo+"' and objectType = '"+objectType+"'";
		List<JsonNode> rNodes = DbUtil.getEntities(jdbcTemplate, sql);
		if(rNodes.size() == 0){
			return null;
		}else {
			return rNodes.get(0);
		}
	}
	public void executeWorkFlowObject(JsonNode node){
		JsonNode objectNode = getWorkFlowObjectBy(node.path("objectNo").asText(), node.path("objectType").asText());
		if(objectNode == null){
			insertWorkFlowObject(node);
		}else {
			updateWorkFlowObject(node);
		}
	}
	private void updateWorkFlowObject(JsonNode node){
		JsonNode vNode = node;
		StringBuilder zhi = new StringBuilder();
		Iterator<String> iterator = vNode.fieldNames();
		while(iterator.hasNext()){
			String field = iterator.next();
			String value = vNode.path(field).asText();
			zhi.append(field);
			zhi.append("='");
			zhi.append(value);
			zhi.append("',");
		}
		String _zhi = zhi.substring(0, zhi.length() - 1);//去掉最后一个逗号
		jdbcTemplate.update("update WorkFlowObject set "+_zhi+" where objectNo='"+node.path("objectNo").asText()+"' and objectType='"+node.path("objectType").asText()+"'");
	}
	private void insertWorkFlowObject(JsonNode node){
		JsonNode vNode = node;
		StringBuilder ziduan = new StringBuilder();
		StringBuilder zhi = new StringBuilder();
		Iterator<String> iterator = vNode.fieldNames();
		while(iterator.hasNext()){
			String field = iterator.next();
			String value = vNode.path(field).asText();
			ziduan.append(field);
			ziduan.append(",");
			zhi.append("'");
			zhi.append(value);
			zhi.append("',");
		}
		String _ziduan = ziduan.substring(0, ziduan.length() - 1);//去掉最后一个逗号
		String _zhi = zhi.substring(0, zhi.length() - 1);//去掉最后一个逗号
		jdbcTemplate.update("INSERT INTO WorkFlowObject("+_ziduan+")VALUES("+_zhi+")");
	}
}