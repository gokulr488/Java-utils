package Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.configuration.Json;
import Utils.database.ConnectionManager;
import Utils.database.DB;
import Utils.database.connection.StandardConnection;
import Utils.database.hibernateentity.HibernateEntity;
import Utils.database.jdbcClassGen.JdbcClassGen;
import Utils.database.metadata.TableToMetadata;
import Utils.database.metadata.model.Metadata;
import Utils.database.metadata.model.Table;
import Utils.fileutils.Files;
import Utils.fileutils.filereader.FileRead;
import Utils.fileutils.filewriter.FileWrite;
import Utils.gen.StringOperations;
import Utils.linux.Linux;
import Utils.linux.ScpConfig;

public class Tester {

	private static Logger logger = LoggerFactory.getLogger(Tester.class);

	public static void main(String[] args) throws SQLException {
		
		StringOperations.getPackageNameOfFolder("C:\\Users\\gokul\\Desktop\\workspaces\\inventory\\production-service\\src\\main\\java\\com\\sixdee\\im\\prd\\");
		
		
		Connection con = DB.getConnection("jdbc:h2:C:\\Users\\gokul\\eclipse-workspace\\MisWebServer\\workspace\\store\\db", "user",
				"pass");
		HibernateEntity hib = new HibernateEntity("output/foo/");
		TableToMetadata metadataGen = new TableToMetadata(con);
		List<String>tables=new ArrayList<>();
//		ALL_ORDERS
//		ALL_TRADES
//		CONTRACT_HISTORY
//		SCRIPT_DETAILS
		tables.add("ALL_ORDERS");
		tables.add("ALL_TRADES");
		tables.add("CONTRACT_HISTORY");
		tables.add("SCRIPT_DETAILS");
//		tables.add("asset_details");
//		tables.add("asset_specific_details");
//		tables.add("asset_state_mapping");
//		tables.add("asset_states");
//		tables.add("asset_transaction_box_range_mapping");
//		tables.add("asset_transaction_history");
//		tables.add("asset_transaction_master");
//		tables.add("buyback_configuration");
//		tables.add("buyback_type");
//		tables.add("cancellation_file");
//		tables.add("countable_assets");
//		tables.add("countable_assets_transaction_history");
//		tables.add("countable_assets_transaction_master");
//		tables.add("delivery_order_master");
//		tables.add("distribution_box");
//		tables.add("distribution_box_distribution_box_mapping");
//		tables.add("distribution_box_mapping");
//		tables.add("document_details");
//		tables.add("job_lock");
//		tables.add("label_details");
//		tables.add("language_master");
//		tables.add("material_group");
//		tables.add("material_master");
//		tables.add("material_type");
//		tables.add("message_details");
//		tables.add("notification");
//		tables.add("packing_list_details");
//		tables.add("product_specific_attributes");
//		tables.add("product_transaction");
//		tables.add("return_file");
//		tables.add("stock_opname_details");
//		tables.add("stock_opname_master");
//		tables.add("stock_transfer_history");
//		tables.add("stock_transfer_master");
//		tables.add("transaction_acknowledge_approvers");

		Metadata md = metadataGen.getMetadata(tables);

		for (Table table : md.getTables()) {
			hib.genEntityForTable(table);
		}

		ScpConfig scpConfig = new ScpConfig();
		scpConfig.setDestinationPath("/home/admin/");
		scpConfig.setIpAddress("10.0.14.171");
		scpConfig.setPassword("@tW1Un5e6(@$#a7");
		scpConfig.setUserName("admin");
		Linux.scp(scpConfig, "C:\\Users\\gokul\\Desktop\\test.txt");

//		Linux.executeOnWindows("dir");
//		Linux.executeInDirectory("dir", "C:\\Users\\gokul\\eclipse-workspace");
//
//		System.out
//				.println(DateUtils.changeFormat("1996 10 06 12:30:55", "yyyy MM dd HH:mm:ss", "yyyy MMM dd HH-mm-ss"));
//		System.out.println(DateUtils.getCurrentTimeAs("yyyy MMM dd HH-mm-ss"));

		Json.generatePojo(".\\resources\\test.json", "Parent", "foo", ".\\output");

		FileRead reader = Files.getReader();
		FileWrite writer = Files.getWriter();
		for (String fileName : Files.getFolderUtils().getAllFilesIn("C:\\Users\\gokul\\Desktop\\Algols\\data")) {
			reader.openFile("C:\\Users\\gokul\\Desktop\\Algols\\data\\" + fileName);

			writer.createFile("C:\\Users\\gokul\\Desktop\\Algols\\output\\" + getFileName(fileName));
			for (String line : reader.readAllLines()) {
				line = line.replaceAll("-", "");
				line = line.replaceAll(":", "");
				writer.write(line);
			}

			reader.close();
			writer.close();
		}

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
		JdbcClassGen.generateDaoImplFrom(metadata, "resources");

	}

	private static String getFileName(String fileName) {
		String[] parts = fileName.split("_");
		String stamp = parts[1];
		Timestamp timestamp = new Timestamp(Long.parseLong(stamp));
		System.out.println("SRC FILE NAME =" + fileName);
		stamp = timestamp.toString();
		stamp = stamp.replaceAll(" ", "");
		stamp = stamp.replaceAll(":", "");
		stamp = stamp.replaceAll("\\.", "");
		stamp = stamp.replaceAll("-", "");
		fileName = parts[0] + "_" + stamp + "_" + parts[2];
		fileName = fileName.replace("csv", "txt");
		System.out.println("OUTPUT FILE NAME = " + fileName);
		return fileName;
	}

}
