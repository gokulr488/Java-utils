package Utils.database.hibernaterepository;

import org.stringtemplate.v4.ST;

import Utils.Utils;
import Utils.database.jdbcClassGen.JdbcClassGen;
import Utils.database.metadata.model.Table;
import Utils.fileutils.Files;
import Utils.fileutils.filewriter.FileWrite;
import Utils.gen.GenUtils;
import Utils.gen.StringOperations;

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
		template.add("packageName", packageName);
		template.add("entityName", StringOperations.getClassName(table.getTableName()));

		return template.render();
	}

}
