package Utils.database.connection;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.database.ConnectionManager;

public class PooledConnection implements ConnectionManager {

	private BasicDataSource dataSource = new BasicDataSource();

	private static Logger logger = LoggerFactory.getLogger(PooledConnection.class);

	private Connection connection;
	private String url;
	private String userName;
	private String password;
	private int minIdle = 5;
	private int maxIdle = 10;
	private int maxPreparedStatements = 25;

	@Override
	public Connection openConnection() {

		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMaxOpenPreparedStatements(maxPreparedStatements);

		try {
			logger.info("Connecting to DB url :{} username :{}", url, userName);
			connection = dataSource.getConnection();
			logger.info("Connected to DB");
		} catch (SQLException e) {
			logger.error("Failed to establish connection with DB ", e);
		}

		return connection;
	}

	public PooledConnection(String url, String userName, String password) {
		this.url = url;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * @param url
	 * @param userName
	 * @param password
	 * @param minIdle
	 * @param maxIdle
	 * @param maxPreparedStatements
	 */
	public PooledConnection(String url, String userName, String password, int minIdle, int maxIdle,
			int maxPreparedStatements) {
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.minIdle = minIdle;
		this.maxIdle = maxIdle;
		this.maxPreparedStatements = maxPreparedStatements;
	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub

	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxPreparedStatements() {
		return maxPreparedStatements;
	}

	public void setMaxPreparedStatements(int maxPreparedStatements) {
		this.maxPreparedStatements = maxPreparedStatements;
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
