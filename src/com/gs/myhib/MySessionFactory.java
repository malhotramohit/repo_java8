package com.gs.myhib;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySessionFactory {

	private Connection connection = null;

	private Properties properties = null;

	public void buildSessionFactory(String propName) {

		properties = new Properties();
		try {
			properties.load(DBManager.class.getClassLoader().getResourceAsStream(propName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			properties.load(DBManager.class.getClassLoader().getResourceAsStream("database.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Class.forName(properties.getProperty("database.drivername"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public MySession openSession() {
		try {
			connection = DriverManager.getConnection(properties.getProperty("database.url"),
					properties.getProperty("database.username"), properties.getProperty("database.password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new MySession(connection);
	}

	public MySession currentSession() {

		return new MySession(connection);
	}

}
