package com.example.whatapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatapp.App;
import com.example.whatapp.api.ContactAPI;
import com.example.whatapp.database.ContactDao;
import com.example.whatapp.database.LocalDatabase;
import com.example.whatapp.entities.Contact;

import java.util.LinkedList;
import java.util.List;

public class ContactsRepository {
    private String userId;
    private String token;
    private ContactDao dao;
    private LocalDatabase db;
    private ContactAPI api;
    private ContactListData contactListData;

    public ContactsRepository(String token, String userId) {
        this.userId = userId;
        this.token = token;
        this.db = Room.databaseBuilder(App.getContext(), LocalDatabase.class, "DB")
                  .allowMainThreadQueries().build();
        this.dao = db.contactDao();
        this.contactListData = new ContactListData();
        this.api = new ContactAPI();
    }

    class ContactListData extends MutableLiveData<List<Contact>> {

        public ContactListData() {
            super();
            super.setValue(new LinkedList<Contact>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                contactListData.postValue(dao.getAllContacts(userId));
            }).start();
            api.getAllContacts(token, this);
        }
    }

    public LiveData<List<Contact>> getAllContacts() {
        return contactListData;
    }
}
