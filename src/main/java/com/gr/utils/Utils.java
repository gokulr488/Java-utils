package com.gr.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gr.utils.configuration.Json;
import com.gr.utils.database.DB;
import com.gr.utils.gen.Generate;

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
