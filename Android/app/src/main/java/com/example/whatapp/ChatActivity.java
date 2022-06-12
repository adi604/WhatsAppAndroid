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
import com.example.whatapp.entities.User;
import com.example.whatapp.viewModels.ContactsViewModel;
import com.example.whatapp.viewModels.MessagesViewModal;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private String currentUser;
    private String contactUser;
    private List<Message> messages;
    private MessagesViewModal viewModel;
    private MessagesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        String name_contact = getIntent().getStringExtra("name_contact");
        setName(name_contact);

        // gets the current user that logged in
        currentUser = getIntent().getStringExtra("userId");
        contactUser = getIntent().getStringExtra("contactId");

        RecyclerView lstMessages = findViewById(R.id.lstMessages);
        adapter = new MessagesListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        messages = new ArrayList<>();
        messages.add(new Message(true, "hey adi"));
        messages.add(new Message(false, "hey eyal"));
        messages.add(new Message(false, "hey bar"));
        messages.add(new Message(true, "hey foo"));
        adapter.setMessages(messages);

//        // creates the message viewModel and pass the JWT token to the viewmodel
//        viewModel = new MessagesViewModal(getIntent().getStringExtra("token"), currentUser, contactUser);
//        viewModel.getAllMessages().observe(this, messages -> {
//            adapter.setMessages(messages);
//            this.messages = messages;
//        });

    }

    private void setName(String name_contact) {
        TextView nameContact = findViewById(R.id.nameContact);
        nameContact.setText(name_contact);
    }
}