package com.gr.utils.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gr.utils.fileutils.Files;

public class Json {

	private static Logger logger = LoggerFactory.getLogger(Json.class);

	public static String getJson(Object obj) {

		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	public static String getPrettyJson(Object obj) {
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		Gson gson = builder.setPrettyPrinting().create();
		String beautified = gson.toJson(obj);
		logger.debug(beautified);
		return beautified;

	}

	public static void genJsonFile(Object obj, String destinationPath) {
		if (Files.isFileExisting(destinationPath)) {
			logger.debug("{} file already exists", destinationPath);
		} else {
			Files.writeFullFile(getPrettyJson(obj), destinationPath);
		}
	}

	public static <T> Object readJsonConfig(String filePath, Class<T> classType) {
		return readJson(Files.readResourceFile(filePath), classType);
	}

	public static <T> Object readJsonFile(String filePath, Class<T> classType) {
		return readJson(Files.readFullFile(filePath), classType);
	}

	public static <T> Object readJson(String jsonString, Class<T> classType) {
		Gson gson = new Gson();

		return gson.fromJson(jsonString, classType);
	}

	/**
	 * <h1>To Generate POJO classes from json file</h1> 1. Input File Path
	 * <li>Points to the json file containing the sample json
	 * <li>Either use Full system Path or use relative path like
	 * ".\\resources\\sample.json"
	 * <p>
	 * 2. Output Folder
	 * <li>Specifies the output Folder for the generated POJO classes
	 * <li>Either use Full system Path or use relative path like ".\\output"
	 * <p>
	 * 
	 */
	public static void generatePojo(String inputFilePath, String parentClassName, String packageName,
			String outputFolder) {
		JsonToPojo jsonToPojo = new JsonToPojo();
		jsonToPojo.generatePojo(inputFilePath, parentClassName, packageName, outputFolder);

	}

}
