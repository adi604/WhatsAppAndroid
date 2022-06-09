package com.example.whatapp.entities;

import androidx.annotation.NonNull;

public class NewContact {
    @NonNull
    private String id;
    @NonNull
    private String name;
    private String server;

    public NewContact(String i, String n, String s)
    {
        id = i;
        name = n;
        server = s;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
