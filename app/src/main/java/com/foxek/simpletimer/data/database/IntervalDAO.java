package com.foxek.simpletimer.data.database;

import com.foxek.simpletimer.data.model.Interval;

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
    void add(Interval interval);

    @Delete()
    void delete(Interval interval);

    @Update
    void update(Interval interval);

    @Query("SELECT * FROM Interval WHERE id IS :id AND trainingID IS :workoutId")
    Single<Interval> getById(int id, int workoutId);

    @Query("SELECT * FROM Interval WHERE ID = (SELECT MAX(ID)  FROM Interval)")
    Single<Interval> getLast();

    @Query("SELECT * FROM Interval WHERE trainingID IS :workoutId")
    Single<List<Interval>> getAll(int workoutId);
}
