package com.gr.utils.gen.model;

public class Variable {
	private String variableName;
	private String dataType;

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return "Variables [variableName=" + variableName + ", dataType=" + dataType + "]";
	}

}
