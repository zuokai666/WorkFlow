package org.zk.workflow.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.zk.workflow.util.DbUtil;

import com.fasterxml.jackson.databind.JsonNode;

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
	public List<JsonNode> getWorkFlowModelBy(String flowNo,String phaseNo){
		String sql = "select * from WorkFlowModel where flowNo = '"+flowNo+"' and phaseNo = '"+phaseNo+"'";
		return DbUtil.getEntities(jdbcTemplate, sql);
	}
}