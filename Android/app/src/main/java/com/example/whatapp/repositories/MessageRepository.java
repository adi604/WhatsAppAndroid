package com.example.whatapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatapp.App;
import com.example.whatapp.api.ContactAPI;
import com.example.whatapp.api.MessageAPI;
import com.example.whatapp.database.ContactDao;
import com.example.whatapp.database.LocalDatabase;
import com.example.whatapp.database.MessageDao;
import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Message;

import java.util.List;

public class MessageRepository {
    private String userId;
    private String contactId;
    private String token;
    private MessageDao dao;
    private LocalDatabase db;
    private MessageAPI api;
    private MessageRepository.MessageListData messageListData;

    public MessageRepository(String token, String userId, String contactId) {
        this.userId = userId;
        this.contactId = contactId;
        this.token = token;
        this.db = Room.databaseBuilder(App.getContext(), LocalDatabase.class, "DB")
                .allowMainThreadQueries().build();
        this.dao = db.messageDao();
        this.messageListData = new MessageRepository.MessageListData();
        this.api = new MessageAPI(contactId);
    }

    class MessageListData extends MutableLiveData<List<Message>> {

        public MessageListData() {
            super();
        }

        @Override
        protected void onActive() {
            super.onActive();
//            Thread t = new Thread(() -> {
//                //messageListData.postValue();
//            });
//            t.start();
//            try {
//                t.join();
//            }
//            catch(InterruptedException e) {
//                e.printStackTrace();
//            }
            api.getAllMessages(token, this);
        }
    }

    public MessageRepository.MessageListData getMessageListData() {
        return messageListData;
    }

    public LiveData<List<Message>> getAllMessages() {
        return messageListData;
    }

}
