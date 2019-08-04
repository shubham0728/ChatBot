package com.ss.chatbot.api;

import com.ss.chatbot.model.ResultEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Shubham Singhal
 */
public interface ChatApiClient {

	@GET("chat/")
	Call<ResultEntity> getChatResult(@Query("apiKey") String key, @Query("chatBotID") String chatBotID, @Query("externalID") String externalID, @Query("message") String message);
}
