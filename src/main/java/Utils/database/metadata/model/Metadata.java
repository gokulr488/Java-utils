package Utils.database.metadata.model;

import java.util.List;

public class Metadata {

	private String schemaName;
	
	List<Table> tables;

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	@Override
	public String toString() {
		return "Metadata [schemaName=" + schemaName + ", tables=" + tables + "]";
	}
}
