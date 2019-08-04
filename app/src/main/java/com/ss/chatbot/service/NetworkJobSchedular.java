package com.ss.chatbot.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.ss.chatbot.interfaces.ConnectivityListener;
import com.ss.chatbot.views.ChatScreenActivity;

/**
 * Created by Shubham Singhal
 * Service to handle callbacks from the JobScheduler. Requests scheduled with the JobScheduler
 * ultimately land on this service's "onStartJob" method.
 */
public class NetworkJobSchedular extends JobService implements ConnectivityListener {

	private NetworkListener mNetworkListener;
	private ChatScreenActivity chatScreenActivity;
	private String broadcastMessage = "NetworkChange";

	@Override
	public void onCreate() {
		super.onCreate();
		mNetworkListener = new NetworkListener(this);
	}

	/**
	 * When @{@link ChatScreenActivity} is created, it starts this service. This is so that the
	 * activity and this service can communicate back and forth."
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("Network", "onStartCommand");
		return START_NOT_STICKY;
	}

	@Override
	public boolean onStartJob(JobParameters jobParameters) {
		registerReceiver(mNetworkListener, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
		return true;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters) {
		unregisterReceiver(mNetworkListener);
		return true;
	}

	@Override
	public void onNetworkConnectionChanged(boolean isConnected) {

	}
}
