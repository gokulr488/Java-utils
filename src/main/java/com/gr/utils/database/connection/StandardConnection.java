package com.gr.utils.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gr.utils.database.ConnectionManager;

public class StandardConnection implements ConnectionManager {

	private static Logger logger = LoggerFactory.getLogger(StandardConnection.class);

	private Connection connection;
	private String url;
	private String userName;
	private String password;

	public StandardConnection(String url, String userName, String password) {
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	@Override
	public Connection openConnection() {

		try {
			logger.info("Connecting to DB, URL: {} username: {} password: {}", url, userName, password);
			connection = DriverManager.getConnection(url, userName, password);
			connection.setAutoCommit(false);
			logger.info("DB Connected. Auto Commit set to FALSE");
		} catch (SQLException e) {
			logger.error("Unable to get DB Connection", e);

		}
		return connection;

	}

	public Connection getOpenedConnection() {
		try {
			if (connection.isClosed()) {
				logger.error("Connection is not currently open");
				return null;
			} else {
				return connection;
			}

		} catch (SQLException e) {
			return null;
		}

	}

	@Override
	public void closeConnection() {
		try {
			connection.close();
			logger.warn("Closed Connection to DB ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getNewConnection(String url, String userName, String password) {
		Connection conn = null;

		try {
			logger.info("Connecting to DB, URL: {} username: {} password: {}", url, userName, password);
			conn = DriverManager.getConnection(url, userName, password);
			logger.info("DB Connected");
		} catch (SQLException e) {
			logger.error("Unable to get DB Connection", e);

		}
		return conn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
