package com.IN6222.myapplication;

import android.app.Application;

import com.IN6222.myapplication.db.DBManager;

public class diaryApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.innitDB(getApplicationContext());
    }
}
