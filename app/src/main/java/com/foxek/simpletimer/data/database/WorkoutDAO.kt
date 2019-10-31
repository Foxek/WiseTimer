package com.foxek.simpletimer.data.database


import com.foxek.simpletimer.data.model.Workout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface WorkoutDAO {

    @get:Query("SELECT MAX(uid) FROM trainings")
    val lastId: Maybe<Int>

    @get:Query("SELECT *, COUNT(*) AS intervalNumber FROM trainings, Interval WHERE trainingID = uid GROUP BY trainingID HAVING intervalNumber != 0")
    val all: Flowable<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(workout: Workout)

    @Query("DELETE FROM trainings WHERE uid = :WorkoutID")
    fun delete(WorkoutID: Int)

    @Query("UPDATE trainings SET volumeState =:state WHERE uid = :id")
    fun update(state: Boolean, id: Int)

    @Query("UPDATE trainings SET training_name=:name WHERE uid = :id")
    fun update(name: String, id: Int)

    @Query("SELECT * FROM trainings WHERE uid = :WorkoutID")
    fun getById(WorkoutID: Int): Single<Workout>

    @Query("SELECT volumeState FROM trainings WHERE uid = :WorkoutID")
    fun getVolume(WorkoutID: Int): Single<Boolean>
}
