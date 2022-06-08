package com.example.whatapp.entities;

import java.util.List;

public interface Lambda {
    default void onLoginResponse(String response) {}

    default void onGetUserResponse(User response) {}

    default void onGetContactsResponse(List<Contact> response) {}
}
