package com.example.whatapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Message;

@Database(entities = {Contact.class, Message.class}, version = 5)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();
}
