package Utils.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {

	public static String getJson(Object obj) {

		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	public static String getPrettyJson(Object obj) {
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		Gson gson = builder.setPrettyPrinting().create();
		return gson.toJson(obj);

	}

}
