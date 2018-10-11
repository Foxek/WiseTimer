package com.foxek.simpletimer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.foxek.simpletimer.data.model.workout.Workout;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface WorkoutDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addWorkout(Workout workout);

    @Update
    void updateWorkout(Workout workout);

    @Query("DELETE FROM trainings WHERE uid = :WorkoutID")
    void deleteWorkout(int WorkoutID);

    @Query("SELECT * FROM trainings WHERE uid = :WorkoutID")
    Single<Workout> getWorkoutById(int WorkoutID);

    @Query("SELECT *, COUNT(*) AS intervalNumber FROM trainings,Interval WHERE trainingID = uid GROUP BY trainingID HAVING intervalNumber != 0")
    Flowable<List<Workout>> getAllWorkout();



}
