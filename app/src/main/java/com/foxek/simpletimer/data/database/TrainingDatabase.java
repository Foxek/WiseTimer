package com.foxek.simpletimer.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.foxek.simpletimer.data.database.model.Interval;
import com.foxek.simpletimer.data.database.model.Workout;

@Database(entities = {Workout.class, Interval.class}, version = 2)
public abstract class TrainingDatabase extends RoomDatabase {
    public abstract WorkoutDAO getWorkoutDAO();
    public abstract IntervalDAO getIntervalDAO();
}

