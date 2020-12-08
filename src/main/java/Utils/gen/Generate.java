package Utils.gen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Utils.Utils;
import Utils.configuration.Json;
import Utils.database.hibernateentity.HibernateEntity;
import Utils.database.hibernaterepository.HibernateRepository;
import Utils.database.jdbcClassGen.JdbcClassGen;
import Utils.database.metadata.TableToMetadata;
import Utils.database.metadata.model.Metadata;
import Utils.database.metadata.model.Table;
import Utils.fileutils.folder.Folder;

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
		Folder.createFolder(projectFolder + "db");
		Folder.createFolder(projectFolder + "db\\repository");
		Folder.createFolder(projectFolder + "db\\entities");
		hibernateRepositories(conn, projectFolder + "db\\repository");
		hibernateEntities(conn, projectFolder + "db\\entities");

	}

	public void hibernateEntities(Connection conn, String outputFolder) {
		Utils.logger.info("Starting Generation of Entities");
		getMetaData(conn);
		HibernateEntity hibernate = new HibernateEntity(outputFolder,
				StringOperations.getPackageNameOfFolder(outputFolder));
		for (Table table : metadata.getTables()) {
			hibernate.genEntityForTable(table);
		}

	}

	public void hibernateRepositories(Connection conn, String outputFolder) {
		Utils.logger.info("Starting Generation of Repositories");
		getMetaData(conn);
		HibernateRepository hibernate = new HibernateRepository(outputFolder,
				StringOperations.getPackageNameOfFolder(outputFolder));
		for (Table table : metadata.getTables()) {
			hibernate.genRepositoryForTable(table);
		}
	}
	
	public void jdbcClasses(Connection conn, String outputFolder) {
		Utils.logger.info("Starting Generation of JDBC DO, DAOImpl classes");
		getMetaData(conn);
		JdbcClassGen.generateDoFrom(metadata, outputFolder);
		JdbcClassGen.generateDaoImplFrom(metadata, outputFolder);
	}

	private void getMetaData(Connection conn) {
		if (metadata != null) {
			return;
		}
		TableToMetadata metadataGen = new TableToMetadata(conn);
		List<String> tables = getAllTablesInSchema(conn);

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
}