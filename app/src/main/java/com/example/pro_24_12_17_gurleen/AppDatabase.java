package com.example.pro_24_12_17_gurleen;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.pro_24_12_17_gurleen.dao.UserActivityDao;
import com.example.pro_24_12_17_gurleen.entities.UserActivity;

@Database(entities = {UserActivity.class}, version = 1)
public abstract class AppDatabase  extends RoomDatabase {
    public abstract UserActivityDao userActivityDao();
}
