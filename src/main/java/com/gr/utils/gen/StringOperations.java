package com.gr.utils.gen;

import java.util.List;

import org.stringtemplate.v4.ST;

import com.gr.utils.gen.model.Variable;

public class StringOperations {

	public static String genGettersAndSetters(List<Variable> variables) {
		String gettersAndSetters = "";

		for (Variable var : variables) {
			ST template = GenUtils.getTemplate("template.stg", "getterSetter");
			template.add("dataType", var.getDataType());
			template.add("capVariableName", getCapStartLetter(var.getVariableName()));
			template.add("smallVariableName", var.getVariableName());

			gettersAndSetters += template.render() + "\n";
		}
		return gettersAndSetters;
	}

	public static String getClassName(String tableName) {
		String className = "";
		String words[] = tableName.split("_");
		for (String word : words) {
			word = word.toLowerCase();
			word = word.substring(0, 1).toUpperCase() + word.substring(1);
			className += word;
		}
		return className;
	}

	public static String getVariableName(String columnName) {
		String varName = "";
		int i = 0;
		String words[] = columnName.split("_");
		for (String word : words) {
			word = word.toLowerCase();
			if (i > 0) {
				word = word.substring(0, 1).toUpperCase() + word.substring(1);
			}
			varName += word;
			i++;
		}
		return varName;
	}

	public static String getCapStartLetter(String variableName) {
		String str = variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
		return str;
	}

	public static String getSmallStartLetter(String variableName) {
		String str = variableName.substring(0, 1).toLowerCase() + variableName.substring(1);
		return str;
	}

	public static String getPackageNameOfFolder(String outputFolder) {

		String str = outputFolder.split("src\\\\main\\\\java\\\\")[1];
		str = str.replaceAll("\\\\", ".");
		char lastChar = str.charAt(str.length() - 1);
		if (lastChar == '.') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

}
