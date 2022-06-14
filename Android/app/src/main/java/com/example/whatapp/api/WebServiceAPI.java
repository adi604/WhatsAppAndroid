package com.example.whatapp.api;

import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Invitation;
import com.example.whatapp.entities.LoginInfo;
import com.example.whatapp.entities.Message;
import com.example.whatapp.entities.NewContact;
import com.example.whatapp.entities.NewUpdateMessage;
import com.example.whatapp.entities.TokenRequest;
import com.example.whatapp.entities.Transfer;
import com.example.whatapp.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("users")
    Call<User> getUser(@Header("Authorization") String token);

    @POST("users/register")
    Call<String> createUser(@Body User user);

    @POST("users/login")
    Call<String> login(@Body LoginInfo loginInfo);

    @GET("contacts")
    Call<List<Contact>> getAllContacts(@Header("Authorization") String token);

    @POST("contacts")
    Call<String> createContact(@Header("Authorization") String token, @Body NewContact newContact);

    @POST("invitations")
    Call<String> invite(@Body Invitation invitation);

    @GET("contacts/{id}/messages")
    Call<List<Message>> getAllMessages(@Header("Authorization") String token, @Path("id") String id);

    @POST("contacts/{id}/messages")
    Call<String> sendMessage(@Header("Authorization") String token, @Path("id") String id, @Body NewUpdateMessage content);

    @POST("transfer")
    Call<String> sendTransfer(@Body Transfer transfer);

    @POST("users/token")
    Call<Void> setToken(@Header("Authorization") String token, TokenRequest tokenRequest);
}
