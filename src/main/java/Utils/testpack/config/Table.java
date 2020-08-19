package Utils.testpack.config;

public class Table {
	private String tableName = "tabel_name";
	private String csvInputFolder = "csv folder";
	private String fieldDelimiter = "\\|";
	private String lineDelimiter = "\n";
	private int noOfRowsToIgnore = 1;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCsvInputFolder() {
		return csvInputFolder;
	}

	public void setCsvInputFolder(String csvInputFolder) {
		this.csvInputFolder = csvInputFolder;
	}

	public String getFieldDelimiter() {
		return fieldDelimiter;
	}

	public void setFieldDelimiter(String fieldDelimiter) {
		this.fieldDelimiter = fieldDelimiter;
	}

	public String getLineDelimiter() {
		return lineDelimiter;
	}

	public void setLineDelimiter(String lineDelimiter) {
		this.lineDelimiter = lineDelimiter;
	}

	public int getNoOfRowsToIgnore() {
		return noOfRowsToIgnore;
	}

	public void setNoOfRowsToIgnore(int noOfRowsToIgnore) {
		this.noOfRowsToIgnore = noOfRowsToIgnore;
	}
}
