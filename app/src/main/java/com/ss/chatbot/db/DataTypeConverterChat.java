package com.ss.chatbot.db;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ss.chatbot.model.ChatEntity;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class DataTypeConverterChat {
	@TypeConverter
	public static List<ChatEntity> stringToMeasurements(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<ChatEntity>>() {

		}.getType();
		List<ChatEntity> measurements = gson.fromJson(json, type);
		return measurements;
	}

	@TypeConverter
	public static String measurementsToString(List<ChatEntity> list) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<ChatEntity>>() {
		}.getType();
		String json = gson.toJson(list, type);
		return json;
	}

}
