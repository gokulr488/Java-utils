package com.gr.utils.database.metadata.model;

public class ChildTable {

	public ChildTable(String childTableName) {
		this.childTableName = childTableName;
	}

	private String childTableName;

	public String getChildTableName() {
		return childTableName;
	}

	public void setChildTableName(String childTableName) {
		this.childTableName = childTableName;
	}

	@Override
	public String toString() {
		return "ChildTable [childTableName=" + childTableName + "]";
	}

}
