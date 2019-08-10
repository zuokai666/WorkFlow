package com.accounting.component.daycut;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.accounting.model.BatchTaskLog;
import com.accounting.util.Constant;
import com.accounting.util.DB;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DistributedEodProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(DistributedEodProcedure.class);
	
	public static final String threadNumK = "eod.distributed.thread.nums";
	public static final int defaultThreadNumV = 2;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbit.queue.eod}")
	private String eodQueue;
	
	public void run(Map<String, Object> map){
		String businessDate = (String) map.get("businessDate");
		int threadNum = (int) map.getOrDefault(threadNumK, defaultThreadNumV);
		Session session = DB.getSession();
		int pId = -1;
		try {
			session.beginTransaction();
			pId = initParentBatchTaskLog(session, businessDate, threadNum);
			initSonBatchTaskLogAndSendMQ(session, businessDate, threadNum, pId);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}finally {
			if(session != null){
				session.close();
			}
		}
		blockWait(pId);
	}
	private void blockWait(int pId) {
		for(int i=0;i<6;i++){
			boolean success = checkParentBatchTaskLogResult(pId);
			if(success){
				return;
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException("日终批处理超时");
	}
	
	private boolean checkParentBatchTaskLogResult(int pId) {
		Session session = DB.getSession();
		try {
			BatchTaskLog parentBatchTaskLog = session.get(BatchTaskLog.class, pId);
			if(parentBatchTaskLog.getMessageNums() == parentBatchTaskLog.getSuccessMessageNums()){
				log.info("日终批次消息处理完毕");
				return true;
			}else {
				log.info("日终批次消息处理中");
				return false;
			}
		}finally {
			if(session != null){
				session.close();
			}
		}
	}
	
	private void sendMQ(int sId) {
		ObjectNode message = new ObjectMapper().createObjectNode();
		message.put("sId", sId);
		rabbitTemplate.convertAndSend(eodQueue, message.toString());
	}
	
	public int initParentBatchTaskLog(Session session, String businessDate, int threadNum){
		BatchTaskLog parentBatchTaskLog = new BatchTaskLog();
		parentBatchTaskLog.setCurrentMessageId(-1);
		parentBatchTaskLog.setpId(-1);
		parentBatchTaskLog.setExecDate(businessDate);
		parentBatchTaskLog.setMessage("我可是父亲，不干活的");
		parentBatchTaskLog.setMessageNums(threadNum);
		parentBatchTaskLog.setSuccessMessageNums(0);
		parentBatchTaskLog.setTaskStatus(null);//状态作废
		session.persist(parentBatchTaskLog);
		return parentBatchTaskLog.getId();
	}
	
	public void initSonBatchTaskLogAndSendMQ(Session session, String businessDate, int threadNum, int pId){
		String sql = "select id from Loan where loanStatus in ('zc','yq') and handleDate < ${businessDate} and MOD(id,${threadNum})=${threadId}";
		for(int threadId=0;threadId<threadNum;threadId++){
			String messageSql = sql
					.replace("${businessDate}", businessDate)
					.replace("${threadNum}", threadNum+"")
					.replace("${threadId}", threadId+"");
			BatchTaskLog sonBatchTaskLog = new BatchTaskLog();
			sonBatchTaskLog.setpId(pId);
			sonBatchTaskLog.setCurrentMessageId(threadId);
			sonBatchTaskLog.setExecDate(businessDate);
			sonBatchTaskLog.setMessage(messageSql);
			sonBatchTaskLog.setMessageNums(threadNum);
			sonBatchTaskLog.setSuccessMessageNums(-1);
			sonBatchTaskLog.setTaskStatus(Constant.batchtasklogstatus_send);
			session.persist(sonBatchTaskLog);
			sendMQ(sonBatchTaskLog.getId());//发送mq
		}
	}
}