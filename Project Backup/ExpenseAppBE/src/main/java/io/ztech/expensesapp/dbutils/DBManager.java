package io.ztech.expensesapp.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//=========================================================================================================================================
//DB MANAGER CLASS - DATABASE UTILITIES
//=========================================================================================================================================
public class DBManager {
	private String url = "jdbc:mysql://localhost:3306/ExpenseApp?autoReconnect=true&useSSL=false";
	private String username = "root";
	private String password = "Ztech@123";
	Connection connection;

	// creates and returns a connection
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;

	}

	// closes the sent connection
	public void closeConnection(ResultSet resultSet, PreparedStatement prepareStatement, Connection connection) {
		try {
			if (resultSet != null)
				resultSet.close();
			if (prepareStatement != null)
				prepareStatement.close();
			if (connection != null)
				connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
