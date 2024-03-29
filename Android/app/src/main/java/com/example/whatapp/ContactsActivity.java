package com.example.whatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatapp.adapters.ContactsListAdapter;
import com.example.whatapp.api.UserAPI;
import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Lambda;
import com.example.whatapp.entities.User;
import com.example.whatapp.viewModels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsActivity extends AppCompatActivity {

    private User currentUser;
    private List<Contact> contacts;
    private ContactsViewModel viewModel;
    private ContactsListAdapter adapter;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnSetting;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getSupportActionBar().hide();

        // gets the current user that logged in
        String userJson = getIntent().getStringExtra("current-user");
        currentUser = new Gson().fromJson(userJson, User.class);
        //set user name in heading
        setName(currentUser.getName());
        //set profile image for user
        CircleImageView profileImage = findViewById(R.id.ivImage);
        byte[] bytes = Base64.decode(currentUser.getImage(), Base64.DEFAULT);
        profileImage.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0 , bytes.length));

        // create recyclerview and adapter for contact
        RecyclerView lstContacts = findViewById(R.id.lstContacts);
        adapter = new ContactsListAdapter(this, currentUser, getIntent().getStringExtra("token"));
        lstContacts.setAdapter(adapter);
        lstContacts.setLayoutManager(new LinearLayoutManager(this));

        // creates the contact viewModel and pass the JWT token to the viewmodel
        viewModel = new ContactsViewModel(getIntent().getStringExtra("token"), currentUser.getId());
        viewModel.getAllContacts().observe(this, contacts -> {
            adapter.setContacts(contacts);
            this.contacts = contacts;
        });

        // on search filter the list contact based on the search text
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

        // on adding a new contact
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Dialog dialog = new Dialog(ContactsActivity.this);
            dialog.setContentView(R.layout.activity_add_contact);
            EditText edtUsername = dialog.findViewById(R.id.username);
            EditText edtNickname = dialog.findViewById(R.id.nickname);
            EditText edtServer = dialog.findViewById(R.id.server);
            Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

            btnUpdate.setOnClickListener(v2 -> {
                String username = edtUsername.getText().toString();
                String nickname = edtNickname.getText().toString();
                String server = edtServer.getText().toString();
                if (!isValid(username, nickname, server)) {
                    return;
                }
                viewModel.add(currentUser.getId(), username, nickname, server);
                dialog.dismiss();
            });
            dialog.show();
        });

        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(v -> {
            Dialog dialog = new Dialog(ContactsActivity.this);
            dialog.setContentView(R.layout.activity_setting);
            EditText newServer = dialog.findViewById(R.id.newServer);
            Button btnSave = dialog.findViewById(R.id.btnSave);

            btnSave.setOnClickListener(v2 -> {
                String server = newServer.getText().toString();
                if (server.equals("")) {
                    Toast.makeText(ContactsActivity.this, "Please enter new server", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
            });
            dialog.show();
        });


    }

    //function validate that all fields entered and return true if they are
    private boolean isValid(String username, String nickname, String server) {
        if (username.equals("") || nickname.equals("") || server.equals("")) {
            Toast.makeText(ContactsActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        } else if (this.currentUser.getId().equals(username) &&
                    server.equals(App.getContext().getString(R.string.web_api_url))){
            Toast.makeText(ContactsActivity.this, "Cant add yourself", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // function filter the contacts list based on the text that entered
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

    private void setName(String name_user) {
        TextView user = findViewById(R.id.user);
        user.setText(name_user);
    }

    public User getUser() {
        return this.currentUser;
    }
}