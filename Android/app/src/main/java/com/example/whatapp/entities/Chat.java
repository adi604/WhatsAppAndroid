package com.example.whatapp.entities;

import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String user1;
    private String user2;
    private List<Message> messages;

    public Chat(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;
        messages = new ArrayList<Message>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public String getUser1() {
        return user1;
    }

    public String getUser2() {
        return user2;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
