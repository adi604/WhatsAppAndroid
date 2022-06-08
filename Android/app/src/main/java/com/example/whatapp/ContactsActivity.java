package com.example.whatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatapp.adapters.ContactsListAdapter;
import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.User;
import com.example.whatapp.viewModels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactsActivity extends AppCompatActivity {

    private User currentUser;
    private List<Contact> contacts;
    private ContactsViewModel viewModel;
    private ContactsListAdapter adapter;
    private FloatingActionButton btnAdd;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().hide();

        // gets the current user that logged in
        String userJson = getIntent().getStringExtra("current-user");
        currentUser = new Gson().fromJson(userJson, User.class);

        // create recyclerview and adapter for contact
        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        adapter = new ContactsListAdapter(this);
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));

        // creates the contact viewModel and pass the JWT token to the viewmodel
        viewModel = new ContactsViewModel(getIntent().getStringExtra("token"), currentUser.getId());
        viewModel.getAllContacts().observe(this, contacts -> {
            adapter.setContacts(contacts);
            adapter.notifyDataSetChanged();
        });

        EditText search = findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            Dialog dialog = new Dialog(ContactsActivity.this);
            dialog.setContentView(R.layout.activity_add_contact);
            EditText edtName = dialog.findViewById(R.id.name);
            EditText edtServer = dialog.findViewById(R.id.server);
            Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

            btnUpdate.setOnClickListener(v2 -> {
                String name = edtName.getText().toString();
                String server = edtServer.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(ContactsActivity.this, "Please Enter Contact Name!", Toast.LENGTH_SHORT).show();
                }

                if (server.equals("")) {
                    Toast.makeText(ContactsActivity.this, "Please Enter Server!", Toast.LENGTH_SHORT).show();
                }

                contacts.add(new Contact(name, "", R.drawable.background));
                adapter.setContacts(contacts);

                dialog.dismiss();
            });
            dialog.show();
        });

    }

    private void filter(String text) {
        ArrayList<Contact> filteredList = new ArrayList<>();
        text = text.toLowerCase(Locale.ROOT);
        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase(Locale.ROOT).contains(text)) {
                filteredList.add(contact);
            }
        }

        adapter.setContacts(filteredList);
    }
}