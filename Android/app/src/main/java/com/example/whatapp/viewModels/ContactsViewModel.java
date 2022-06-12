package com.example.whatapp.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.whatapp.entities.Contact;
import com.example.whatapp.repositories.ContactsRepository;

import java.util.List;
import java.util.Objects;

public class ContactsViewModel extends ViewModel {

    private ContactsRepository mRepository;
    private LiveData<List<Contact>> contacts;

    public ContactsViewModel (String token, String userId) {
        mRepository = new ContactsRepository(token, userId);
        contacts = mRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() { return contacts;}


    public void add(String userId, String contactId, String contactName, String server) {
        mRepository.add(userId,contactId, contactName,server);
    }

    //public void reload() {mRepository.reload();}
}
