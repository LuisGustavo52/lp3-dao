package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MySQLConnectionFactory {

	private static final String JDBC_DRIVER_NAME = 
			"com.mysql.jdbc.Driver";
	
	private static final String DATABASE_URL = 
			"jdbc:mysql://127.0.0.1/facebook";

	static final String USER = "root";
	static final String PASSWORD = "";
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		Class.forName(JDBC_DRIVER_NAME);

		return DriverManager.getConnection(
				DATABASE_URL, USER, PASSWORD);
	}

}
