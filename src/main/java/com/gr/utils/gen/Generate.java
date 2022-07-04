package com.gr.utils.gen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gr.utils.Utils;
import com.gr.utils.configuration.Json;
import com.gr.utils.database.hibernateentity.HibernateEntity;
import com.gr.utils.database.hibernaterepository.HibernateRepository;
import com.gr.utils.database.jdbcClassGen.JdbcClassGen;
import com.gr.utils.database.metadata.TableToMetadata;
import com.gr.utils.database.metadata.model.Metadata;
import com.gr.utils.database.metadata.model.Table;
import com.gr.utils.database.tabletocsv.TableToCsv;
import com.gr.utils.fileutils.folder.Folder;

public class Generate {
	private Metadata metadata;

	/**
	 * <h1>To Generate POJO classes from json file</h1> 1. Input File Path
	 * <li>Points to the json file containing the sample json
	 * <li>Either use Full system Path or use relative path like
	 * ".\\resources\\sample.json"
	 * <p>
	 * 2. Output Folder
	 * <li>Specifies the output Folder for the generated POJO classes
	 * <li>Either use Full system Path or use relative path like ".\\output"
	 * <p>
	 * 
	 */
	public void pojoForJson(String inputFilePath, String parentClassName, String packageName, String outputFolder) {
		Json.generatePojo(inputFilePath, parentClassName, packageName, outputFolder);
	}

	public void hibernateEntitiesAndRepositories(Connection conn, String projectFolder) {
		hibernateEntitiesAndRepositories(conn, projectFolder, null);
	}

	public void hibernateEntitiesAndRepositories(Connection conn, String projectFolder, List<String> tables) {
		Folder.createFolder(projectFolder + "db");
		Folder.createFolder(projectFolder + "db\\repository");
		Folder.createFolder(projectFolder + "db\\entities");
		hibernateRepositories(conn, projectFolder + "db\\repository", tables);
		hibernateEntities(conn, projectFolder + "db\\entities", tables);

	}

	public void hibernateEntities(Connection conn, String outputFolder, List<String> tables) {
		Utils.logger.info("Starting Generation of Entities");
		getMetaData(conn, tables);
		HibernateEntity hibernate = new HibernateEntity(outputFolder,
				StringOperations.getPackageNameOfFolder(outputFolder));
		for (Table table : metadata.getTables()) {
			hibernate.genEntityForTable(table);
		}

	}

	public void hibernateRepositories(Connection conn, String outputFolder, List<String> tables) {
		Utils.logger.info("Starting Generation of Repositories");
		getMetaData(conn, tables);
		HibernateRepository hibernate = new HibernateRepository(outputFolder,
				StringOperations.getPackageNameOfFolder(outputFolder));
		for (Table table : metadata.getTables()) {
			hibernate.genRepositoryForTable(table);
		}
	}

	public void jdbcClasses(Connection conn, String outputFolder, List<String> tables) {
		Utils.logger.info("Starting Generation of JDBC DO, DAOImpl classes");
		getMetaData(conn, tables);
		JdbcClassGen.generateDoFrom(metadata, outputFolder);
		JdbcClassGen.generateDaoImplFrom(metadata, outputFolder);
	}

	private void getMetaData(Connection conn, List<String> tables) {
		if (metadata != null) {
			return;
		}
		TableToMetadata metadataGen = new TableToMetadata(conn);
		if (tables == null) {
			tables = getAllTablesInSchema(conn);
		}

		metadata = metadataGen.getMetadata(tables);
	}

	private List<String> getAllTablesInSchema(Connection conn) {
		List<String> tables = new ArrayList<String>();
		try {
			DatabaseMetaData md = conn.getMetaData();
			ResultSet resultSet = md.getTables(conn.getCatalog(), null, null, null);
			int noOfTables = 0;
			while (resultSet.next()) {
				tables.add(resultSet.getString("TABLE_NAME"));
				noOfTables++;
			}
			Utils.logger.info("No Of Tables in Schema= {}", noOfTables);
		} catch (SQLException e) {
			Utils.logger.error("Unable to get list of all tables in schema", e);
		}

		return tables;
	}

	public void tableInfoToCsv(Connection conn, String outputFolder) {
		getMetaData(conn, null);
		TableToCsv csvGen = new TableToCsv();
		csvGen.metaDataToCsv(metadata, outputFolder);
	}
}
