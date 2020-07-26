package Utils.database.dataobjpojo.pojos;

public class ForeignKey {

	private String columnName;
	private String varName;
	private String foreignTableEntityClassName;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getForeignTableEntityClassName() {
		return foreignTableEntityClassName;
	}

	public void setForeignTableEntityClassName(String foreignTableEntityClassName) {
		this.foreignTableEntityClassName = foreignTableEntityClassName;
	}

}
