package Utils.database.metadata.pojos;

public class Column {

	private String columnName;
	private String dataType;
	private String defaultValue;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public String toString() {
		return "Column [columnName=" + columnName + ", dataType=" + dataType + ", defaultValue=" + defaultValue + "]";
	}

}
