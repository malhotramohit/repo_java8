package com.gs.myhib;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManagerSingleton {

	private static Connection connection;

	static {
		Properties properties = new Properties();
		InputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("D:\\wks_spring_hib_jlgx\\JAVA8\\src\\database.properties");

			properties.load(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Class.forName(properties.getProperty("database.drivername"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(properties.getProperty("database.url"),
					properties.getProperty("database.username"), properties.getProperty("database.password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return connection;
	}
}
