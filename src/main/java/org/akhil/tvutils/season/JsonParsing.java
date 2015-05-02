package org.akhil.tvutils.season;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonParsing {
	
	public static Map<String, TvSeries> parse(String jsonString) {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, TvSeries>>() {}.getType(); 
		Map<String, TvSeries> map = gson.fromJson(jsonString, type);
		return map;
	}

}
