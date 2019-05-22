package org.zk.workflow.cache;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.zk.workflow.service.DataService;

@Configuration
@Order(8)
public class CacheStartupRunner implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(CacheStartupRunner.class);
	
	@Autowired
	private DataService dataService;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("服务Cache启动......");
		Cache.obtainWorkFlowModelCacheFromDb(dataService);
	}
	
	@PreDestroy
	public void destroy(){
		log.info("服务Cache销毁......");
		Cache.killWorkFlowModelCache();
	}
}