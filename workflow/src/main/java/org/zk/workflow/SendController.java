package org.zk.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class SendController {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(SendController.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Value("${rabbit.queue.flow}")
	private String queue;
	
	@RequestMapping(path="/send",method=RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public String send(){
		String applyNo = "ylyd123456";
		String objectType = "jbo.app.BUSINESS_APPLY";
		ObjectNode taskObject = new ObjectMapper().createObjectNode();
		SqlRowSet task = jdbcTemplate.queryForRowSet(
				"select * from WorkFlowTask where objectNo = '"+applyNo+"' and objectType = '"+objectType+"'");
		if(task.next()){
			SqlRowSetMetaData metaData = task.getMetaData();
			for(int i=1;i<=metaData.getColumnCount();i++){
				String columnName = metaData.getColumnName(i);
				String columnValue = task.getString(columnName);
				taskObject.put(columnName, columnValue);
			}
			taskObject.put("taskNo", task.getString("serialNo"));
			taskObject.put("isFromArtificial", "0");
			rabbitTemplate.convertAndSend(queue, taskObject.toString());
			return taskObject.toString();
		}else {
			return "";
		}
	}
}