package Utils.testpack;

import java.util.ArrayList;
import java.util.List;

import Utils.configuration.Json;
import Utils.fileutils.Files;
import Utils.testpack.config.CsvLoaderConfig;
import Utils.testpack.config.Table;
public class CsvLoader {

	public void generateConfig() {
		CsvLoaderConfig config = new CsvLoaderConfig();
		List<Table> tables = new ArrayList<Table>();
		tables.add(new Table());
		config.setTables(tables);
		String def = Json.getPrettyJson(config);
		Files.getWriter().createFile("CsvLoaderConfig.json");
		Files.getWriter().write(def);
		Files.getWriter().close();
	}

	public CsvLoaderConfig getConfig(String configPath) {
		Files.getReader().openFile(configPath);
		String jsonString = Files.getReader().readAsSingleString();
		return (CsvLoaderConfig) Json.readJson(jsonString, CsvLoaderConfig.class);
	}
}
