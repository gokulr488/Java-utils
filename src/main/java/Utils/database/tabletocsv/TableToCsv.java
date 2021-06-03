package Utils.database.tabletocsv;

import java.util.ArrayList;
import java.util.List;

import Utils.Utils;
import Utils.database.metadata.model.Column;
import Utils.database.metadata.model.Metadata;
import Utils.database.metadata.model.Table;
import Utils.fileutils.Files;
import Utils.fileutils.filewriter.FileWrite;
import Utils.fileutils.folder.Folder;

public class TableToCsv {
	private static final String HEADER = "Column Name,Data Type,Size,Default Value";
	private static final String DELIMITER = ",";

	public void metaDataToCsv(Metadata metadata, String outputFolder) {
		Utils.logger.info("Generating CSV for {} tables", metadata.getTables().size());
		Utils.logger.info("Creating output folder {}", outputFolder);
		Folder.createFolder(outputFolder);
		for (Table table : metadata.getTables()) {
			tableToCsv(table, outputFolder);
		}
		Utils.logger.info("CSV writing Completed");
	}

	public void tableToCsv(Table table, String outputFolder) {
		List<String> lines = new ArrayList<>();
		lines.add(HEADER);
		for (Column col : table.getColumns()) {
			StringBuilder builder = new StringBuilder();
			builder.append(col.getColumnName());
			builder.append(DELIMITER);
			builder.append(col.getDataType());
			builder.append(DELIMITER);
			builder.append(col.getSize());
			builder.append(DELIMITER);
			builder.append(col.getDefaultValue());
			lines.add(builder.toString());
		}
		FileWrite writer = Files.getNewWriter();
		writer.createFile(outputFolder + table.getTableName() + ".csv");
		writer.write(lines);
		writer.close();

	}
}
