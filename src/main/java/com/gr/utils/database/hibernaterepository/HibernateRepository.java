package com.gr.utils.database.hibernaterepository;

import org.stringtemplate.v4.ST;

import com.gr.utils.Utils;
import com.gr.utils.database.jdbcClassGen.JdbcClassGen;
import com.gr.utils.database.metadata.model.Table;
import com.gr.utils.fileutils.Files;
import com.gr.utils.fileutils.filewriter.FileWrite;
import com.gr.utils.gen.GenUtils;
import com.gr.utils.gen.StringOperations;

public class HibernateRepository {

	private String outputFolder;
	private String packageName;

	/**
	 * @param outputFolder
	 * @param packageName
	 */
	public HibernateRepository(String outputFolder, String packageName) {
		this.outputFolder = outputFolder;
		this.packageName = packageName;
		JdbcClassGen.populateMap();
	}

	public void genRepositoryForTable(Table table) {
		Utils.logger.info("Generating Repository classes for {} table", table.getTableName());
		generateFiles(table);
		Utils.logger.info("Repository classes generated for {} table in {}", table.getTableName(), outputFolder);

	}

	private void generateFiles(Table table) {
		String className = StringOperations.getClassName(table.getTableName());
		FileWrite writer = Files.getWriter();
		writer.createFile(outputFolder + "\\" + className + "Repository.java");
		writer.write(generateCode(table));
		writer.close();

	}

	private String generateCode(Table table) {
		ST template = GenUtils.getTemplate("template.stg", "hibernateRepository");
		template.add("entityImport", getEntityImportString(packageName));
		template.add("packageName", packageName);
		template.add("entityName", StringOperations.getClassName(table.getTableName()));

		return template.render();
	}

	private String getEntityImportString(String packageName) {
		packageName = packageName.replaceAll(".repository", ".entities");
		return packageName;

	}

}
