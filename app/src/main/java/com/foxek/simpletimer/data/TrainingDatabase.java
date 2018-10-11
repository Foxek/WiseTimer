package com.foxek.simpletimer.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.foxek.simpletimer.data.model.interval.Interval;
import com.foxek.simpletimer.data.model.workout.Workout;

@Database(entities = {Workout.class, Interval.class}, version = 2)
public abstract class TrainingDatabase extends RoomDatabase {
    public abstract WorkoutDAO getWorkoutDAO();
    public abstract IntervalDAO getIntervalDAO();
}

