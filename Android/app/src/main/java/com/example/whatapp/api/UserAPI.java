package com.example.whatapp.api;

import com.example.whatapp.R;
import com.example.whatapp.entities.LoginInfo;
import com.example.whatapp.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    String response;

    public UserAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5028/api/")//String.valueOf((R.string.BaseUrl)))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void login(String username, String password) {
        Call<String> call;
        call = webServiceAPI.login(new LoginInfo(username, password));
        call.enqueue(new Callback<String>() {
            private String response;

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                this.response = response.body().toString();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}