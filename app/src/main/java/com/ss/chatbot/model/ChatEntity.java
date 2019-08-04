package com.ss.chatbot.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shubham Singhal
 */

@Entity(tableName = "chat_table")
public class ChatEntity implements Serializable {

	@NonNull
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	private int id;

	@SerializedName("message")
	@ColumnInfo(name = "message")
	@Expose
	private String message;

	@SerializedName("chatBotName")
	@ColumnInfo(name = "chatBotName")
	@Expose
	private String chatBotName;

	@SerializedName("chatBotID")
	@ColumnInfo(name = "chatBotID")
	@Expose
	private String chatBotID;

	@ColumnInfo(name = "type")
	private String type;

	@ColumnInfo(name = "nameID")
	private String nameID;

	@ColumnInfo(name = "senderName")
	private String senderName;

	@ColumnInfo(name = "isSynced")
	private boolean isSynced;

	@ColumnInfo(name = "dateTime")
	private String dateTime;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getChatBotName() {
		return chatBotName;
	}

	public void setChatBotName(String chatBotName) {
		this.chatBotName = chatBotName;
	}

	public String getChatBotID() {
		return chatBotID;
	}

	public void setChatBotID(String chatBotID) {
		this.chatBotID = chatBotID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNameID() {
		return nameID;
	}

	public void setNameID(String nameID) {
		this.nameID = nameID;
	}

	public boolean isSynced() {
		return isSynced;
	}

	public void setSynced(boolean synced) {
		isSynced = synced;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
