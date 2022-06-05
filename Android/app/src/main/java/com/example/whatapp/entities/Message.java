package com.example.whatapp.entities;

import androidx.room.PrimaryKey;

import java.util.Calendar;

public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private boolean sent;
    private String content;
    private Calendar time;

    public int getId() {
        return id;
    }

    public boolean isSent() {
        return sent;
    }

    public String getContent() {
        return content;
    }

    public Calendar getTime() {
        return time;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }




}
