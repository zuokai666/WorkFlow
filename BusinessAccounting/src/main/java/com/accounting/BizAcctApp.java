package com.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.accounting.util.DB;

@SpringBootApplication
@ComponentScan("com.accounting")
public class BizAcctApp extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BizAcctApp.class);
	}
	
	public static void main(String[] args) {
		DB.printDate();
		SpringApplication aApp = new SpringApplication(BizAcctApp.class);
		aApp.run(args);
	}
}