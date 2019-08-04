package com.ss.chatbot.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shubham Singhal
 */
public class ChatApi {

	private static Retrofit retrofit = null;

	public static Retrofit getRetrofitClient(){
		if(retrofit == null){
			retrofit = new Retrofit.Builder()
					.addConverterFactory(GsonConverterFactory.create())
					.baseUrl("http://www.personalityforge.com/api/")
					.build();
		}
		return retrofit;
	}
}
