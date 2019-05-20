package org.zk.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.zk.workflow")
public class WorkFlowApp extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WorkFlowApp.class);
	}
	
	public static void main(String[] args) {
		SpringApplication aApp = new SpringApplication(WorkFlowApp.class);
		aApp.run(args);
	}
}