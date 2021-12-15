package com.gr.utils.database;

import java.sql.Connection;

public interface ConnectionManager {
	public Connection openConnection();

	public void closeConnection();
}
