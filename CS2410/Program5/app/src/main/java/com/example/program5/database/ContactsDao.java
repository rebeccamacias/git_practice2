package com.example.program5.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.program5.models.Contact;

import java.util.List;

@Dao
public interface ContactsDao {

    @Query("SELECT * FROM Contact")
    List<Contact> getAllContacts();

    @Query("SELECT * FROM Contact WHERE id = :id LIMIT 1")
    Contact getContact(long id);

    @Insert
    long insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
}
