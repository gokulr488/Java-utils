package com.gr.utils.database.metadata;

import java.util.List;

import com.gr.utils.database.metadata.model.Metadata;

public interface MetadataCollector {

	public Metadata getMetadata(List<String> listOfTables);
}
