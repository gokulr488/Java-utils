package Utils.database.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.database.metadata.model.ChildTable;
import Utils.database.metadata.model.Column;
import Utils.database.metadata.model.Metadata;
import Utils.database.metadata.model.ParentTable;
import Utils.database.metadata.model.PrimaryKey;
import Utils.database.metadata.model.Table;

public class TableToMetadata implements MetadataCollector {

	private static Logger logger = LoggerFactory.getLogger(TableToMetadata.class);
	private Connection conn;
	DatabaseMetaData dataBaseMD;

	public TableToMetadata(Connection conn) {
		this.conn = conn;
	}

	public Metadata getMetadata(String table) {
		List<String> tables = new ArrayList<String>();
		tables.add(table);
		return getMetadata(tables);
	}

	@Override
	public Metadata getMetadata(List<String> listOfTables) {
		Metadata metadata = new Metadata();
		try {
			logger.info("Collecting Metadata from DB");
			dataBaseMD = conn.getMetaData();
			metadata.setTables(getTables(listOfTables));
		} catch (SQLException e) {
			logger.error("Failed to collect Metadata from DB", e);
		}

		logger.info("Collected and parsed Metadata from Database");
		return metadata;
	}

	private List<Table> getTables(List<String> listOfTables) throws SQLException {
		List<Table> tables = new ArrayList<Table>();
		for (String tableName : listOfTables) {

			ResultSet res = dataBaseMD.getTables(null, null, tableName, null);

			while (res.next()) {
				Table table = new Table();
				table.setTableName(res.getString("Table_NAME"));
				table.setTableSchema(res.getString("TABLE_SCHEM"));
				table.setColumns(getColumns(tableName));
				table.setPrimaryKeys(getPrimaryKey(tableName));
				table.setParentTables(getParentTables(tableName));

				tables.add(table);
				logger.info("MetaData collected for '{}' table", tableName);
			}
		}
		tables = getChildTables(tables);
		// TODO method to find child tables

		return tables;
	}

	private List<Table> getChildTables(List<Table> tables) {

		for (Table table : tables) {
			for (ParentTable parent : table.getParentTables()) {
				for (Table tbl : tables) {
					if (tbl.getTableName().equals(parent.getParentTableName())) {
						if (tbl.getChildTables() == null) {
							tbl.setChildTables(new ArrayList<ChildTable>());
						}
						tbl.getChildTables().add(new ChildTable(table.getTableName()));
					}
				}
			}
		}

		return tables;
	}

	private List<ParentTable> getParentTables(String tableName) throws SQLException {
		ResultSet res = dataBaseMD.getImportedKeys(null, null, tableName);
		List<ParentTable> parentTables = new ArrayList<ParentTable>();
		List<String> addedParent = new ArrayList<String>();
		while (res.next()) {
			ParentTable parentTable = new ParentTable();
			parentTable.setParentTableName(res.getString("pktable_name"));
			parentTable.setParentColName(res.getString("pkcolumn_name"));
			parentTable.setForeignColName(res.getString("fkcolumn_name"));

			if (addedParent.contains(parentTable.getParentTableName())) {
				logger.warn("ParentTable: {} exist already for Table :{}", parentTable.getParentTableName(), tableName);
			} else {
				parentTables.add(parentTable);
				addedParent.add(parentTable.getParentTableName());
			}
		}

		return parentTables;
	}

	private List<PrimaryKey> getPrimaryKey(String tableName) throws SQLException {
		ResultSet res = dataBaseMD.getPrimaryKeys(null, null, tableName);

		List<PrimaryKey> primaryKeys = new ArrayList<PrimaryKey>();
		List<String> pkAdded = new ArrayList<String>();

		while (res.next()) {
			PrimaryKey primaryKey = new PrimaryKey();
			primaryKey.setPrimaryKey(res.getString("COLUMN_NAME"));
			primaryKey.setSequence(Integer.valueOf(res.getString("KEY_SEQ")));
			if (pkAdded.contains(primaryKey.getPrimaryKey())) {
				logger.warn("PrimaryKey: {} exist already in table: {}", primaryKey.getPrimaryKey(), tableName);
			} else {
				primaryKeys.add(primaryKey);
				pkAdded.add(primaryKey.getPrimaryKey());
			}

		}
		return primaryKeys;
	}

	private List<Column> getColumns(String tableName) throws SQLException {
		ResultSet res = dataBaseMD.getColumns(null, null, tableName, null);

		List<Column> columns = new ArrayList<Column>();
		List<String> colsAdded = new ArrayList<String>();
		while (res.next()) {

			Column column = new Column();
			column.setColumnName(res.getString("COLUMN_NAME"));

			column.setDefaultValue(res.getString("COLUMN_DEF"));

			column.setDataType(res.getString("TYPE_NAME"));

			if (colsAdded.contains(column.getColumnName())) {
				logger.warn("Column: {} exist already in table: {}", column.getColumnName(), tableName);
			} else {
				columns.add(column);
				colsAdded.add(column.getColumnName());
			}
		}

		return columns;
	}

}
