package com.example.whatapp.api;

import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.LoginInfo;
import com.example.whatapp.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

interface WebServiceAPI {
    @GET("users")
    Call<User> getUser(@Header("Authorization") String token);

    @POST("users/register")
    Call<String> createUser(@Body User user);

    @POST("users/login")
    Call<String> login(@Body LoginInfo loginInfo);

    @GET("contacts")
    Call<List<Contact>> getAllContacts(@Header("Authorization") String token);
}
