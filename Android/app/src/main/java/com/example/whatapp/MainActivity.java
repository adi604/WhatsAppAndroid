package com.example.whatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.whatapp.api.UserAPI;
import com.example.whatapp.entities.Lambda;
import com.example.whatapp.entities.LoginInfo;
import com.example.whatapp.entities.TokenRequest;
import com.example.whatapp.entities.User;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String tokenFirebase;
    private String tokenJWT;


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
                        tokenJWT = "Bearer " + response;
                        i.putExtra("token", tokenJWT);
                        userAPI.getUser(tokenJWT, new Lambda() {
                            @Override
                            public void onGetUserResponse(User response) {
                                i.putExtra("current-user", new Gson().toJson(response));
                                startActivity(i);
                            }
                        });

                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, instanceIdResult -> {
                            tokenFirebase = instanceIdResult.getToken();
                            Call<Void> call;
                            call = userAPI.webServiceAPI.setToken(tokenJWT, new TokenRequest(tokenFirebase));
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                }
                            });

                        });
                        return;
                    }
                    Toast.makeText(MainActivity.this, R.string.login_error_Msg, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}