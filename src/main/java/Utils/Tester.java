package Utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.database.DB;
import Utils.gen.Generate;

public class Tester {

	private static Logger logger = LoggerFactory.getLogger(Tester.class);

	public static void main(String[] args) throws SQLException {

		Connection con = DB.getConnection("jdbc:mysql://localhost:3306/receive_sim", "root", "pass");
		Generate generate = new Generate();
		generate.hibernateEntitiesAndRepositories(con,
				"C:\\Users\\gokul\\Desktop\\workspaces\\inventory\\production-service\\src\\main\\java\\com\\sixdee\\im\\prd\\");



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
