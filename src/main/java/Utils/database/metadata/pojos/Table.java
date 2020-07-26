package Utils.database.metadata.pojos;

import java.util.List;

public class Table {

	private String tableName;
	private String tableSchema;
	private List<PrimaryKey> primaryKeys;
	private List<Column> columns;
	private List<ParentTable> parentTables;
	private List<ChildTable> childTables;

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
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

	public List<ParentTable> getParentTables() {
		return parentTables;
	}

	public void setParentTables(List<ParentTable> parentTables) {
		this.parentTables = parentTables;
	}

	public List<ChildTable> getChildTables() {
		return childTables;
	}

	public void setChildTables(List<ChildTable> childTables) {
		this.childTables = childTables;
	}

	@Override
	public String toString() {
		return "Table [tableName=" + tableName + ", tableSchema=" + tableSchema + ", primaryKeys=" + primaryKeys
				+ ", columns=" + columns + ", parentTables=" + parentTables + ", childTables=" + childTables + "]";
	}

}
