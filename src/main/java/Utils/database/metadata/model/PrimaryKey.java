package Utils.database.metadata.model;

public class PrimaryKey {

	private String primaryKey;
	private int sequence;
	private int startValue;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getStartValue() {
		return startValue;
	}

	public void setStartValue(int startValue) {
		this.startValue = startValue;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public String toString() {
		return "PrimaryKey [primaryKey=" + primaryKey + ", sequence=" + sequence + ", startValue=" + startValue + "]";
	}
}
