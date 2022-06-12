package com.example.whatapp.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Formatter;
@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int localId;
    private int id;
    @NonNull
    private String userId;
    @NonNull
    private String contactId;
    private boolean sent;
    private String content;
    private String created;

    public Message(int id, @NonNull String userId, @NonNull String contactId, boolean sent, String content, String created) {
        this.id = id;
        this.userId = userId;
        this.contactId = contactId;
        this.sent = sent;
        this.content = content;
        this.created = created;
    }

    @Ignore
    public Message(@NonNull String userId, @NonNull String contactId, boolean sent, String content, String created) {
        this.userId = userId;
        this.contactId = contactId;
        this.sent = sent;
        this.content = content;
        this.created = created;
    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getContactId() {
        return contactId;
    }

    public void setContactId(@NonNull String contactId) {
        this.contactId = contactId;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public String getCreated() {
        Formatter fmt = new Formatter();
        fmt.format("%tl:%tM", Calendar.getInstance(), Calendar.getInstance());
        return fmt.toString();
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public void setContent(String content) {
        this.content = content;
    }





}
