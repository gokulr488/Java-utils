package utils.oldtest;

import java.sql.Connection;

import com.gr.utils.database.DB;
import com.gr.utils.gen.Generate;

public class Tester {

	public static void main(String[] args) {
		Connection con = DB.getConnection("jdbc:mysql://localhost:3306/pcex_db", "root", "pass");
		Generate generate = new Generate();
		// generate.tableInfoToCsv(con, "output\\");

		//generate.jdbcClasses(con, "D:\\work\\temp", null);

		generate.hibernateEntitiesAndRepositories(con,
				"C:\\Users\\91740\\eclipse-workspace\\utils\\src\\main\\java\\com\\gr\\utils\\test\\");
		
		generate.jdbcClasses(con, "D:\\work\\temp", null);

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
