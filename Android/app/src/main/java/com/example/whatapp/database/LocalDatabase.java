package com.example.whatapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.whatapp.entities.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();
}
