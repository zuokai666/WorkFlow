package com.accounting.util;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.SystemConfig;

public class Db {
	
	private static final Logger log = LoggerFactory.getLogger(Db.class);
	
	public static void printDate(){
		Session session = getSession();
		String bizDate = getBusinessDate(session);
		String batchDate = getBatchDate(session);
		log.info("当前业务日期:[{}],批量日期:[{}]", bizDate, batchDate);
		session.close();
	}
	
	private static SessionFactory sessionFactory = null;
	
	static{
		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}
	
	public static Session getSession(){
		return sessionFactory.openSession();
	}
	
	public static String getBusinessDate(Session session){
		SystemConfig config = session.get(SystemConfig.class, 1, LockMode.PESSIMISTIC_READ);
		return config.getBusinessDate();
	}
	
	public static SystemConfig getSystemConfig(Session session){
		SystemConfig config = session.get(SystemConfig.class, 1, LockMode.PESSIMISTIC_WRITE);
		return config;
	}
	
	public static String getBatchDate(Session session){
		SystemConfig config = session.get(SystemConfig.class, 1, LockMode.PESSIMISTIC_READ);
		return config.getBatchDate();
	}
}