package com.ss.chatbot.application;

		import android.app.Application;

		import com.ss.chatbot.interfaces.ConnectivityListener;
		import com.ss.chatbot.service.NetworkListener;

/**
 * Created by Shubham Singhal
 */
public class ChatBotApplication extends Application {

	private static ChatBotApplication mInstance;

	@Override
	public void onCreate() {
		super.onCreate();

		mInstance = this;
	}

	public static synchronized ChatBotApplication getInstance() {
		return mInstance;
	}

	public void setConnectivityListener(ConnectivityListener listener) {
		NetworkListener.mConnectivityListener = listener;
	}
}
