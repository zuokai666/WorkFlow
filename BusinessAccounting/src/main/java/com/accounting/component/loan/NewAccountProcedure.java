package com.accounting.component.loan;

import java.math.BigDecimal;
import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.model.Account;

public class NewAccountProcedure {
	
	private static final Logger log = LoggerFactory.getLogger(NewAccountProcedure.class);
	
	public void run(Session session,Map<String, Object> map){
		Account account = new Account();
		account.setAmount(new BigDecimal("10000000"));
		account.setBankCardNo("6216605038324580967");
		account.setBankPhone("18041990317");
		account.setIdNumber("130125199502096666");
		account.setName("左凯");
		account.setPassword("P@ssw0rd");
		account.setUsername("zuok_ic");
		session.persist(account);
		map.put("accountId", account.getId());
		log.info("新建账户数据插入成功");
	}
}