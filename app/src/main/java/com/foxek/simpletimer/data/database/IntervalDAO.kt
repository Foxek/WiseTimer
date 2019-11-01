package com.foxek.simpletimer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.foxek.simpletimer.data.model.Interval

import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface IntervalDAO {

    @get:Query("SELECT MAX(id)  FROM Interval")
    val lastId: Single<Int>

    @Insert
    fun add(interval: Interval)

    @Query("DELETE FROM Interval WHERE id IS :id AND trainingID IS :workoutId")
    fun delete(id: Int, workoutId: Int)

    @Update
    fun update(interval: Interval)

    @Query("SELECT * FROM Interval WHERE id IS :id AND trainingID IS :workoutId")
    fun getById(id: Int, workoutId: Int): Single<Interval>

    @Query("SELECT COUNT(*) FROM Interval WHERE trainingID IS :workoutId")
    fun size(workoutId: Int): Single<Int>

    @Query("SELECT * FROM Interval WHERE trainingID IS :workoutId")
    fun getAll(workoutId: Int): Flowable<List<Interval>>
}
