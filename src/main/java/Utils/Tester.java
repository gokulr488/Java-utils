package Utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.database.ConnectionManager;
import Utils.database.connection.StandardConnection;
import Utils.database.jdbcClassGen.JdbcClassGen;
import Utils.database.metadata.TableToMetadata;
import Utils.database.metadata.model.Metadata;
import Utils.fileutils.filewriter.FileWrite;

public class Tester {

	private static Logger logger = LoggerFactory.getLogger(Tester.class);

	public static void main(String[] args) {

//		Metadata metadata = new Metadata();
//		List<Table> tables = new ArrayList<Table>();
//		Table table = new Table();
//		List<ChildTable> childTables = new ArrayList<ChildTable>();
//		childTables.add(new ChildTable(""));
//		table.setChildTables(childTables);
//		List<Column> columns = new ArrayList();
//		columns.add(new Column());
//		table.setColumns(columns);
//		List<ParentTable> parentTables = new ArrayList();
//		parentTables.add(new ParentTable());
//		table.setParentTables(parentTables);
//		List<PrimaryKey> primaryKeys = new ArrayList();
//		primaryKeys.add(new PrimaryKey());
//		table.setPrimaryKeys(primaryKeys);
//		tables.add(table);
//		metadata.setTables(tables);
//
//		logger.info(Json.getPrettyJson(metadata));

//		FileWrite writer = new FileWrite();
//		writer.setFolder("resources");
//		writer.createFile("test.txt");
//		writer.write("test");
//		writer.close();

		ConnectionManager connManager = new StandardConnection("jdbc:oracle:thin:@10.0.14.171:1521:vodacom", "SVC_ESB",
				"svc_esb");
		Connection conn = connManager.openConnection();
		TableToMetadata toMetadata = new TableToMetadata(conn);
		List<String> tabless = new ArrayList<String>();
		tabless.add("T_SUB_EVENT");
		tabless.add("EVN_ATR_LIST");
		Metadata metadata = toMetadata.getMetadata(tabless);
		logger.info(metadata.toString());
		JdbcClassGen.generateDoFrom(metadata, "resources");

	}

}
