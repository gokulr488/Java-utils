package Utils.database.metadata;

import java.util.List;

import Utils.database.metadata.pojos.Metadata;

public interface MetadataCollector {

	public Metadata getMetadata(List<String> listOfTables);
}
