package com.ss.chatbot.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.ss.chatbot.model.NewChatEntity;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * Created by Shubham Singhal
 */

@Dao
public interface NewChatDao {

	/**
	 * Returns observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM chat_name_table")
	LiveData<List<NewChatEntity>> loadAllChatsWithName();

	/**
	 * Returns non-observable data from local db.
	 *
	 * @return
	 */
	@Query("SELECT * FROM chat_name_table")
	List<NewChatEntity> getAllChatsName();

	/**
	 * Returns single entity based on id.
	 *
	 * @param nameID
	 * @return
	 */
	@Query("SELECT * FROM chat_name_table WHERE nameID like :nameID")
	NewChatEntity getChatsByNameID(int nameID);


	/**
	 * Insert single entity in local db.
	 *
	 * @param chatNameEntity
	 */
	@Insert
	void insert(NewChatEntity chatNameEntity);

	/**
	 * Delete all data from local db.
	 */
	@Query("Delete from chat_name_table")
	void deleteAll();

	/**
	 * Update data for single entity.
	 *
	 * @param chatNameEntity
	 */
	@Update(onConflict = REPLACE)
	void update(NewChatEntity chatNameEntity);
}
