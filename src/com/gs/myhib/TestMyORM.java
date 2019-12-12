package com.gs.myhib;

public class TestMyORM {

	public static void main(String[] args) {

		Account account = new Account(1, "user 1");

		//testcase1(account);

		// testcase2(account);
		
		MySessionFactory mySessionFactory = new MySessionFactory();

		mySessionFactory.buildSessionFactory("database.properties");

		MySession mySession = mySessionFactory.openSession();
		account= mySession.get(1, Account.class);
		System.out.println(account);

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

	private static void testcase2(Account account) {
		MySessionFactory mySessionFactory = new MySessionFactory();

		mySessionFactory.buildSessionFactory("database.properties");

		MySession mySession = mySessionFactory.openSession();
		mySession.save(account);
	}
}
