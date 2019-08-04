package com.ss.chatbot.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.ss.chatbot.db.DataTypeConverterChat;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shubham Singhal
 */

@Entity(tableName = "chat_name_table")
public class NewChatEntity implements Serializable {


	@NonNull
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	private int id;

	@ColumnInfo(name = "name")
	private String name;

	@ColumnInfo(name = "nameID")
	private String nameID;

	@TypeConverters(DataTypeConverterChat.class)
	@ColumnInfo(name = "chatEntities")
	private List<ChatEntity> chatEntities;

	public NewChatEntity() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameID() {
		return nameID;
	}

	public void setNameID(String nameID) {
		this.nameID = nameID;
	}

	public List<ChatEntity> getChatEntities() {
		return chatEntities;
	}

	public void setChatEntities(List<ChatEntity> chatEntities) {
		this.chatEntities = chatEntities;
	}
}
