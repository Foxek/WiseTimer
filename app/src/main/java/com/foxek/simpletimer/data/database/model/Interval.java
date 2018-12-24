package com.foxek.simpletimer.data.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(indices = {@Index("trainingID")},foreignKeys = @ForeignKey(entity = Workout.class, parentColumns = "uid", childColumns = "trainingID", onDelete = CASCADE))

public class Interval {

    @ColumnInfo(name = "work_intervals")
    public int workInterval;

    @ColumnInfo(name = "rest_intervals")
    public int restInterval;

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "position_id")
    public int position_id;

    @ColumnInfo(name = "trainingID")
    public int trainingID;

    public Interval(int workInterval, int restInterval, int trainingID, int position_id) {
        this.workInterval = workInterval;
        this.restInterval = restInterval;
        this.trainingID = trainingID;
        this.position_id = position_id;
    }

    public int getID() {
        return id;
    }

}