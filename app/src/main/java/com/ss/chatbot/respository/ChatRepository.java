package com.ss.chatbot.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ss.chatbot.db.ChatDao;
import com.ss.chatbot.db.Database;
import com.ss.chatbot.model.ChatEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class ChatRepository {

	private ChatDao chatDao;
	private LiveData<List<ChatEntity>> listLiveData;
	private List<ChatEntity> chatEntities;
	private ChatEntity chatEntity;
	private Database db;

	/**
	 * Constructor
	 *
	 * @param ctx
	 */
	public ChatRepository(Context ctx) {
		db = Database.getDatabase(ctx);
		chatDao = db.chatDao();
		listLiveData = chatDao.loadAllChats();
		chatEntities = chatDao.getAllChats();
	}

	/*
	Get observable data from db.
	 */
	LiveData<List<ChatEntity>> getAllChats() {
		return listLiveData;
	}

	/**
	 * Return non-obersvable data.
	 *
	 * @return
	 */
	List<ChatEntity> getChats() {
		return chatEntities;
	}

	/**
	 * Fetch chats for specified name ID
	 *
	 * @param nameID
	 * @return
	 */
	public List<ChatEntity> loadChatsWithNameID(final String nameID) {
		if (chatDao.getChatsByNameID(nameID) != null) {
			chatEntities = chatDao.getChatsByNameID(nameID);
		}
		return chatEntities;
	}

	/**
	 * Fetch chats with sync status as False
	 *
	 * @param status
	 * @param nameID
	 * @return
	 */
	public List<ChatEntity> loadChatsWithSyncStatus(final boolean status, String nameID) {
		if (chatDao.getChatsBySyncStatus(status, nameID) != null) {
			chatEntities = chatDao.getChatsBySyncStatus(status, nameID);
		}
		return chatEntities;
	}


	/**
	 * Insert data in local db using Asyc Task.
	 *
	 * @param chatEntity
	 */
	public void insert(ChatEntity chatEntity) {
		new insertAsyncTask(chatDao).execute(chatEntity);
	}

	private static class insertAsyncTask extends AsyncTask<ChatEntity, Void, Void> {

		private ChatDao mAsyncTaskDao;

		insertAsyncTask(ChatDao dao) {
			mAsyncTaskDao = dao;
		}


		@Override
		protected Void doInBackground(ChatEntity... chatEntities) {
			try {
				mAsyncTaskDao.insert(chatEntities[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Update data in local db using Async task.
	 *
	 * @param chatEntity
	 */
	public void updateSource(ChatEntity chatEntity) {
		new UpdateAsyncTask(chatDao).execute(chatEntity);
	}

	private static class UpdateAsyncTask extends AsyncTask<ChatEntity, Void, Void> {

		ChatDao sourcesDao;

		public UpdateAsyncTask(ChatDao _sourcesDao) {
			this.sourcesDao = _sourcesDao;
		}

		@Override
		protected Void doInBackground(ChatEntity... chatEntities) {
			try {
				sourcesDao.update(chatEntities[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Delete all data from table.
	 */
	public void deleteAll() {
		if (chatDao != null) {
			chatDao.deleteAll();
		}
	}

}
