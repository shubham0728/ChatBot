package com.ss.chatbot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ss.chatbot.R;
import com.ss.chatbot.interfaces.RecyclerViewItemClickListener;
import com.ss.chatbot.model.ChatEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	// Identifier for sent messages.
	private static final int MESSAGE_SENT = 0;

	// Identifier for received messages.
	private static final int MESSAGE_RECEIVED = 1;

	private Context mCtx;
	private RecyclerViewItemClickListener mrecyclerViewItemClickListener;
	private List<ChatEntity> chatEntities;

	/**
	 * Constructor
	 *
	 * @param mCtx
	 * @param mrecyclerViewItemClickListener
	 */
	public ChatAdapter(Context mCtx, RecyclerViewItemClickListener mrecyclerViewItemClickListener) {
		this.mCtx = mCtx;
		this.mrecyclerViewItemClickListener = mrecyclerViewItemClickListener;
		chatEntities = new ArrayList<>();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder viewHolder = null;
		switch (viewType) {
			case MESSAGE_SENT:
				View sentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sent_message, parent, false);
				viewHolder = new SentViewHolder(sentView);
				break;
			case MESSAGE_RECEIVED:
				View receivedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_received_message, parent, false);
				viewHolder = new ReceivedViewHolder(receivedView);
				break;
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

		ChatEntity chatEntity = chatEntities.get(position);
		switch (holder.getItemViewType()) {
			case MESSAGE_SENT:
				((SentViewHolder) holder).setData(chatEntity);
				break;

			case MESSAGE_RECEIVED:
				((ReceivedViewHolder) holder).setData(chatEntity);
				break;
		}

	}

	@Override
	public int getItemCount() {
		return chatEntities.size();
	}

	@Override
	public int getItemViewType(int position) {
		ChatEntity chatEntity = chatEntities.get(position);
		if (chatEntity.getType().equalsIgnoreCase("Sender"))
			return MESSAGE_SENT;
		else
			return MESSAGE_RECEIVED;

	}

	/**
	 * Add single entity from api call.
	 *
	 * @param chatEntity
	 */
	public void add(ChatEntity chatEntity) {
		chatEntities.add(chatEntity);
		notifyItemInserted(chatEntities.size() - 1);
	}

	/**
	 * Add all data from the api call.
	 *
	 * @param chatEntities
	 */
	public void addAll(List<ChatEntity> chatEntities) {
		for (ChatEntity result : chatEntities) {
			add(result);
		}
	}

	/**
	 * Remove entity from list
	 *
	 * @param chatEntity
	 */
	public void remove(ChatEntity chatEntity) {
		int position = chatEntities.indexOf(chatEntity);
		if (position > -1) {
			chatEntities.remove(position);
			notifyItemRemoved(position);
		}
	}


	public boolean isEmpty() {
		return getItemCount() == 0;
	}


	public ChatEntity getItem(int position) {
		return chatEntities.get(position);
	}

	public List<ChatEntity> getAll() {
		return chatEntities;
	}


	/**
	 * Holder class For Sent Messages
	 */
	public class SentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView txt_message_body_sent;
		private TextView txt_message_time;

		public SentViewHolder(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			txt_message_body_sent = itemView.findViewById(R.id.text_message_body_sent);
			txt_message_time = itemView.findViewById(R.id.text_message_time);
		}

		@Override
		public void onClick(View view) {
			mrecyclerViewItemClickListener.onRecyclerViewItemClick(getAdapterPosition());
		}

		/**
		 * Set Data to UI elements of Sent Holder class.
		 *
		 * @param entity
		 */
		public void setData(ChatEntity entity) {
			txt_message_body_sent.setText(entity.getMessage());
			txt_message_time.setText(entity.getDateTime());
		}

	}

	/**
	 * Holder class for Received Messages.
	 */
	public class ReceivedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView txt_messageReceived;
		private TextView txt_sender_name;
		private TextView txt_message_time;

		public ReceivedViewHolder(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			txt_messageReceived = itemView.findViewById(R.id.text_message_body);
			txt_sender_name = itemView.findViewById(R.id.text_sender_name);
			txt_message_time = itemView.findViewById(R.id.text_message_time);
		}

		@Override
		public void onClick(View view) {
			mrecyclerViewItemClickListener.onRecyclerViewItemClick(getAdapterPosition());
		}

		/**
		 * Set Data to UI elements of Received Holder class.
		 *
		 * @param entity
		 */
		public void setData(ChatEntity entity) {
			txt_sender_name.setText(entity.getChatBotName());
			txt_messageReceived.setText(entity.getMessage());
			txt_message_time.setText(entity.getDateTime());
		}

	}


}
