package com.example.whatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.whatapp.R;
import com.example.whatapp.adapters.ContactsListAdapter;
import com.example.whatapp.adapters.MessagesListAdapter;
import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    List<Message> list;
    MessagesListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        String name_contact = getIntent().getStringExtra("name_contact");
        setName(name_contact);

        RecyclerView lstMessages = findViewById(R.id.lstMessages);
        adapter = new MessagesListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        list.add(new Message(true, "hey adi"));
        list.add(new Message(false, "hey eyal"));
        list.add(new Message(false, "hey bar"));
        list.add(new Message(true, "hey foo"));
        adapter.setContacts(list);

    }

    private void setName(String name_contact) {
        TextView nameContact = findViewById(R.id.nameContact);
        nameContact.setText(name_contact);
    }
}