package com.example.whatapp.entities;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Formatter;

import com.example.whatapp.R;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String userName;
    private String server;
    private String lastMessageC;
    private int image;
    private Calendar lastMessageT;


    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getLastMessageC() {
        return lastMessageC;
    }

    public void setLastMessageC(String lastMessageC) {
        this.lastMessageC = lastMessageC;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getImage() {
        return image;
    }

    public Contact() {
        image = R.drawable.background;
    }

    public void setLastMessageT(Calendar lastMessage) {
        this.lastMessageT = lastMessage;
    }

    public String getLastMessageT() {
        Formatter fmt = new Formatter();
        fmt.format("%tl:%tM", lastMessageT, lastMessageT);
        return fmt.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Contact(String user,String message,  int img) {
        userName = user;
        image = img;
        lastMessageT = Calendar.getInstance();
        lastMessageC = message;
    }
}
