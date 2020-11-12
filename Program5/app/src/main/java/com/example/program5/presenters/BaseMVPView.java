package com.example.program5.presenters;

import com.example.program5.database.AppDatabase;

public interface BaseMVPView {
    public AppDatabase getContextDatabase();
}
