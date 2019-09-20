package com.foxek.simpletimer.data.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Workout.class, parentColumns = "uid", childColumns = "trainingID", onDelete = CASCADE))
public class Interval {

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "work_intervals")
    private int workTime;

    @ColumnInfo(name = "rest_intervals")
    private int restTime;

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "position_id")
    private int position;

    @ColumnInfo(name = "trainingID")
    private int workoutId;

    public Interval(String name, int workTime, int restTime, int workoutId, int position) {
        this.name = name;
        this.workTime = workTime;
        this.restTime = restTime;
        this.workoutId = workoutId;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int setID() {
        return id;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }
}