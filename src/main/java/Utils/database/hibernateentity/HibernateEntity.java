package Utils.database.hibernateentity;

import java.util.List;

import org.stringtemplate.v4.ST;

import Utils.Utils;
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
	private String packageName;

	public HibernateEntity(String outputFolder, String packageName) {
		this.outputFolder = outputFolder;
		this.packageName = packageName;
		JdbcClassGen.populateMap();
	}

	public void genEntityForTable(Table table) {
		Utils.logger.info("Generating Entity classes for {} table", table.getTableName());
		generateFiles(table);
		Utils.logger.info("Entity classes generated for {} table in {}", table.getTableName(), outputFolder);
	}

	private void generateFiles(Table table) {
		String className = StringOperations.getClassName(table.getTableName());
		FileWrite writer = Files.getWriter();
		writer.createFile(outputFolder + "\\" + className + ".java");
		writer.write(generateCode(table));

		writer.close();
	}

	private String generateCode(Table table) {
		ST template = GenUtils.getTemplate("template.stg", "hibernateEntity");
		template.add("packageName", packageName);
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
				temp.add("thisClassName", StringOperations.getClassName(table.getTableName()));
				temp.add("childTableClass", StringOperations.getClassName(childTable.getChildTableName()));
				temp.add("childTableObject", StringOperations.getVariableName(childTable.getChildTableName()));
				oneToManyMappings += temp.render();
			}
		}
		return oneToManyMappings;
	}

	private Object getGettersAndSeters(Table table) {
		List<Variable> variables = JdbcClassGen.getVariables(table.getColumns());

		if (table.getParentTables() != null) {
			for (ParentTable parentTable : table.getParentTables()) {
				for (Variable variable : variables) {// To prevent getters and setter for ManyToOne annotated variables.
														// we need field variables not column variables.
					if (variable.getVariableName()
							.equals(StringOperations.getVariableName(parentTable.getForeignColName()))) {
						variables.remove(variable);
						break;
					}
				}

				// To generate getters and setters for ManyToOne annotated fields
				Variable var = new Variable();
				var.setDataType(StringOperations.getClassName(parentTable.getParentTableName()));
				var.setVariableName(StringOperations.getVariableName(parentTable.getParentTableName()));
				variables.add(var);
			}
		}
		return StringOperations.genGettersAndSetters(variables);
	}

	private String getPrimaryKey(Table table) {

		if (table.getPrimaryKeys().size() > 0) {
			// Primary key object doesn't have the data type. So get data type of PKey from
			// the list of columns
			String primaryKey = table.getPrimaryKeys().get(0).getPrimaryKey();
			String dataType = "undefined";
			for (Column col : table.getColumns()) {
				if (primaryKey.equals(col.getColumnName())) {
					dataType = JdbcClassGen.map.get(col.getDataType());
				}
			}

			ST temp = new ST("	@Column(name = \"<primaryKey>\") \n" + " 	private <dataType> <variableName>;");
			temp.add("primaryKey", primaryKey);
			temp.add("dataType", dataType);
			temp.add("variableName", StringOperations.getVariableName(primaryKey));
			return temp.render();
		} else {
			return "";
		}
	}

	private String getVariables(Table table) {
		String variables = "";
		for (Column column : table.getColumns()) {

			if (table.getPrimaryKeys().size() > 0 // there might not be any primary keys defined
					&& column.getColumnName().equals(table.getPrimaryKeys().get(0).getPrimaryKey())) {
				continue;
			}
			boolean skipParentTable = false;
			if (table.getParentTables() != null) {
				for (ParentTable parentTable : table.getParentTables()) {
					if (parentTable.getForeignColName().equals(column.getColumnName())) {
						skipParentTable = true;
						break;
					}
				}
			}
			if (!skipParentTable) {
				ST var = new ST("	<timeStamp>\r\n	@Column(name = \"<columnName>\" <length>)\r\n"
						+ "	private <dataType> <variableName>;\r\n\r\n");
				String variableName = StringOperations.getVariableName(column.getColumnName());
				var.add("columnName", column.getColumnName());
				var.add("dataType", JdbcClassGen.map.get(column.getDataType()));
				var.add("variableName", variableName);
				var.add("length", ",length = " + column.getSize());
				if (variableName.equals("createdAt") || variableName.equals("createdOn")) {
					var.add("timeStamp", "@CreationTimestamp");
				} else if (variableName.equals("updatedAt") || variableName.equals("modifiedOn")
						|| variableName.equals("updatedOn")) {
					var.add("timeStamp", "@UpdateTimestamp");
				} else {
					var.add("timeStamp", "");
				}

				variables += var.render();
			}
		}
		return variables;
	}

	public void genEntityforTables(List<Table> tables) {
		for (Table table : tables) {
			genEntityForTable(table);
		}
	}
}
