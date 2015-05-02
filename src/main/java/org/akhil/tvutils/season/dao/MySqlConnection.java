package org.akhil.tvutils.season.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {

	public static Connection getConnection() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			throw new SQLException("Driver NOT Found");
		}

		System.out.println("MySQL JDBC Driver Registered!");
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/tvseries", "root", "root123");
		return connection;
	}

}
