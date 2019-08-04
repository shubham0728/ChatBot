package com.ss.chatbot.respository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ss.chatbot.model.ChatEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class ChatViewModel extends AndroidViewModel {
	private ChatRepository chatRepository;
	private LiveData<List<ChatEntity>> listLiveData;
	private List<ChatEntity> chatEntities;

	public ChatViewModel(@NonNull Application application) {
		super(application);
		chatRepository = new ChatRepository(application);
		listLiveData = chatRepository.getAllChats();
		chatEntities = chatRepository.getChats();
	}

	/*
	Get observable data from db.
	 */
	public LiveData<List<ChatEntity>> getAllChats() {
		return listLiveData;
	}

	/**
	 * Return non-obersvable data.
	 *
	 * @return
	 */
	public List<ChatEntity> getChatsWithoutLiveData() {
		return chatEntities;
	}

	/**
	 * Fetch chats for specified name ID
	 *
	 * @param nameID
	 * @return
	 */
	public List<ChatEntity> loadChatsWithNameID(String nameID) {
		return chatRepository.loadChatsWithNameID(nameID);
	}

	/**
	 * Fetch chats with sync status as False
	 *
	 * @param status
	 * @param nameID
	 * @return
	 */
	public List<ChatEntity> loadChatsWithSyncStatus(boolean status, String nameID) {
		return chatRepository.loadChatsWithSyncStatus(status, nameID);
	}

	/**
	 * Insert data in local db.
	 *
	 * @param chatEntity
	 */
	public void insert(ChatEntity chatEntity) {
		chatRepository.insert(chatEntity);
	}


	/**
	 * Update data in local db.
	 *
	 * @param chatEntity
	 */
	public void updateSource(ChatEntity chatEntity) {
		chatRepository.updateSource(chatEntity);
	}


	/**
	 * Delete complete table
	 */
	public void deleteAll() {
		chatRepository.deleteAll();
	}
}
