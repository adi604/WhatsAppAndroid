package com.example.whatapp.entities;

import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Formatter;

public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private boolean sent;
    private String content;
    private Calendar time;

    public Message(boolean sent, String content) {
        this.sent = sent;
        this.content = content;
        this.time = Calendar.getInstance();
    }

    public int getId() {
        return id;
    }

    public boolean isSent() {
        return sent;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        Formatter fmt = new Formatter();
        fmt.format("%tl:%tM", time, time);
        return fmt.toString();
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
