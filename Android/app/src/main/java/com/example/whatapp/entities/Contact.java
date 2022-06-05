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
    private int image;
    private Calendar lastMessage;


    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setImage(int image) {
        this.image = image;
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

    public void setLastMessage(Calendar lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        Formatter fmt = new Formatter();
        fmt.format("%tl:%tM", lastMessage, lastMessage);
        return fmt.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Contact(String user, int img) {
        userName = user;
        image = img;
        lastMessage = Calendar.getInstance();

    }
}
