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
import Utils.linux.Linux;
import Utils.linux.ScpConfig;

public class Tester {

	private static Logger logger = LoggerFactory.getLogger(Tester.class);

	public static void main(String[] args) throws SQLException {
		Connection con = DB.getConnection("jdbc:mysql://localhost:3308/inventory_29_sep", "snd",
				"snd@6Dtech");
		HibernateEntity hib = new HibernateEntity("output/foo/");
		TableToMetadata metadataGen = new TableToMetadata(con);
		Metadata md = metadataGen.getMetadata("asset_details");

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
