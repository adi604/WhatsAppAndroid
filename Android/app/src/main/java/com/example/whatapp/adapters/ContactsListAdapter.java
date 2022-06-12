package com.example.whatapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatapp.R;
import com.example.whatapp.entities.Contact;
import com.example.whatapp.ChatActivity;
import com.example.whatapp.entities.User;
import com.google.gson.Gson;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUserName;
        private final TextView tvLMessageT;
        private final TextView tvLMessageC;
        private final CircleImageView ivImage;
        private ConstraintLayout cl;

        private ContactViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvLMessageT = itemView.findViewById(R.id.tvLMessageT);
            tvLMessageC = itemView.findViewById(R.id.tvLMessageC);
            cl = itemView.findViewById(R.id.contact);
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> contacts;
    private Context mContext;
    private User user;
    private String token;

    public ContactsListAdapter(Context context, User user, String token) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.user = user;
        this.token = token;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if (contacts != null) {
            final Contact current = contacts.get(position);
            holder.tvUserName.setText(current.getName());
            holder.ivImage.setImageResource(current.getImage());
            holder.tvLMessageT.setText(current.getLastDate());
            holder.tvLMessageC.setText(current.getLast());

            holder.cl.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ChatActivity.class);
                String contactJson = new Gson().toJson(current);
                String userJson = new Gson().toJson(user);
                intent.putExtra("token", this.token);
                intent.putExtra("current-user", userJson);
                intent.putExtra("current-contact", contactJson);
                mContext.startActivity(intent);
            });
        }


    }

    public void setContacts(List<Contact> l) {
        contacts = l;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        }
        return 0;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

}
