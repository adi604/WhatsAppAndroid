package com.example.whatapp.viewModels;

import androidx.lifecycle.LiveData;

import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Message;
import com.example.whatapp.repositories.ContactsRepository;
import com.example.whatapp.repositories.MessageRepository;

import java.util.List;

public class MessagesViewModal {
    private MessageRepository mRepository;
    private LiveData<List<Message>> messages;

    public MessagesViewModal (String token, String userId, String contactId) {
        mRepository = new MessageRepository(token, userId, contactId);
        messages = mRepository.getAllMessages();
    }

    public LiveData<List<Message>> getAllMessages() { return messages;}

}
