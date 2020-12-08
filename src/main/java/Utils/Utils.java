package Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Utils.configuration.Json;
import Utils.database.DB;
import Utils.gen.Generate;

public class Utils {
	public static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static DB getDB() {
		return new DB();
	}

	public static Generate getCodeGenerator() {
		return new Generate();
	}

	public static Json getJsonHandler() {
		return new Json();
	}
	
	
}
