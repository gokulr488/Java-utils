package Utils.gen.model;

import java.util.List;

public class PojoModel {
	private String className;
	private List<Variable> variables;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	@Override
	public String toString() {
		return "PojoModel [className=" + className + ", variables=" + variables + "]";
	}
}
