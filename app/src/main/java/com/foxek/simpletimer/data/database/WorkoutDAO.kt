package com.foxek.simpletimer.data.database


import androidx.room.*
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.data.model.Workout

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class WorkoutDAO {

    @Transaction
    open fun addNewWorkout(workout: Workout, interval: Interval) {
        add(workout)
        add(interval)
    }

    //TODO: Убрать в отдельный дао или совместить дао
    @Insert
    abstract fun add(interval: Interval)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun add(workout: Workout)

    @Query("DELETE FROM trainings WHERE uid = :WorkoutID")
    abstract fun delete(WorkoutID: Int)

    @Query("UPDATE trainings SET volumeState =:state WHERE uid = :id")
    abstract fun update(state: Boolean, id: Int)

    @Query("UPDATE trainings SET training_name=:name WHERE uid = :id")
    abstract fun update(name: String, id: Int)

    @Query("SELECT * FROM trainings WHERE uid = :WorkoutID")
    abstract fun getById(WorkoutID: Int): Single<Workout>

    @Query("SELECT *, COUNT(*) AS intervalNumber FROM trainings, Interval WHERE trainingID = uid GROUP BY trainingID HAVING intervalNumber != 0")
    abstract fun getAll(): Flowable<List<Workout>>

    @Query("SELECT MAX(uid) FROM trainings")
    abstract fun getLastId(): Maybe<Int>

    @Query("SELECT volumeState FROM trainings WHERE uid = :WorkoutID")
    abstract fun getVolume(WorkoutID: Int): Single<Boolean>
}
