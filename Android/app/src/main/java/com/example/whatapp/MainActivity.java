package com.example.whatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatapp.api.UserAPI;
import com.example.whatapp.entities.Lambda;
import com.example.whatapp.entities.User;
import com.google.gson.Gson;

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

            // sends the username and password to the WebAPI
            userAPI.login(username, password, new Lambda() {
                //function to be called when the response arrived
                @Override
                public void onLoginResponse(String response) {
                    if (!response.equals("Invalid username or password")) {
                        Intent i = new Intent(MainActivity.this, ContactsActivity.class);
                        String token = "Bearer " + response;
                        i.putExtra("token", token);
                        userAPI.getUser(token, new Lambda() {
                            @Override
                            public void onGetUserResponse(User response) {
                                i.putExtra("current-user", new Gson().toJson(response));
                                startActivity(i);
                            }
                        });
                        return;
                    }
                    Toast.makeText(MainActivity.this, "Invalid username or password" , Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}