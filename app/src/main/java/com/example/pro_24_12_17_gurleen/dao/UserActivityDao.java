package com.example.pro_24_12_17_gurleen.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.pro_24_12_17_gurleen.entities.UserActivity;

import java.util.List;

@Dao
public interface UserActivityDao {
    @Query("SELECT * FROM UserActivity")
    List<UserActivity> getAll();

    @Insert
    void insertAll(UserActivity... users);

    @Update
    void updateUsers(UserActivity... users);

    @Delete
    void delete(UserActivity user);

    @Query("SELECT * FROM UserActivity ua WHERE ua.timeStamp > :timeStamp")
    List<UserActivity> getActivitiesHavingTimeStampgreaterThan(Long timeStamp);


    @Query("SELECT * FROM UserActivity ua WHERE  ua.id = :id")
    UserActivity selectById(long id);
}
