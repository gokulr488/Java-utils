package com.gr.utils.database.jdbcClassGen;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

import com.gr.utils.database.metadata.model.Column;
import com.gr.utils.database.metadata.model.Table;
import com.gr.utils.fileutils.Files;
import com.gr.utils.fileutils.filewriter.FileWrite;
import com.gr.utils.gen.GenUtils;
import com.gr.utils.gen.StringOperations;

public class DaoImplGen {

	private String outputFolder;
	private static Logger logger = LoggerFactory.getLogger(DaoImplGen.class);

	public DaoImplGen(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public void generateDaoImpl(Table table) {
		ST selectQuery = new ST("SELECT <columns> FROM <tableName>");
		selectQuery.add("columns", getColumns(table.getColumns()));
		selectQuery.add("tableName", table.getTableName());
		ST daoImplTemp = GenUtils.getTemplate("template.stg", "daoTemplate");
		daoImplTemp.add("selectQuery", selectQuery.render());
		String className = StringOperations.getClassName(table.getTableName());
		daoImplTemp.add("className", className);
		daoImplTemp.add("gettersFromResultSet", genGettersFromResSet(table));

		FileWrite writer = Files.getWriter();
		writer.setFolder(outputFolder);
		writer.createFile(className + "DaoImpl.java");
		writer.write(daoImplTemp.render());
		logger.info("Generated Dao IMPL for {} in {} folder ", className, outputFolder);
		writer.close();
	}

	private String genGettersFromResSet(Table table) {
		String gettersFromResultSet = "";
		JdbcClassGen.populateMap();
		for (Column col : table.getColumns()) {
			String varName = StringOperations.getVariableName(col.getColumnName());
			ST temp = new ST("eventsDo.set<setter>(res.get<dataType>(\"<columnName>\"));");
			temp.add("setter", StringOperations.getCapStartLetter(varName));
			temp.add("dataType", StringOperations.getCapStartLetter(JdbcClassGen.map.get(col.getDataType())));
			temp.add("columnName", col.getColumnName());
			gettersFromResultSet += temp.render() + "\n";
		}

		return gettersFromResultSet;
	}

	private String getColumns(List<Column> columns) {
		String columnString = "";
		for (Column col : columns) {
			columnString += col.getColumnName() + ",";
		}
		columnString = columnString.substring(0, columnString.length() - 1);
		return columnString;
	}

}
