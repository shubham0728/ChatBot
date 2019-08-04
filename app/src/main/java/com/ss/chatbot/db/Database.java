package com.ss.chatbot.db;

import android.content.Context;


import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ss.chatbot.model.ChatEntity;
import com.ss.chatbot.model.NewChatEntity;

/**
 * Created by Shubham Singhal
 */
@androidx.room.Database(entities = {ChatEntity.class, NewChatEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DataTypeConverterChat.class)
public abstract class Database extends RoomDatabase {

	public abstract ChatDao chatDao();

	public abstract NewChatDao newChatDao();

	private static Database INSTANCE;
	// Name of the database
	public static final String DATABASE_NAME = "chat_database";

	/**
	 * Database Instance.
	 *
	 * @param context
	 * @return
	 */
	public static Database getDatabase(final Context context) {
		if (INSTANCE == null) {
			synchronized (Database.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
							Database.class, DATABASE_NAME)
							.fallbackToDestructiveMigration()
							.allowMainThreadQueries()
							.build();
				}
			}
		}
		return INSTANCE;
	}
}
