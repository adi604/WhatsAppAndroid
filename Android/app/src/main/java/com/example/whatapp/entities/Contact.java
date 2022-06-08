package com.example.whatapp.entities;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Formatter;

import com.example.whatapp.R;

@Entity
public class Contact {

    @PrimaryKey
    private String id;
    private String userId;
    private String name;
    private String server;
    private String last;
    private int image;
    private Calendar lastDate;

    public Contact(String id, String name, String server, String last, int image, Calendar lastDate) {
        this.userId = null;
        this.id = id;
        this.name = name;
        this.server = server;
        this.last = last;
        this.image = image;
        this.lastDate = lastDate;
    }

    public Contact(String id, String userId, String name, String server, String last, int image, Calendar lastDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.server = server;
        this.last = last;
        this.image = image;
        this.lastDate = lastDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Contact(String user,String message,  int img) {
        name = user;
        image = img;
        lastDate = Calendar.getInstance();
        last = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public Contact() {
        image = R.drawable.background;
    }

    public void setLastDate(Calendar lastMessage) {
        this.lastDate = lastMessage;
    }

    public String getLastDate() {
        Formatter fmt = new Formatter();
        fmt.format("%tl:%tM", lastDate, lastDate);
        return fmt.toString();
    }

}
