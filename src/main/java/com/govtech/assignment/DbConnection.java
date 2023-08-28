package com.govtech.assignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection extends BaseUI{

	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;

	public static void openDBConnection() throws ClassNotFoundException {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "user", "userpassword");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static ResultSet runQuery(String query) throws SQLException {

		stmt = con.createStatement();
		rs = stmt.executeQuery(query);
		return rs;
	}
	
	public static void closeConnection() throws SQLException {
		con.close();
	}

}
