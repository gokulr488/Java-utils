package Utils.database;

import java.sql.Connection;
import java.sql.SQLException;

import Utils.Utils;
import Utils.database.connection.StandardConnection;

public class DB {
	private static Connection con;
	private static ConnectionManager manager;

	/**
	 * @param url
	 * @param userName
	 * @param password
	 */
	public static Connection getConnection(String url, String userName, String password) {
		try {
			if (con == null || con.isClosed()) {
				manager = new StandardConnection(url, userName, password);
				con = manager.openConnection();
			}

		} catch (SQLException e) {
			Utils.logger.error("Unable to get Connection", e);
		}
		return con;
	}

	public static void closeConnection() {
		manager.closeConnection();
	}
}
