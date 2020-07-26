package Utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.configuration.Json;
import Utils.database.ConnectionManager;
import Utils.database.connection.StandardConnection;
import Utils.database.metadata.TableToMetadata;
import Utils.database.metadata.pojos.ChildTable;
import Utils.database.metadata.pojos.Column;
import Utils.database.metadata.pojos.Metadata;
import Utils.database.metadata.pojos.ParentTable;
import Utils.database.metadata.pojos.PrimaryKey;
import Utils.database.metadata.pojos.Table;

public class Tester {

	private static Logger logger = LoggerFactory.getLogger(Tester.class);

	public static void main(String[] args) {

		Metadata metadata = new Metadata();
		List<Table> tables = new ArrayList<Table>();
		Table table = new Table();
		List<ChildTable> childTables = new ArrayList<ChildTable>();
		childTables.add(new ChildTable(""));
		table.setChildTables(childTables);
		List<Column> columns = new ArrayList();
		columns.add(new Column());
		table.setColumns(columns);
		List<ParentTable> parentTables = new ArrayList();
		parentTables.add(new ParentTable());
		table.setParentTables(parentTables);
		List<PrimaryKey> primaryKeys = new ArrayList();
		primaryKeys.add(new PrimaryKey());
		table.setPrimaryKeys(primaryKeys);
		tables.add(table);
		metadata.setTables(tables);

		logger.info(Json.getPrettyJson(metadata));

		ConnectionManager connManager = new StandardConnection("jdbc:oracle:thin:@10.0.14.171:1521:vodacom", "SVC_ESB",
				"svc_esb");
		Connection conn = connManager.openConnection();
		TableToMetadata toMetadata = new TableToMetadata(conn);
		List<String> tabless = new ArrayList<String>();
		tabless.add("T_SUB_EVENT");
		tabless.add("EVN_ATR_LIST");
		logger.info(toMetadata.getMetadata(tabless).toString());

	}

}
