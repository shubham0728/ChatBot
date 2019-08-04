package com.ss.chatbot.model;

import java.io.Serializable;

/**
 * Created by Shubham Singhal
 */
public class ResultEntity implements Serializable {

	private ChatEntity message;

	public ChatEntity getMessage() {
		return message;
	}

	public void setMessage(ChatEntity message) {
		this.message = message;
	}
}
