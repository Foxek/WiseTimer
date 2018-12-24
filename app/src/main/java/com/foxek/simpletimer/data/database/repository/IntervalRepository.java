package com.foxek.simpletimer.data.database.repository;


import com.foxek.simpletimer.data.database.TrainingDatabase;
import com.foxek.simpletimer.data.database.model.Interval;
import com.foxek.simpletimer.di.RoomDatabase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class IntervalRepository {

    private TrainingDatabase mDatabase;

    @Inject
    public IntervalRepository(@RoomDatabase TrainingDatabase database){
        mDatabase = database;
    }

    public void createNewInterval(Interval interval){
        mDatabase.getIntervalDAO().addInterval(interval);
    }

    public Single<List<Interval>> getAllInterval(int workoutId){
        return mDatabase.getIntervalDAO().getIntervalForWorkout(workoutId);
    }

    public Single<Interval> getLastInterval(){
        return mDatabase.getIntervalDAO().getLastInterval();
    }

    public Single<Interval> getIntervalById(int intervalId, int workoutId){
        return mDatabase.getIntervalDAO().getIntervalByID(intervalId,workoutId);
    }

    public Single<List<Interval>> getIntervalForWorkout(int workoutId){
        return mDatabase.getIntervalDAO().getIntervalForWorkout(workoutId);
    }

    public void updateInterval(Interval interval){
        mDatabase.getIntervalDAO().updateInterval(interval);
    }

    public void deleteInterval(Interval interval){
        mDatabase.getIntervalDAO().deleteInterval(interval);
    }
}
