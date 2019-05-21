package org.zk.workflow.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DbUtil {
	
	/**
	 * 将SqlRowSet转换为List<JsonNode>
	 */
	public static List<JsonNode> getEntities(JdbcTemplate jdbcTemplate,String sql){
		List<JsonNode> jsonNodes = new ArrayList<>();
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
		while(sqlRowSet.next()){
			ObjectNode objectNode = Contant.om.createObjectNode();
			for(int i=1;i<=sqlRowSet.getMetaData().getColumnCount();i++){
				objectNode.put(sqlRowSet.getMetaData().getColumnName(i), sqlRowSet.getString(sqlRowSet.getMetaData().getColumnName(i)));
			}
			jsonNodes.add(objectNode);
		}
		return jsonNodes;
	}
}