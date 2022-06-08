package com.example.whatapp.api;

import com.example.whatapp.entities.Lambda;
import com.example.whatapp.entities.LoginInfo;
import com.example.whatapp.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5028/api/")//String.valueOf((R.string.BaseUrl)))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void login(String username, String password, Lambda onResponse) {
        Call<String> call;
        call = webServiceAPI.login(new LoginInfo(username, password));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                onResponse.onLoginResponse(response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public void getUser(String token, Lambda onResponse) {
        Call<User> call;
        call = webServiceAPI.getUser(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                onResponse.onGetUserResponse(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    public void register(User newUser, Lambda onResponse) {
        Call<String> call;
        call = webServiceAPI.createUser(newUser);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                onResponse.onLoginResponse(response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}