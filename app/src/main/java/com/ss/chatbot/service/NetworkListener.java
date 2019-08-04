package com.ss.chatbot.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ss.chatbot.interfaces.ConnectivityListener;

/**
 * Created by Shubham Singhal
 */
public class NetworkListener extends BroadcastReceiver {

	public static ConnectivityListener mConnectivityListener;

	public NetworkListener(ConnectivityListener connectivityListener) {
		mConnectivityListener = connectivityListener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		mConnectivityListener.onNetworkConnectionChanged(isConnected(context));
	}

	/**
	 * Identify Network Status and return boolean value.
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
	}

}
