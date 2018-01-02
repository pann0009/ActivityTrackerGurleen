package com.example.pro_24_12_17_gurleen.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class UserActivity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String type;
    private double duration;
    private String comment;

    private Long timeStamp;

    @Ignore
    public UserActivity(String type, double duration, String comment,Date date) {
        this.type = type;
        this.duration = duration;
        this.comment = comment;
        this.timeStamp = dateToTimestamp(date);
    }

    public UserActivity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.timeStamp = dateToTimestamp(date);
    }

    public Date getDate() {
        return fromTimestamp(timeStamp);
    }


    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public static Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }

    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

}
