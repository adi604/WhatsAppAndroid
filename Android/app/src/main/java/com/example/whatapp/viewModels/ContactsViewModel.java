package com.example.whatapp.viewModels;

import androidx.lifecycle.LiveData;

import com.example.whatapp.entities.Contact;
import com.example.whatapp.repositories.ContactsRepository;

import java.util.List;

public class ContactsViewModel {

    private ContactsRepository mRepository;
    private LiveData<List<Contact>> contacts;

    public ContactsViewModel (String token, String userId) {
        mRepository = new ContactsRepository(token, userId);
        contacts = mRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() { return contacts;}

    //public void add(Contact contact) {mRepository.add(contact);}

    //public void reload() {mRepository.reload();}
}