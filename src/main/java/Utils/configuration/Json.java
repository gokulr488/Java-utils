package Utils.configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.codemodel.JCodeModel;

public class Json {

	private static Logger logger = LoggerFactory.getLogger(Json.class);

	public static String getJson(Object obj) {

		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	public static String getPrettyJson(Object obj) {
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		Gson gson = builder.setPrettyPrinting().create();
		String beautified = gson.toJson(obj);
		logger.debug(beautified);
		return beautified;

	}

	public static <T> Object readJson(String jsonString, Class<T> classType) {
		Gson gson = new Gson();

		return gson.fromJson(jsonString, classType);
	}

	/**
	 * <h1>To Generate POJO classes from json file</h1> 1. Input File Path
	 * <li>Points to the json file containing the sample json
	 * <li>Either use Full system Path or use relative path like
	 * ".\\resources\\sample.json"
	 * <p>
	 * 2. Output Folder
	 * <li>Specifies the output Folder for the generated POJO classes
	 * <li>Either use Full system Path or use relative path like ".\\output"
	 * <p>
	 * 
	 */
	public static void generatePojo(String inputFilePath, String parentClassName, String packageName,
			String outputFolder) {
		JCodeModel codeModel = new JCodeModel();

		try {
			URL source = new File(inputFilePath).toURI().toURL();
			logger.info("Generating Pojos for {}", source);
			GenerationConfig config = new DefaultGenerationConfig() {
				@Override
				public boolean isGenerateBuilders() { // set config option by overriding method
					return true;

				}

				@Override
				public SourceType getSourceType() {
					return SourceType.JSON;
				}

				@Override
				public boolean isIncludeAdditionalProperties() {

					return false;
				}
			};

			SchemaMapper mapper = new SchemaMapper(
					new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
			mapper.generate(codeModel, parentClassName, packageName, source);
			File outputPath = new File(outputFolder);
			codeModel.build(outputPath);
			logger.info("Pojos Generated in {} folder", outputPath.getAbsolutePath());

		} catch (IOException e) {
			logger.error("Unable to generate Pojo for {} file", inputFilePath, e);
		}

	}

}
