package com.gr.utils.configuration;

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

import com.gr.utils.Utils;
import com.sun.codemodel.JCodeModel;

class JsonToPojo {
	public void generatePojo(String inputFilePath, String parentClassName, String packageName,
			String outputFolder) {
		JCodeModel codeModel = new JCodeModel();

		try {
			URL source = new File(inputFilePath).toURI().toURL();
			Utils.logger.info("Generating Pojos for {}", source);
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
			Utils.logger.info("Pojos Generated in {} folder", outputPath.getAbsolutePath());

		} catch (IOException e) {
			Utils.logger.error("Unable to generate Pojo for {} file", inputFilePath, e);
		}

	}
}
