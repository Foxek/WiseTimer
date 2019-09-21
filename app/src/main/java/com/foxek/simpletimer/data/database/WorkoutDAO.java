package com.foxek.simpletimer.data.database;


import com.foxek.simpletimer.data.model.Workout;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
@SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
public interface WorkoutDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(Workout workout);

    @Query("DELETE FROM trainings WHERE uid = :WorkoutID")
    void delete(int WorkoutID);

    @Query("UPDATE trainings SET volumeState =:state WHERE uid = :id")
    void update(boolean state, int id);

    @Query("UPDATE trainings SET training_name=:name WHERE uid = :id")
    void update(String name, int id);

    @Query("SELECT MAX(uid) FROM trainings")
    Maybe<Integer> getLastId();

    @Query("SELECT * FROM trainings WHERE uid = :WorkoutID")
    Single<Workout> getById(int WorkoutID);

    @Query("SELECT volumeState FROM trainings WHERE uid = :WorkoutID")
    Single<Boolean> getVolume(int WorkoutID);

    @Query("SELECT *, COUNT(*) AS intervalNumber FROM trainings, Interval WHERE trainingID = uid GROUP BY trainingID HAVING intervalNumber != 0")
    Flowable<List<Workout>> getAll();
}
