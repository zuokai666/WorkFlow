package com.session.test;

import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Session;

import com.accounting.util.DB;

public class SessionTest {

	public static void main(String[] args) {
		Set<String> set = new TreeSet<>();
		for(int i=1;i<=1;i++){
			Session session = DB.getSession();
			String sessionS = session.toString();
			if(set.contains(sessionS)) {
				System.err.println(sessionS);
			}else {
				set.add(sessionS);
//				System.out.println(sessionS);
			}
			session.close();
		}
	}
}