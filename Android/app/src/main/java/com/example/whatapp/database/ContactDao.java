package com.example.whatapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.whatapp.entities.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact WHERE userId = :userId")
    List<Contact> getAllContacts(String userId);

    @Query("SELECT * FROM contact WHERE id = :id And userId = :userId")
    Contact getContact(String id, String userId);

    @Insert
    void insert(Contact... contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);
}

