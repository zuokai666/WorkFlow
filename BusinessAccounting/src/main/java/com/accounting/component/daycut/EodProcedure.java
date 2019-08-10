package com.accounting.component.daycut;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EodProcedure {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(EodProcedure.class);
	
	public void run(Map<String, Object> map){
//		new MultiThreadEodProcedure().run(map);
		new DistributedEodProcedure().run(map);
	}
}