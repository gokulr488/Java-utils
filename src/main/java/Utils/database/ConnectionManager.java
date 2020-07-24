package Utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private Connection connection;
	private String url;
	private String userName;
	private String password;

	public ConnectionManager(String url, String userName, String password) {
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	public Connection getConnection() {

		try {
			System.out.println("Connecting with url= " + url + "  username= " + userName + " pass= " + password);
			connection = DriverManager.getConnection(url, userName, password);
			System.out.println("DB Connected");
		} catch (SQLException e) {
			System.out.println("Unable to get DB Connection");
			e.printStackTrace();
		}
		return connection;

	}

	public void closeConnection() {
		try {
			connection.close();
			System.out.println("Succesfully Closed Connection to DB");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
