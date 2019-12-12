package com.gs.myhib;

public class TestMyORM {

	public static void main(String[] args) {

		Account account = new Account(7, "user7");

		// testcase1(account);

		MySessionFactory mySessionFactory = new MySessionFactory();

		mySessionFactory.buildSessionFactory("database.properties");

		MySession mySession = mySessionFactory.openSession();
		mySession.save(account);

	}

	private static void testcase1(Account account) {
		MySessionFactory mySessionFactory = new MySessionFactory();

		mySessionFactory.buildSessionFactory("database.properties");

		MySession mySession = mySessionFactory.openSession();
		MyTransaction myTransaction = mySession.getTransaction();
		myTransaction.beginTransaction();
		mySession.save(account);
		myTransaction.commit();
		mySession.close();
	}

}
