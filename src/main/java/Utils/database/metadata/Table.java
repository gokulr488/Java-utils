package Utils.database.metadata;

import java.util.List;

public class Table {

	private String tableName;
	private String tableSchema;
	List<PrimaryKey> primaryKeys;
	List<Column> columns;
	List<ForeignKey> foreignKeys;

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<ForeignKey> getForeignKeys() {
		return foreignKeys;
	}

	public void setForeignKeys(List<ForeignKey> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public List<PrimaryKey> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<PrimaryKey> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", tableSchema=" + tableSchema + ", primaryKeys=" + primaryKeys
				+ ", columns=" + columns + ", foreignKeys=" + foreignKeys + "]";
	}
}
