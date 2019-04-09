package com.foxek.simpletimer.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "trainings")
public class Workout {

    @ColumnInfo(name = "training_name")
    public String training_name;

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "volumeState")
    public int volumeState;

//    @Ignore
    @ColumnInfo(name = "intervalNumber")
    public int intervalNumber;

    public Workout(String training_name, int uid, int intervalNumber, int volumeState) {
        this.training_name = training_name;
        this.intervalNumber = intervalNumber;
        this.uid = uid;
        this.volumeState = volumeState;
    }

    public String getTraining_name() {
        return training_name;
    }

    public void setTraining_name(String name) {
        this.training_name = name;
    }
}
