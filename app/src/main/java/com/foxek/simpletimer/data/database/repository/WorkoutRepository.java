package com.foxek.simpletimer.data.database.repository;

import com.foxek.simpletimer.data.database.TrainingDatabase;
import com.foxek.simpletimer.data.database.model.Workout;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;


public class WorkoutRepository {

    private TrainingDatabase        mDatabase;

    @Inject
    public WorkoutRepository(TrainingDatabase database){
        mDatabase = database;
    }

    public Flowable<List<Workout>> getAllWorkouts(){
        return mDatabase.getWorkoutDAO().getAllWorkout();
    }

    public void createWorkout(Workout workout){
        mDatabase.getWorkoutDAO().addWorkout(workout);
    }

    public Single<Workout> getWorkoutById(int workoutId){
        return mDatabase.getWorkoutDAO().getWorkoutById(workoutId);
    }

    public Completable updateWorkoutName(String name, int id){
        return Completable.fromAction(() -> mDatabase.getWorkoutDAO().updateWorkoutName(name, id));
    }
    public Completable updateWorkoutVolumeState(int state, int id){
        return Completable.fromAction(() -> mDatabase.getWorkoutDAO().updateVolumeState(state, id));
    }
    public void deleteWorkout(int workoutId){
        mDatabase.getWorkoutDAO().deleteWorkout(workoutId);
    }
}
