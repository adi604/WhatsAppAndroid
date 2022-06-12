package com.example.whatapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteTable;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Message;
import com.example.whatapp.entities.Chat;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM message WHERE userId = :userId And contactId= :contactId")
    List<Message> getAllMessages(String userId, String contactId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message... messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);
}
