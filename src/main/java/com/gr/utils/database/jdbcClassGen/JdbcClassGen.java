package com.gr.utils.database.jdbcClassGen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gr.utils.database.metadata.model.Column;
import com.gr.utils.database.metadata.model.Metadata;
import com.gr.utils.database.metadata.model.Table;
import com.gr.utils.fileutils.Files;
import com.gr.utils.gen.PojoGen;
import com.gr.utils.gen.StringOperations;
import com.gr.utils.gen.model.PojoModel;
import com.gr.utils.gen.model.Variable;

public class JdbcClassGen {
	public static Map<String, String> map;

	public JdbcClassGen() {

		populateMap();

	}

	public static void populateMap() {
		if (map == null) {
			map = new HashMap<>();
			Files.getReader().openResourceFile("javaDataTypeMapping.txt");
			for (String line : Files.getReader().readAllLines()) {
				String[] mapping = line.split(",");
				map.put(mapping[0], mapping[1]);
			}
			Files.getReader().close();
		}
	}

	private static Logger logger = LoggerFactory.getLogger(JdbcClassGen.class);

	public static void generateDoFrom(Metadata metadata, String outputFolder) {
		PojoGen generator = new PojoGen(outputFolder);
		for (PojoModel model : metadataToPojoModel(metadata)) {
			generator.generatePojo(model);
		}

	}

	public static void generateDaoImplFrom(Metadata metadata, String outputFolder) {
		DaoImplGen generator = new DaoImplGen(outputFolder);
		for (Table table : metadata.getTables()) {
			generator.generateDaoImpl(table);
		}

	}

	public static List<PojoModel> metadataToPojoModel(Metadata metadata) {
		List<PojoModel> models = new ArrayList<>();
		for (Table table : metadata.getTables()) {
			PojoModel model = new PojoModel();
			model.setClassName(StringOperations.getClassName(table.getTableName()) + "Do");
			model.setVariables(getVariables(table.getColumns()));
			models.add(model);
		}

		return models;
	}

	public static List<Variable> getVariables(List<Column> columns) {

		populateMap();

		List<Variable> variables = new ArrayList<>();
		for (Column column : columns) {
			Variable var = new Variable();
			var.setVariableName(StringOperations.getVariableName(column.getColumnName()));
			if (map.get(column.getDataType()) == null) {
				logger.error("Could not find Java data type for {} of column {}", column.getDataType(),
						column.getColumnName());
			}
			var.setDataType(map.get(column.getDataType().toUpperCase()));
			variables.add(var);
		}
		return variables;
	}

}
