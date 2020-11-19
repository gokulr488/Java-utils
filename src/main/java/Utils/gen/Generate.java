package Utils.gen;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import Utils.Utils;
import Utils.database.hibernateentity.HibernateEntity;
import Utils.database.hibernaterepository.HibernateRepository;
import Utils.database.metadata.TableToMetadata;
import Utils.database.metadata.model.Metadata;
import Utils.database.metadata.model.Table;

public class Generate {
	private Metadata metadata;

	public void hibernateEntitiesAndRepositories(Connection conn, String outputFolder) {
		hibernateEntities(conn, outputFolder);
		hibernateRepositories(conn, outputFolder);

	}

	public void hibernateEntities(Connection conn, String outputFolder) {
		Utils.logger.info("Starting Generation of Entities");
		getMetaData(conn);
		HibernateEntity hibernate = new HibernateEntity(outputFolder);
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

	private void getMetaData(Connection conn) {
		if (metadata != null) {
			return;
		}
		TableToMetadata metadataGen = new TableToMetadata(conn);
		List<String> tables = getAllTablesInSchema(conn);

		metadata = metadataGen.getMetadata(tables);
	}

	private List<String> getAllTablesInSchema(Connection conn) {
		get list of all tables in the specified schema
		return null;
	}
}
