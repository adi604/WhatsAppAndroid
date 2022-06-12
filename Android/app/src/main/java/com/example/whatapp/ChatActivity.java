package com.example.whatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private User currentUser;
    private Contact currentContact;
    private List<Message> messages;
    private MessagesViewModal viewModel;
    private MessagesListAdapter adapter;
    private Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        sendBtn = findViewById(R.id.button_send);

        // gets the current user that logged in and the current contact
        currentUser = new Gson().fromJson(getIntent().getStringExtra("current-user"), User.class);
        currentContact = new Gson().fromJson(getIntent().getStringExtra("current-contact"), Contact.class);
        String token = getIntent().getStringExtra("token");

        this.viewModel = new MessagesViewModal(token, currentUser, currentContact);

        //sets the name of the contact
        String name_contact = currentContact.getName();
        setName(name_contact);

        // sets the recyclerview and the messages adapter
        RecyclerView lstMessages = findViewById(R.id.lstMessages);
        adapter = new MessagesListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        // creates the message viewModel and pass the JWT token to the viewmodel
        viewModel = new MessagesViewModal(token, currentUser, currentContact);
        viewModel.getAllMessages().observe(this, messages -> {
            adapter.setMessages(messages);
            this.messages = messages;
        });

        // function to be called when the user wants to send a message
        sendBtn.setOnClickListener(v -> {
            EditText etMessage = findViewById(R.id.edit_message);
            String messageContent = etMessage.getText().toString();
            // if the message is empty then return
            if (messageContent.isEmpty()) {
                return;
            }
            this.viewModel.sendMessage(messageContent);
            etMessage.setText("");
        });

    }

    private void setName(String name_contact) {
        TextView nameContact = findViewById(R.id.nameContact);
        nameContact.setText(name_contact);
    }
}