package com.gs.myhib;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnection {

	public static void main(String[] args) {

		// testCon();

		Integer integer = 23;
		System.out.println(integer.getClass());

		MyORM myORM = new MyORM();
		// Account account1 = new Account(4, "user4");
		// myORM.save(account1);

		Account accountFromDB = (Account) myORM.get(2, Account.class);
		System.out.println(accountFromDB);

	}

	private static void testCon() {
		try {
			System.out.println(DBManager.getConnection());

			Connection con = DBManager.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("SELECT table_name FROM user_tables");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				System.out.println(resultSet.getString(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
