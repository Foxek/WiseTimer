package com.foxek.simpletimer.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "trainings")
class Workout(
        @ColumnInfo(name = "training_name")
        var name: String?,

        @PrimaryKey
        var uid: Int,

        @ColumnInfo(name = "intervalNumber")
        var intervalCount: Int,

        @ColumnInfo(name = "volumeState")
        var isVolume: Boolean
)

//@Entity(tableName = "trainings")
//public class Workout {
//
//    @PrimaryKey
//    private int uid;
//
//    @ColumnInfo(name = "training_name")
//    private String name;
//
//    @ColumnInfo(name = "volumeState")
//    private boolean volume;
//
//    @ColumnInfo(name = "intervalNumber")
//    private int intervalCount;
//
//    public Workout(String name, int uid, int intervalCount, boolean volume) {
//        this.name = name;
//        this.intervalCount = intervalCount;
//        this.uid = uid;
//        this.volume = volume;
//    }
//
//    public int getUid() {
//        return uid;
//    }
//
//    public void setUid(int uid) {
//        this.uid = uid;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//    public boolean isVolume() {
//        return volume;
//    }
//
//    public void setVolume(boolean volume) {
//        this.volume = volume;
//    }
//
//    public int getIntervalCount() {
//        return intervalCount;
//    }
//
//    public void setIntervalCount(int intervalCount) {
//        this.intervalCount = intervalCount;
//    }
//}