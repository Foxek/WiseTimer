package com.foxek.simpletimer.data.database;


import com.foxek.simpletimer.data.database.model.Workout;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
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
