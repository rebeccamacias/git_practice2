package com.example.program5.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.program5.models.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactsDao getContactDao();
}
