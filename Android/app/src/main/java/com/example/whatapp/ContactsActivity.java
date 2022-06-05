package com.example.whatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.whatapp.adapters.ContactsListAdapter;
import com.example.whatapp.entities.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        final ContactsListAdapter adapter = new ContactsListAdapter(this);
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));

        List<Contact> list = new ArrayList<>();
        list.add(new Contact("Adi Aviv", R.drawable.background));
        list.add(new Contact("guy Ben Razon", R.drawable.background));
        list.add(new Contact("Eran Haim", R.drawable.background));
        list.add(new Contact("Or Aviv", R.drawable.background));
        list.add(new Contact("Adi Aviv", R.drawable.background));
        list.add(new Contact("guy Ben Razon", R.drawable.background));
        list.add(new Contact("Eran Haim", R.drawable.background));
        list.add(new Contact("Or Aviv", R.drawable.background));
        list.add(new Contact("Adi Aviv", R.drawable.background));
        list.add(new Contact("guy Ben Razon", R.drawable.background));
        list.add(new Contact("Eran Haim", R.drawable.background));
        list.add(new Contact("Or Aviv", R.drawable.background));
        adapter.setContacts(list);

    }
}