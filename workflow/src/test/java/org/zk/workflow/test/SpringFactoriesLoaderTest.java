package org.zk.workflow.test;

import java.util.List;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;

public class SpringFactoriesLoaderTest {
	
	public static void main(String[] args) {
		Class<?> factoryClass = PropertySourceLoader.class;
		System.err.println(factoryClass.getClassLoader());
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		List<String> result = SpringFactoriesLoader.loadFactoryNames(factoryClass, classLoader);
		for(String con : result){
			System.out.println(con);
		}
	}
}