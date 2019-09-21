package com.foxek.simpletimer.data.database;

import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.data.model.Workout;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Workout.class, Interval.class}, version = 4, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract WorkoutDAO getWorkoutDAO();

    public abstract IntervalDAO getIntervalDAO();
}

