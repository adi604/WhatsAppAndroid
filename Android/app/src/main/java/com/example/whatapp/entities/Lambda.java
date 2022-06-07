package com.example.whatapp.entities;

public interface Lambda {
    default void onLoginResponse(String response) {}

    default void onGetUserResponse(User response) {}
}
