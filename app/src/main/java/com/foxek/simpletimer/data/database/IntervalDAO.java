package com.foxek.simpletimer.data.database;

import com.foxek.simpletimer.data.database.model.Interval;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Single;

@Dao
public interface IntervalDAO {

    @Insert
    void addInterval(Interval interval);

    @Delete()
    void deleteInterval(Interval interval);

    @Update
    void updateInterval(Interval interval);

    @Query("SELECT * FROM Interval WHERE id IS :id AND trainingID IS :workoutId")
    Single<Interval> getIntervalByID(int id, int workoutId);

    @Query("SELECT * FROM Interval WHERE ID = (SELECT MAX(ID)  FROM Interval)")
    Single<Interval> getLastInterval();

    @Query("SELECT * FROM Interval WHERE trainingID IS :Id")
    Single<List<Interval>> getIntervalForWorkout(int Id);
}
