package org.zk.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Deprecated
public class SendController {
	
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
		log.info("{}准备发送消息,{}", Thread.currentThread().getName());
		rabbitTemplate.convertAndSend(queue, "zuokai");
		try {
			SqlRowSet workFlowModelSet = jdbcTemplate.queryForRowSet(
					"select * from WorkFlowModel where flowNo = 'CreditFlow' and phaseNo = '0010'");
			if(workFlowModelSet.getRow()+1 == 1){
				workFlowModelSet.next();
				String phaseName = workFlowModelSet.getString("phaseName");
				log.info("phaseName = {}", phaseName);
			}else {
				throw new IncorrectResultSizeDataAccessException(1,workFlowModelSet.getRow()+1);
			}
		} catch (DataAccessException e) {
			log.error("", e);
		}
		return "{\"result\":\"1\"}";
	}
}