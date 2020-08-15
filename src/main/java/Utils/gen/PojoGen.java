package Utils.gen;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import Utils.fileutils.Files;
import Utils.fileutils.filewriter.FileWrite;
import Utils.gen.model.PojoModel;
import Utils.gen.model.Variable;

public class PojoGen {

	private static Logger logger = LoggerFactory.getLogger(PojoGen.class);

	/**
	 * @param outputFolder
	 */
	public PojoGen(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	private String outputFolder;

	public void generatePojo(PojoModel model) {
		String variables = generateVariables(model.getVariables());
		String gettersAndSeters = StringOperations.genGettersAndSetters(model.getVariables());

		ST pojoTemplate = GenUtils.getTemplate("template.stg", "pojoTemplate");
		pojoTemplate.add("className", model.getClassName());
		pojoTemplate.add("variables", variables);
		pojoTemplate.add("gettersAndSetters", gettersAndSeters);

		FileWrite writer = Files.getWriter();
		writer.setFolder(outputFolder);
		writer.createFile(model.getClassName() + ".java");
		writer.write(pojoTemplate.render());
		logger.info("Generated Pojo {} class in {} folder", model.getClassName(), outputFolder);
		writer.close();
	}

	private String generateVariables(List<Variable> variables) {
		String genVariables = "";

		for (Variable var : variables) {
			ST template = new ST("private <dataType> <variableName>;");
			template.add("dataType", var.getDataType());
			template.add("variableName", var.getVariableName());

			genVariables += template.render() + "\n";
		}
		return genVariables;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}
}
