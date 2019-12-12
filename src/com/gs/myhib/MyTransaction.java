package com.gs.myhib;

import java.sql.SQLException;

public class MyTransaction {

	private MySession mySession;

	public MyTransaction(MySession mySession) {
		super();
		this.mySession = mySession;
	}

	public void beginTransaction() {
		try {
			mySession.getConnection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void commit() {
		try {
			mySession.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
