package Utils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Utils.database.DB;
import Utils.fileutils.zip.UnZip;
import Utils.gen.Generate;

public class Tester {

	public static void main(String[] args) {

		//Json.generatePojo(".\\input\\reservationResp.json", "BlockAssetResp", "com.sixdee.im.arm.pojos.reservation", ".\\output\\");

		
		UnZip.unZip("input/test.zip", "output");
		
		
		
		
		
		
		
		
		
		
		
		
		
		Connection con = DB.getConnection("jdbc:mysql://localhost:3303/oscar", "root", "my-secret-pw");
		Generate generate = new Generate();
		List<String> tables = Arrays.asList("Department");
		
		
		generate.hibernateEntitiesAndRepositories(con,
				"C:\\Users\\gokul\\Desktop\\workspaces\\innoneo\\emr\\emr-api\\innoneo\\innoneoemr\\src\\main\\java\\com\\reds\\service\\innoneoemr\\",tables);
		
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
