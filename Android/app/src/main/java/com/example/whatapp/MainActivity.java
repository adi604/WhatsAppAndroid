package com.example.whatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.whatapp.api.UserAPI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserAPI userAPI = new UserAPI();

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });

        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(v -> {
            EditText etUsername = findViewById(R.id.editTextTextPersonName);
            String username = etUsername.getText().toString();
            EditText etPassword = findViewById(R.id.editTextTextPassword);
            String password = etPassword.getText().toString();
            userAPI.login(username, password);

            //Intent i = new Intent(this, ContactsActivity.class);
            //startActivity(i);
        });
    }
}