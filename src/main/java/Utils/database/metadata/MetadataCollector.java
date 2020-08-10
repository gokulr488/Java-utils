package Utils.database.metadata;

import java.util.List;

import Utils.database.metadata.model.Metadata;

public interface MetadataCollector {

	public Metadata getMetadata(List<String> listOfTables);
}
