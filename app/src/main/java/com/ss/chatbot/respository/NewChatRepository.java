package com.ss.chatbot.respository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ss.chatbot.db.Database;
import com.ss.chatbot.db.NewChatDao;
import com.ss.chatbot.model.NewChatEntity;

import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class NewChatRepository {

	private NewChatDao newChatDao;
	private LiveData<List<NewChatEntity>> listLiveData;
	private List<NewChatEntity> chatNameEntities;
	private NewChatEntity chatNameEntity;
	private Database db;

	/**
	 * Constructor
	 *
	 * @param ctx
	 */
	public NewChatRepository(Context ctx) {
		db = Database.getDatabase(ctx);
		newChatDao = db.newChatDao();
		listLiveData = newChatDao.loadAllChatsWithName();
		chatNameEntities = newChatDao.getAllChatsName();
	}

	/*
	Get observable data from db.
	 */
	LiveData<List<NewChatEntity>> getAllChats() {
		return listLiveData;
	}

	/**
	 * Return non-obersvable data.
	 *
	 * @return
	 */
	List<NewChatEntity> getChats() {
		return chatNameEntities;
	}

	/**
	 * Insert data in local db using Asyc Task.
	 *
	 * @param chatNameEntity
	 */
	public void insert(NewChatEntity chatNameEntity) {
		new insertAsyncTask(newChatDao).execute(chatNameEntity);
	}

	private static class insertAsyncTask extends AsyncTask<NewChatEntity, Void, Void> {

		private NewChatDao mAsyncTaskDao;

		insertAsyncTask(NewChatDao dao) {
			mAsyncTaskDao = dao;
		}


		@Override
		protected Void doInBackground(NewChatEntity... chatNameEntities) {
			try {
				mAsyncTaskDao.insert(chatNameEntities[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Update data in local db using Async task.
	 *
	 * @param chatNameEntity
	 */
	public void updateSource(NewChatEntity chatNameEntity) {
		new UpdateAsyncTask(newChatDao).execute(chatNameEntity);
	}

	private static class UpdateAsyncTask extends AsyncTask<NewChatEntity, Void, Void> {

		NewChatDao sourcesDao;

		public UpdateAsyncTask(NewChatDao _sourcesDao) {
			this.sourcesDao = _sourcesDao;
		}

		@Override
		protected Void doInBackground(NewChatEntity... chatNameEntities) {
			try {
				sourcesDao.update(chatNameEntities[0]);
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
		if (newChatDao != null) {
			newChatDao.deleteAll();
		}
	}
}
