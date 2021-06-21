package com.jorocha.coopervote.resources.util;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONUtils {
	
	private JSONUtils() {
		
	}
	
	public static <T> List<T> json2ListObject(String jsonString, Class<T> clazz) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
				.create();		
	    Type type = TypeToken.getParameterized(ArrayList.class, clazz).getType();
	    return gson.fromJson(jsonString, type);				
	}

	public static <T> T json2Object(String jsonString, Class<T> clazz) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
				.create();
		
	    return gson.fromJson(jsonString, clazz);	 		
	}

	public static String toJson(Object objeto) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
				.create();
		return gson.toJson(objeto);
	}	

	public static <T> T fromJson(String json, Class<T> clazz) {
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
				.create();
		return gson.fromJson(json, clazz);
	}	
	
}
