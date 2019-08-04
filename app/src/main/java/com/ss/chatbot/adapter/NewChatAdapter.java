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
import com.ss.chatbot.model.NewChatEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham Singhal
 */
public class NewChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mCtx;
	private RecyclerViewItemClickListener mrecyclerViewItemClickListener;
	private List<NewChatEntity> chatNameEntities;

	/**
	 * Constructor
	 *
	 * @param mCtx
	 * @param mrecyclerViewItemClickListener
	 */
	public NewChatAdapter(Context mCtx, RecyclerViewItemClickListener mrecyclerViewItemClickListener) {
		this.mCtx = mCtx;
		this.mrecyclerViewItemClickListener = mrecyclerViewItemClickListener;
		chatNameEntities = new ArrayList<>();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder viewHolder = null;
		View nameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_chat_adapter, parent, false);
		viewHolder = new NameViewHolder(nameView);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		NewChatEntity chatNameEntity = chatNameEntities.get(position);
		((NameViewHolder) holder).setData(chatNameEntity);
	}

	@Override
	public int getItemCount() {
		return chatNameEntities.size();
	}

	/**
	 * Add single entity from api call.
	 *
	 * @param chatNameEntity
	 */
	public void add(NewChatEntity chatNameEntity) {
		chatNameEntities.add(chatNameEntity);
		notifyItemInserted(chatNameEntities.size() - 1);
	}

	/**
	 * Add all data from the api call.
	 *
	 * @param chatNameEntities
	 */
	public void addAll(List<NewChatEntity> chatNameEntities) {
		for (NewChatEntity result : chatNameEntities) {
			add(result);
		}
	}

	/**
	 * Remove entity from list
	 *
	 * @param chatNameEntity
	 */
	public void remove(NewChatEntity chatNameEntity) {
		int position = chatNameEntities.indexOf(chatNameEntity);
		if (position > -1) {
			chatNameEntities.remove(position);
			notifyItemRemoved(position);
		}
	}

	public NewChatEntity getItem(int position) {
		return chatNameEntities.get(position);
	}


	public boolean isEmpty() {
		return getItemCount() == 0;
	}

	/**
	 * Chat Name view Holder.
	 */
	public class NameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView txt_name;

		public NameViewHolder(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			txt_name = itemView.findViewById(R.id.txt_name);
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
		public void setData(NewChatEntity entity) {
			txt_name.setText(entity.getName());
		}

	}
}
