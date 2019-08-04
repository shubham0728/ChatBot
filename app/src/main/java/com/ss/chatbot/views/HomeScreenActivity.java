package com.ss.chatbot.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ss.chatbot.R;
import com.ss.chatbot.adapter.NewChatAdapter;
import com.ss.chatbot.interfaces.RecyclerViewItemClickListener;
import com.ss.chatbot.model.NewChatEntity;
import com.ss.chatbot.respository.NewChatViewModel;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shubham Singhal
 */
public class HomeScreenActivity extends AppCompatActivity implements RecyclerViewItemClickListener {

	@BindView(R.id.toolbar)
	Toolbar mToolbar;
	@BindView(R.id.recycler_new_chat)
	RecyclerView mNewChatList;
	@BindView(R.id.fab)
	FloatingActionButton mFab;
	@BindView(R.id.layout_nothing_to_show)
	LinearLayout mlayoutNothingToShow;

	private NewChatViewModel chatNameViewModel;
	private NewChatAdapter newChatAdapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		ButterKnife.bind(this);
		init();
		setDataOnLaunch();
	}

	/**
	 * Initialise all objects in this method.
	 */
	private void init() {
		setSupportActionBar(mToolbar);
		mFab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addNewChatDialog();
			}
		});

		chatNameViewModel = ViewModelProviders.of(this).get(NewChatViewModel.class);
		newChatAdapter = new NewChatAdapter(this, this);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
		mNewChatList.setLayoutManager(linearLayoutManager);
		mNewChatList.setAdapter(newChatAdapter);
	}

	/**
	 * Dialog to add Chat Name.
	 * Chat name is saved in Local Db @{@link com.ss.chatbot.db.Database}
	 */
	private void addNewChatDialog() {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View mView = layoutInflater.inflate(R.layout.add_chat_name_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(mView);
		final EditText chatName = (EditText) mView.findViewById(R.id.edt_chatName);
		builder.setCancelable(false)
				.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogBox, int id) {
						// ToDo get user input here
						mlayoutNothingToShow.setVisibility(View.INVISIBLE);
						Random rand = new Random();
						int random = rand.nextInt(10000);
						String name = chatName.getText().toString().trim();
						String chatNameId = name + random + "";
						NewChatEntity chatNameEntity = new NewChatEntity();
						chatNameEntity.setName(name);
						chatNameEntity.setNameID(chatNameId);
						chatNameViewModel.insert(chatNameEntity);
						newChatAdapter.add(chatNameEntity);
						newChatAdapter.notifyDataSetChanged();
					}
				})

				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogBox, int id) {
								dialogBox.cancel();
							}
						});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}


	@Override
	public void onRecyclerViewItemClick(int position) {
		NewChatEntity chatNameEntity = newChatAdapter.getItem(position);
		Intent in = new Intent(HomeScreenActivity.this, ChatScreenActivity.class);
		in.putExtra("name", chatNameEntity.getName());
		in.putExtra("id", chatNameEntity.getNameID());
		startActivity(in);
	}

	/**
	 * Check if there is any data to display from local db @{@link com.ss.chatbot.db.Database}
	 * on the launch of @{@link HomeScreenActivity}
	 */
	private void setDataOnLaunch() {
		ArrayList<NewChatEntity> chatNameEntities = new ArrayList<>();
		chatNameEntities.addAll(chatNameViewModel.getChatsWithoutLiveData());
		if (chatNameEntities.size() == 0) {
			mlayoutNothingToShow.setVisibility(View.VISIBLE);
		} else {
			mlayoutNothingToShow.setVisibility(View.INVISIBLE);
			newChatAdapter.addAll(chatNameEntities);
		}
	}
}
