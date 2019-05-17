package org.zk.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkFlowApp {
	
	public static void main(String[] args) {
		SpringApplication aApp = new SpringApplication(WorkFlowApp.class);
		aApp.run(args);
	}
}