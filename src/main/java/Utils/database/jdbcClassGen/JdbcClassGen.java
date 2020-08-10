package Utils.database.jdbcClassGen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.database.metadata.model.Column;
import Utils.database.metadata.model.Metadata;
import Utils.database.metadata.model.Table;
import Utils.fileutils.filereader.FileRead;
import Utils.gen.PojoGen;
import Utils.gen.StringOperations;
import Utils.gen.model.PojoModel;
import Utils.gen.model.Variable;

public class JdbcClassGen {

	public JdbcClassGen() {
		if (map == null) {

			populateMap();
		}
	}

	private static void populateMap() {
		map = new HashMap<String, String>();
		FileRead reader = new FileRead();
		//reader.setFolder("resources");
		reader.openResourceFile("javaDataTypeMapping.txt");
		for (String line : reader.getAllLines()) {
			String[] mapping = line.split(",");
			map.put(mapping[0], mapping[1]);
		}

	}

	private static Logger logger = LoggerFactory.getLogger(JdbcClassGen.class);

	private static Map<String, String> map;

	public static void generateDoFrom(Metadata metadata, String outputFolder) {
		PojoGen generator = new PojoGen(outputFolder);
		for (PojoModel model : metadataToPojoModel(metadata)) {
			generator.generatePojo(model);
		}

	}

	public static List<PojoModel> metadataToPojoModel(Metadata metadata) {
		List<PojoModel> models = new ArrayList<PojoModel>();
		for (Table table : metadata.getTables()) {
			PojoModel model = new PojoModel();
			model.setClassName(StringOperations.getClassName(table.getTableName()));
			model.setVariables(getVariables(table.getColumns()));
			models.add(model);
		}

		return models;
	}

	private static List<Variable> getVariables(List<Column> columns) {
		if (map == null) {
			populateMap();
		}
		List<Variable> variables = new ArrayList<Variable>();
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
