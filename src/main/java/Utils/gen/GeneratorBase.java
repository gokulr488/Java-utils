package Utils.gen;

import java.util.List;

import org.stringtemplate.v4.ST;

import Utils.database.dataobjpojo.pojos.Variables;

public class GeneratorBase {

	protected String genGettersAndSetters(List<Variables> variables) {
		String gettersAndSetters = "";

		for (Variables var : variables) {
			ST template = GenUtils.getTemplate("resources\\template.stg", "getterSetter");
			template.add("dataType", var.getDataType());
			template.add("capVariableName", getCapStartLetter(var.getVariableName()));
			template.add("smallVariableName", var.getVariableName());

			gettersAndSetters += template.render() + "\n";
		}
		return gettersAndSetters;
	}

	protected String getCapStartLetter(String variableName) {
		String str = variableName.substring(0, 1).toUpperCase() + variableName.substring(1);
		return str;
	}

	protected String getSmallStartLetter(String variableName) {
		String str = variableName.substring(0, 1).toLowerCase() + variableName.substring(1);
		return str;
	}

}
