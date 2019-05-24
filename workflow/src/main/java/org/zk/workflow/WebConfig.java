package org.zk.workflow;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zk.workflow.interceptor.CountHandlerInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CountHandlerInterceptor());
	}
}