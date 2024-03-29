package com.example.whatapp.entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Formatter;

import com.example.whatapp.App;
import com.example.whatapp.R;

@Entity(primaryKeys = {"id", "userId"})
public class Contact {

    @NonNull
    private String id;
    @NonNull
    private String userId;
    private String name;
    private String server;
    private String last;

    private String lastDate;

    public Contact(@NonNull String id,@NonNull String userId, String name, String server, String last, String lastDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.server = server;
        this.last = last;

        this.lastDate = lastDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Contact(String user,String message) {
        name = user;
        last = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public @NonNull  String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public Contact() {

    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastDate() {
        if (!lastDate.contains("T")) {
            return lastDate;
        }
        String time[] = lastDate.split("T")[1].split(":");
        String result = time[0] + ":" + time[1];
        return result;
    }

}
