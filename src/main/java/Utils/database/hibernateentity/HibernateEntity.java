package Utils.database.hibernateentity;

import java.util.List;

import org.stringtemplate.v4.ST;

import Utils.UtilsLogger;
import Utils.database.jdbcClassGen.JdbcClassGen;
import Utils.database.metadata.model.ChildTable;
import Utils.database.metadata.model.Column;
import Utils.database.metadata.model.ParentTable;
import Utils.database.metadata.model.Table;
import Utils.fileutils.Files;
import Utils.fileutils.filewriter.FileWrite;
import Utils.gen.GenUtils;
import Utils.gen.StringOperations;
import Utils.gen.model.Variable;

public class HibernateEntity {
	private String outputFolder;

	public HibernateEntity(String outputFolder) {
		this.outputFolder = outputFolder;
		JdbcClassGen.populateMap();
	}

	public void genEntityForTable(Table table) {
		UtilsLogger.logger.info("Generating Entity classes for {} table", table.getTableName());
		generateFiles(table);
		UtilsLogger.logger.info("Entity classes generated for {} table in {}", table.getTableName(), outputFolder);
	}

	private void generateFiles(Table table) {
		String className = StringOperations.getClassName(table.getTableName());
		FileWrite writer = Files.getWriter();
		writer.createFile(outputFolder + className + ".java");
		writer.write(generateCode(table));

		writer.close();
	}

	private String generateCode(Table table) {
		ST template = GenUtils.getTemplate("template.stg", "hibernateEntity");
		template.add("tableName", table.getTableName());
		template.add("className", StringOperations.getClassName(table.getTableName()));
		template.add("variables", getVariables(table));
		template.add("manyToOne", manyToOneMappings(table));
		template.add("oneToMany", oneToManyMappings(table));
		template.add("primaryKey", getPrimaryKey(table));
		template.add("gettersAndSetters", getGettersAndSeters(table));

		return template.render();
	}

	private String manyToOneMappings(Table table) {
		String manyToOneMappings = "";
		if (table.getParentTables() != null) {
			for (ParentTable parentTable : table.getParentTables()) {
				ST temp = GenUtils.getTemplate("template.stg", "manyTOneMapping");
				temp.add("columnName", parentTable.getForeignColName());
				temp.add("parentClassName", StringOperations.getClassName(parentTable.getParentTableName()));
				temp.add("parentObjName", StringOperations.getVariableName(parentTable.getParentTableName()));
				manyToOneMappings += temp.render();
			}
		}

		return manyToOneMappings;
	}

	private String oneToManyMappings(Table table) {
		String oneToManyMappings = "";
		if (table.getChildTables() != null) {
			for (ChildTable childTable : table.getChildTables()) {
				ST temp = GenUtils.getTemplate("template.stg", "oneToManyMapping");
				temp.add("mappedBy", StringOperations.getVariableName(table.getTableName()));
				temp.add("childTableClass", StringOperations.getClassName(childTable.getChildTableName()));
				temp.add("childTableObject", StringOperations.getVariableName(childTable.getChildTableName()));
				oneToManyMappings += temp.render();
			}
		}
		return oneToManyMappings;
	}

	private Object getGettersAndSeters(Table table) {
		List<Variable> variables = JdbcClassGen.getVariables(table.getColumns());
		return StringOperations.genGettersAndSetters(variables);
	}

	private String getPrimaryKey(Table table) {
		// Primary key object doesn't have the data type. So get data type of PKey from
		// the list of columns
		String primaryKey = table.getPrimaryKeys().get(0).getPrimaryKey();
		String dataType = "undefined";
		for (Column col : table.getColumns()) {
			if (primaryKey.equals(col.getColumnName())) {
				dataType = JdbcClassGen.map.get(col.getDataType());
			}
		}

		ST temp = new ST("private <dataType> <variableName>;");
		temp.add("dataType", dataType);
		temp.add("variableName", StringOperations.getVariableName(primaryKey));
		return temp.render();
	}

	private String getVariables(Table table) {
		String variables = "";
		for (Column column : table.getColumns()) {
			if (column.getColumnName().equals(table.getPrimaryKeys().get(0).getPrimaryKey())) {
				continue;
			}
			if (table.getParentTables() != null) {
				for (ParentTable parentTable : table.getParentTables()) {
					if (parentTable.getForeignColName().equals(column.getColumnName())) {
						continue;
					}
				}
			}

			ST var = new ST(
					"	@Column(name = \"<columnName>\")\r\n" + "	private <dataType> <variableName>;\r\n\r\n");
			var.add("columnName", column.getColumnName());
			var.add("dataType", JdbcClassGen.map.get(column.getDataType()));
			var.add("variableName", StringOperations.getVariableName(column.getColumnName()));

			variables += var.render();
		}
		return variables;
	}

	public void genEntityforTables(List<Table> tables) {
		for (Table table : tables) {
			genEntityForTable(table);
		}
	}
}
