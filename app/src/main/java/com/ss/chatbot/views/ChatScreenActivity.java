package com.ss.chatbot.views;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.ss.chatbot.R;
import com.ss.chatbot.adapter.ChatAdapter;
import com.ss.chatbot.api.ChatApi;
import com.ss.chatbot.api.ChatApiClient;
import com.ss.chatbot.application.ChatBotApplication;
import com.ss.chatbot.interfaces.ConnectivityListener;
import com.ss.chatbot.interfaces.RecyclerViewItemClickListener;
import com.ss.chatbot.model.ChatEntity;
import com.ss.chatbot.model.ResultEntity;
import com.ss.chatbot.respository.ChatViewModel;
import com.ss.chatbot.service.NetworkJobSchedular;
import com.ss.chatbot.service.NetworkListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.net.ConnectivityManagerCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatScreenActivity extends AppCompatActivity implements RecyclerViewItemClickListener, ConnectivityListener {

	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.edittext_chatbox)
	EditText mChatBox;
	@BindView(R.id.button_chatbox_send)
	Button mBtnSendText;
	@BindView(R.id.layout_nothing_to_show)
	LinearLayout mlayoutNothingToShow;
	@BindView(R.id.recycler_chat)
	RecyclerView mChatList;

	ChatViewModel chatViewModel;
	private ChatAdapter chatAdapter;
	private String message;
	private String nameID;
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_screen);
		ButterKnife.bind(this);
		init();
		setDataOnlaunch();
		scheduleJob();
	}

	/**
	 * Initialise all objects in this method.
	 */
	private void init() {
		name = getIntent().getStringExtra("name");
		nameID = getIntent().getStringExtra("id");

		setSupportActionBar(mToolbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		mToolbar.setTitle(name);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		mBtnSendText.setEnabled(false);
		chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
		chatAdapter = new ChatAdapter(this, this);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
		linearLayoutManager.setStackFromEnd(true);
		mChatList.setLayoutManager(linearLayoutManager);
		mChatList.setAdapter(chatAdapter);

		// Send Button click listener.
		mBtnSendText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkConnection();
			}
		});

		// Chat Box text change listener.
		mChatBox.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				if (charSequence.length() == 0)
					mBtnSendText.setEnabled(false);
				else
					mBtnSendText.setEnabled(true);
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}


	/**
	 * Identify Network status using @{@link NetworkListener}.
	 * true - @validateData
	 * false - insert data in local db with sync status as false which
	 * will get synced when application is back online.
	 */
	public void checkConnection() {
		message = mChatBox.getText().toString().trim();
		if (NetworkListener.isConnected(this)) {
			validateData();
			mlayoutNothingToShow.setVisibility(View.INVISIBLE);
		} else {
			ChatEntity chatEntity_sender = new ChatEntity();
			chatEntity_sender.setChatBotID(getResources().getString(R.string.chat_bot_id));
			chatEntity_sender.setType("Sender");
			chatEntity_sender.setMessage(message);
			chatEntity_sender.setNameID(nameID);
			chatEntity_sender.setSynced(false);
			chatEntity_sender.setDateTime(getDate());
			chatViewModel.insert(chatEntity_sender);
			chatAdapter.add(chatEntity_sender);
			mChatBox.setText("");
			chatAdapter.notifyDataSetChanged();
			mChatList.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
			mlayoutNothingToShow.setVisibility(View.INVISIBLE);
			//Toast.makeText(this, getString(R.string.no_netowrk) + "\nMessages will be queued.", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Check if there is any data to diaplay from local db @{@link com.ss.chatbot.db.Database}
	 * on the launch of @{@link ChatScreenActivity}
	 */
	private void setDataOnlaunch() {
		ArrayList<ChatEntity> chatEntities = new ArrayList<>();
		chatEntities.addAll(chatViewModel.loadChatsWithNameID(nameID));
		if (chatEntities.size() == 0) {
			mlayoutNothingToShow.setVisibility(View.VISIBLE);
		} else {
			mlayoutNothingToShow.setVisibility(View.INVISIBLE);
			chatAdapter.addAll(chatEntities);
			mChatList.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
		}
	}

	/**
	 * Insert data as Sender in Local db @{@link com.ss.chatbot.db.Database}
	 * Pass message to api call.
	 */
	private void validateData() {
		if (!TextUtils.isEmpty(message)) {
			ChatEntity chatEntity_sender = new ChatEntity();
			chatEntity_sender.setChatBotID(getResources().getString(R.string.chat_bot_id));
			chatEntity_sender.setType("Sender");
			chatEntity_sender.setMessage(message);
			chatEntity_sender.setNameID(nameID);
			chatEntity_sender.setSynced(true);
			chatEntity_sender.setSenderName(name);
			chatEntity_sender.setDateTime(getDate());
			chatViewModel.insert(chatEntity_sender);
			chatAdapter.add(chatEntity_sender);
			mChatBox.setText("");
			chatAdapter.notifyDataSetChanged();
			mChatList.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
			getChatResult(message);
		} else
			mChatBox.setError("Message cannot be empty");

	}

	/**
	 * Save receiving data in local db @{@link com.ss.chatbot.db.Database}
	 *
	 * @param message
	 */
	private void getChatResult(String message) {
		try {
			ChatApiClient chatApiClient = ChatApi.getRetrofitClient().create(ChatApiClient.class);
			Call<ResultEntity> resultEntityCall = chatApiClient.getChatResult(getResources().getString(R.string.api_key), getResources().getString(R.string.chat_bot_id), name, message);
			resultEntityCall.enqueue(new Callback<ResultEntity>() {
				@Override
				public void onResponse(Call<ResultEntity> call, Response<ResultEntity> response) {
					if (response.isSuccessful()) {
						ResultEntity resultEntity = response.body();
						ChatEntity chatEntity_receiver = resultEntity.getMessage();
						chatEntity_receiver.setType("Receiver");
						chatEntity_receiver.setNameID(nameID);
						chatEntity_receiver.setSynced(true);
						chatEntity_receiver.setSenderName(name);
						chatEntity_receiver.setDateTime(getDate());
						chatViewModel.insert(chatEntity_receiver);
						chatAdapter.add(chatEntity_receiver);
						chatAdapter.notifyDataSetChanged();
						mChatList.smoothScrollToPosition(chatAdapter.getItemCount() - 1);

					} else {
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}

				}

				@Override
				public void onFailure(Call<ResultEntity> call, Throwable t) {
					if (t instanceof SocketTimeoutException || t instanceof IOException) {
						//	Toast.makeText(getApplicationContext(), getString(R.string.no_netowrk), Toast.LENGTH_SHORT).show();
						Log.e("ERROR", getString(R.string.no_netowrk), t);
					} else {
						Log.e("ERROR", getString(R.string.error), t);
						Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onRecyclerViewItemClick(int position) {

	}

	/**
	 * Schedule @{@link JobScheduler}
	 */
	private void scheduleJob() {
		JobInfo jobInfo = new JobInfo.Builder(0, new ComponentName(this, NetworkJobSchedular.class))
				.setRequiresCharging(true)
				.setMinimumLatency(1000)
				.setOverrideDeadline(2000)
				.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
				.setPersisted(true)
				.build();

		JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
		jobScheduler.schedule(jobInfo);
	}

	@Override
	protected void onStop() {
		super.onStop();
		stopService(new Intent(this, NetworkJobSchedular.class));
	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent startServiceIntent = new Intent(this, NetworkJobSchedular.class);
		startService(startServiceIntent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ChatBotApplication.getInstance().setConnectivityListener(this);
	}

	/**
	 * Sync data with chat bot api.
	 */
	public void syncMessages() {
		ArrayList<ChatEntity> nonSyncedChatEntities = new ArrayList<>();
		nonSyncedChatEntities.addAll(chatViewModel.loadChatsWithSyncStatus(false, nameID));

		if (nonSyncedChatEntities.size() > 0) {
			for (int i = 0; i < nonSyncedChatEntities.size(); i++) {
				ChatEntity chatEntity = nonSyncedChatEntities.get(i);
				chatEntity.setSynced(true);
				chatViewModel.updateSource(chatEntity);
				getChatResult(nonSyncedChatEntities.get(i).getMessage());
			}
		}
	}

	/**
	 * Identify network change using @{@link NetworkListener}
	 * If back online sync unsynced messages
	 *
	 * @param isConnected
	 */
	@Override
	public void onNetworkConnectionChanged(boolean isConnected) {
		if (isConnected)
			syncMessages();
	}

	/**
	 * Add Date & Time to messages.
	 *
	 * @return
	 */
	private String getDate() {
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
		Date date = new Date();
		String s = parseFormat.format(date);
		return s;
	}
}
