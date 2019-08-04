package com.ss.chatbot.respository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ss.chatbot.model.NewChatEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class NewChatViewModel extends AndroidViewModel {
	private NewChatRepository chatNameRepository;
	private LiveData<List<NewChatEntity>> listLiveData;
	private List<NewChatEntity> chatNameEntities;

	/**
	 * Constructor
	 *
	 * @param application
	 */
	public NewChatViewModel(@NonNull Application application) {
		super(application);
		chatNameRepository = new NewChatRepository(application);
		listLiveData = chatNameRepository.getAllChats();
		chatNameEntities = chatNameRepository.getChats();
	}

	/*
	Get observable data from db.
	 */
	public LiveData<List<NewChatEntity>> getAllChats() {
		return listLiveData;
	}

	/**
	 * Return non-obersvable data.
	 *
	 * @return
	 */
	public List<NewChatEntity> getChatsWithoutLiveData() {
		return chatNameEntities;
	}


	/**
	 * Insert data in local db using Asyc Task.
	 *
	 * @param chatNameEntity
	 */
	public void insert(NewChatEntity chatNameEntity) {
		chatNameRepository.insert(chatNameEntity);
	}

	/**
	 * Update data in local db using Async task.
	 *
	 * @param chatNameEntity
	 */
	public void updateSource(NewChatEntity chatNameEntity) {
		chatNameRepository.updateSource(chatNameEntity);
	}


	/**
	 * Delete complete table
	 */
	public void deleteAll() {
		chatNameRepository.deleteAll();
	}
}
