package org.ludo.utils.gameSaving;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public abstract class Serializer {
	private static Gson gson = new Gson();

	public static String serialize(Map map) {
		return gson.toJson(map);
	}

	public static <T> T deserialize(String json, Type type) {
		T res = gson.fromJson(json, type);
		return res;
	}

}
