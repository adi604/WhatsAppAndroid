package com.example.whatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatapp.api.UserAPI;
import com.example.whatapp.entities.Lambda;
import com.example.whatapp.entities.User;
import com.google.gson.Gson;

public class RegisterActivity extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UserAPI userAPI = new UserAPI();

        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            // validate all of the fields entered
            User user = getAndValidateUser();
            if(user == null) {
                return;
            }
            // creating a new user
            userAPI.register(user, new Lambda() {
                // function that will be called on response from the webAPI
                @Override
                public void onLoginResponse(String response) {
                    // if the username isnt taken than gets the token and start the contact activity
                    if (!response.equals("Username already taken")) {
                        Intent i = new Intent(RegisterActivity.this, ContactsActivity.class);
                        String token = "Bearer " + response;
                        i.putExtra("token", token);
                        i.putExtra("current-user", new Gson().toJson(response));
                        startActivity(i);
                        return;
                    }
                    Toast.makeText(RegisterActivity.this, R.string.register_username_taken_msg, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    // function tests if the fields are valid and if they are then return a new user with those fields
    private User getAndValidateUser() {
        EditText etUsername = findViewById(R.id.etRegisterUsername);
        String username = etUsername.getText().toString();
        EditText etNickname = findViewById(R.id.etRegisterNickname);
        String nickname = etNickname.getText().toString();
        EditText etPassword = findViewById(R.id.etRegisterPassword);
        String password = etPassword.getText().toString();
        EditText etConfPass = findViewById(R.id.etRegisterConfirmPassword);
        String confPassword = etConfPass.getText().toString();

        if (username.isEmpty() || nickname.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, R.string.register_empty_field_msg , Toast.LENGTH_SHORT).show();
            return null;
        } else if (!password.equals(confPassword)) {
            Toast.makeText(RegisterActivity.this, R.string.register_password_confirmation_msg , Toast.LENGTH_SHORT).show();
            return null;
        } else if (password.length() < 10) {
            Toast.makeText(RegisterActivity.this, R.string.register_password_length_msg , Toast.LENGTH_SHORT).show();
            return null;
        }
        return new User(username, nickname, password, "none");
    }
}