package com.ss.chatbot.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ss.chatbot.model.ChatEntity;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by Shubham Singhal
 */

@Dao
public interface ChatDao {

	/**
	 * Returns observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM chat_table")
	LiveData<List<ChatEntity>> loadAllChats();

	/**
	 * Returns non-observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM chat_table")
	List<ChatEntity> getAllChats();

	/**
	 * Returns single entity based on id.
	 *
	 * @param id
	 * @return
	 */
	@Query("SELECT * FROM chat_table WHERE id like :id")
	ChatEntity getChatsByID(int id);

	/**
	 * Returns single entity based on id.
	 *
	 * @param nameID
	 * @return
	 */
	@Query("SELECT * FROM chat_table WHERE nameID like :nameID")
	List<ChatEntity> getChatsByNameID(String nameID);

	/**
	 * Returns single entity based on sync status.
	 *
	 * @param isSynced
	 * @return
	 */
	@Query("SELECT * FROM chat_table WHERE isSynced like :isSynced and nameID like:nameID")
	List<ChatEntity> getChatsBySyncStatus(boolean isSynced, String nameID);

	/**
	 * Insert single entity in local db.
	 *
	 * @param chatEntity
	 */
	@Insert
	void insert(ChatEntity chatEntity);

	/**
	 * Delete all data from local db.
	 */
	@Query("Delete from chat_table")
	void deleteAll();

	/**
	 * Update data for single entity.
	 *
	 * @param chatEntity
	 */
	@Update(onConflict = REPLACE)
	void update(ChatEntity chatEntity);
}
