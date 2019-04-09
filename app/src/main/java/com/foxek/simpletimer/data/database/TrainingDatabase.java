package com.foxek.simpletimer.data.database;

import com.foxek.simpletimer.data.database.model.Interval;
import com.foxek.simpletimer.data.database.model.Workout;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Workout.class, Interval.class}, version = 3,exportSchema = false)
public abstract class TrainingDatabase extends RoomDatabase {
    public abstract WorkoutDAO getWorkoutDAO();
    public abstract IntervalDAO getIntervalDAO();
}

