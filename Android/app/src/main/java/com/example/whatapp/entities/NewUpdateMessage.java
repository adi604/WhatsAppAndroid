package com.example.whatapp.entities;

public class NewUpdateMessage {
    private String content;

    public NewUpdateMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
