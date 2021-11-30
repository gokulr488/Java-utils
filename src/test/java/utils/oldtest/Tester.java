package utils.oldtest;

import java.sql.Connection;

import Utils.database.DB;
import Utils.gen.Generate;

public class Tester {

	public static void main(String[] args) {
		Connection con = DB.getConnection("jdbc:mysql://localhost:3306/test", "root", "pass");
		Generate generate = new Generate();
		//generate.tableInfoToCsv(con, "output\\");

		generate.hibernateEntitiesAndRepositories(con,
				"D:\\work\\workspaces\\algols\\Gutils\\src\\main\\java\\Utils\\test");

//		//generate.jdbcClasses(con, "output\\foo\\");
//		
//		
//		generate.hibernateEntitiesAndRepositories(con,
//				"C:\\Users\\gokul\\Desktop\\workspaces\\innoneo\\emr\\emr-api\\innoneo\\innoneoemr\\src\\main\\java\\com\\reds\\service\\innoneoemr\\");
//		System.out.println("over");
		// Folder.createFolder("C:\\Users\\gokul\\Desktop\\workspaces\\inventory\\production-service\\src\\main\\java\\com\\sixdee\\im\\prd\\db\\temp");
		// generate.hibernateEntities(con,
		// "C:\\Users\\gokul\\Desktop\\workspaces\\inventory\\production-service\\src\\main\\java\\com\\sixdee\\im\\prd\\db\\temp");

//		ConnectionManager connManager = new StandardConnection("jdbc:oracle:thin:@10.0.14.171:1521:vodacom", "SVC_ESB",
//				"svc_esb");
//		Connection conn = connManager.openConnection();
//		TableToMetadata toMetadata = new TableToMetadata(conn);
//		List<String> tabless = new ArrayList<String>();
//		tabless.add("T_SUB_EVENT");
//		tabless.add("EVN_ATR_LIST");
//		Metadata metadata = toMetadata.getMetadata(tabless);
//		logger.info(metadata.toString());
//		JdbcClassGen.generateDoFrom(metadata, "resources");
//		JdbcClassGen.generateDaoImplFrom(metadata, "resources");

	}

}
