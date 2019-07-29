package com.session.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

public class ConnectionTest {

	public static void main(String[] args) throws SQLException {
		Set<String> set = new TreeSet<>();
		int i=0;
		while(true){
			System.err.println(i++);
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "d2p9bupt");
			String cString = connection.toString();
			if(set.contains(cString)){
				System.err.println(cString);
			}else {
				set.add(cString);
			}
		}
	}
}