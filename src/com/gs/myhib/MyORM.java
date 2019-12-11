package com.gs.myhib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MyORM {

	public void save(Object obj) {

		String tableName = getTableName(obj);

		Method[] methods = obj.getClass().getDeclaredMethods();
		HashMap<String, Object> colValMap = new HashMap<>();

		for (int i = 0; i < methods.length; i++) {

			Method method = methods[i];
			if (method.getName().startsWith("get")) {

				String colName = method.getAnnotation(MyColumn.class).name();

				try {
					colValMap.put(colName, method.invoke(obj));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		try {
			boolean isDone = buildAndExecuteInsertQuery(tableName, colValMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getTableName(Object obj) {

		String entityName = obj.getClass().getDeclaredAnnotation(MyEntity.class).name();
		String tableName = obj.getClass().getDeclaredAnnotation(MyTable.class).name();

		return tableName == null ? entityName : tableName;
	}

	private boolean buildAndExecuteInsertQuery(String tableName, HashMap<String, Object> colValMap)
			throws SQLException {

		// insert into account (col1,col2,col3) values (?,?,?)
		String startInsert = "insert into " + tableName;
		String colNames = "(";
		String values = "(";

		for (Map.Entry<String, Object> entry : colValMap.entrySet()) {
			colNames = colNames + entry.getKey() + ",";
		}

		colNames = colNames.substring(0, colNames.length() - 1).concat(")");

		for (int i = 0; i < colValMap.size(); i++) {
			values = values + "?" + ",";
		}

		values = values.substring(0, values.length() - 1).concat(")");

		String finalQuery = startInsert + colNames + " values " + values;
		System.out.println(finalQuery);
		Connection con = DBManager.getConnection();
		PreparedStatement preparedStatement = con.prepareStatement(finalQuery);
		int i = 1;
		for (Map.Entry<String, Object> entry : colValMap.entrySet()) {
			preparedStatement.setObject(i, entry.getValue());
			i++;
		}

		return preparedStatement.execute();
	}

	public Object get(Object id, Class clazz) {

		Class<?> myClass = null;
		try {
			myClass = Class.forName(clazz.getName());
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Object obj = null;
		try {
			obj = myClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String tableName = getTableName(obj);

		ResultSet resultSet = null;

		Method[] methods = clazz.getDeclaredMethods();
		HashMap<String, Object> colValMap = new HashMap<>();
		String primaryColName = null;

		for (int i = 0; i < methods.length; i++) {

			Method method = methods[i];
			if (method.getName().startsWith("get")) {

				MyId annotation = method.getAnnotation(MyId.class);
				if (null != annotation) {

					primaryColName = method.getAnnotation(MyColumn.class).name();
					break;
				}

			}

		}

		String query = "select * from " + tableName + " where " + primaryColName + "=?";

		Connection con;
		try {
			con = DBManager.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setObject(1, id);
			resultSet = preparedStatement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Method[] methods2 = obj.getClass().getDeclaredMethods();

		try {
			while (resultSet.next()) {
				int i = 0;
				for (; i < methods2.length; i++) {
					if (methods2[i].getName().startsWith("get")) {
						Method method = getGetterMethod(methods2[i].getName(), methods2);
						// resultSet.getObject(methods2[i].getDeclaredAnnotation(MyColumn.class).name());
						method.invoke(obj, resultSet.getObject(methods2[i].getDeclaredAnnotation(MyColumn.class).name(),
								methods2[i].getReturnType()));
					}
				}
				i = 0;
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;
	}

	private Method getGetterMethod(String name, Method[] methods2) {
		for (int i = 0; i < methods2.length; i++) {
			if (methods2[i].getName().startsWith("set") && methods2[i].getName().endsWith(name.substring(3))) {
				return methods2[i];
			}
		}
		return null;
	}

	private void loadTables() {
		Connection con;
		try {
			con = DBManager.getConnection();
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
