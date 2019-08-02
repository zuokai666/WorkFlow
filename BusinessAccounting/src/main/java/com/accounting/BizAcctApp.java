package com.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.accounting.service.ScheduleServiceImpl;
import com.accounting.util.DB;

@EnableScheduling
@SpringBootApplication
@ComponentScan("com.accounting")
public class BizAcctApp extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BizAcctApp.class);
	}
	
	@Scheduled(cron ="0 0 * * * ?")
	public void eod(){
		ScheduleServiceImpl scheduleService = new ScheduleServiceImpl();
		scheduleService.dayCut();
		scheduleService.batchCharge();
	}
	
	public static void main(String[] args) {
		DB.printDate();
		SpringApplication aApp = new SpringApplication(BizAcctApp.class);
		aApp.run(args);
	}
}