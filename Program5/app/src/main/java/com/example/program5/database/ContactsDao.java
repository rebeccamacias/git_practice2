package com.example.program5.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.program5.models.Contact;

import java.util.List;

@Dao
public interface ContactsDao {

    @Query("SELECT * FROM Contact")
    List<Contact> getAllContacts();

    @Insert
    long insert(Contact contact);

    @Query("SELECT * FROM Contact WHERE id = :id LIMIT 1")
    Contact getContact(int id);
}
