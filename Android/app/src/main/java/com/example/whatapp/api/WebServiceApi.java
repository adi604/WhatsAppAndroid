package com.example.whatapp.api;

import com.example.whatapp.entities.LoginInfo;
import com.example.whatapp.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface WebServiceAPI {
    @GET("users")
    Call<User> getUser();

    @POST("users/register")
    Call<String> createUser(@Body User user);

    @POST("users/login")
    Call<String> login(@Body LoginInfo loginInfo);
}
