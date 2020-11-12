package com.example.program5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.program5.database.AppDatabase;
import com.example.program5.presenters.BaseMVPView;

public class BaseActivity extends AppCompatActivity implements BaseMVPView {

    public AppDatabase getContextDatabase() {
        return Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "contacts-database").build();
    }
}
