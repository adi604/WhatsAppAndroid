package com.example.whatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatapp.R;
import com.example.whatapp.entities.Message;

import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder> {

    class MessageViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvContent;
        private final TextView tvTime;

        private MessageViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }

    private final LayoutInflater mInflater;
    private List<Message> messages;

    public MessagesListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public int getItemViewType(int position) {
        final Message m = messages.get(position);
        if (m.isSent()) {
            return 0;
        }
        return 1;
    }

    @Override
    public MessagesListAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 1) {
            itemView = mInflater.inflate(R.layout.message_item_sent, parent, false);
        }
        else {
            itemView = mInflater.inflate(R.layout.message_item_receive, parent, false);
        }
        return new MessagesListAdapter.MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessagesListAdapter.MessageViewHolder holder, int position) {
        if (messages != null) {
            final Message current = messages.get(position);
            holder.tvContent.setText(current.getContent());
            holder.tvTime.setText(current.getTime());
        }
    }

    public void setMessages(List<Message> l) {
        messages = l;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
