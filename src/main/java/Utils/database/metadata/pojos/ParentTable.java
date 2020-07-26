package Utils.database.metadata.pojos;

public class ParentTable {

	private String parentTableName;
	private String parentColName;
	private String foreignColName;

	public String getParentTableName() {
		return parentTableName;
	}

	public void setParentTableName(String parentTableName) {
		this.parentTableName = parentTableName;
	}

	public String getParentColName() {
		return parentColName;
	}

	public void setParentColName(String parentColName) {
		this.parentColName = parentColName;
	}

	public String getForeignColName() {
		return foreignColName;
	}

	public void setForeignColName(String foreignColName) {
		this.foreignColName = foreignColName;
	}

	@Override
	public String toString() {
		return "ParentTable [parentTableName=" + parentTableName + ", parentColName=" + parentColName
				+ ", foreignColName=" + foreignColName + "]";
	}

}
