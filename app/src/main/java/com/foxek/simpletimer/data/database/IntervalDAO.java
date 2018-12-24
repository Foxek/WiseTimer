package com.foxek.simpletimer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.foxek.simpletimer.data.model.interval.Interval;

import java.util.List;

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
