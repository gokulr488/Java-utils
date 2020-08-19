package Utils.testpack.config;

import java.util.List;

import Utils.configuration.Config;
@Config(configFileName  = "CsvLoaderConfig.json")
public class CsvLoaderConfig {
	private List<Table> tables;

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
}
