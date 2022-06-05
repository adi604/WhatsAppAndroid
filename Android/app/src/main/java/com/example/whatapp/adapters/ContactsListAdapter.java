package com.example.whatapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.whatapp.R;
import com.example.whatapp.entities.Contact;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder> {

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUserName;
        private final TextView tvLMessageT;
        private final TextView tvLMessageC;
        private final CircleImageView ivImage;

        private ContactViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvLMessageT = itemView.findViewById(R.id.tvLMessageT);
            tvLMessageC = itemView.findViewById(R.id.tvLMessageC);
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> contacts;

    public ContactsListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if (contacts != null) {
            final Contact current = contacts.get(position);
            holder.tvUserName.setText(current.getUserName());
            holder.ivImage.setImageResource(current.getImage());
            holder.tvLMessageT.setText(current.getLastMessageT());
            holder.tvLMessageC.setText(current.getLastMessageC());
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
