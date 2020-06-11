package com.foxek.simpletimer.data.database

import androidx.room.*
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.data.model.Workout

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class TimerDAO {

    @Transaction
    open fun addNewWorkout(workout: Workout, interval: Interval) {
        addWorkout(workout)
        addInterval(interval)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addWorkout(workout: Workout)

    @Query("DELETE FROM trainings WHERE uid = :WorkoutID")
    abstract fun deleteWorkout(WorkoutID: Int)

    @Query("UPDATE trainings SET volumeState =:volumeState WHERE uid = :workoutId")
    abstract fun updateWorkoutVolumeState(volumeState: Boolean, workoutId: Int)

    @Query("UPDATE trainings SET training_name=:workoutName WHERE uid = :workoutId")
    abstract fun updateWorkoutName(workoutName: String, workoutId: Int)

    @Query("SELECT * FROM trainings WHERE uid = :WorkoutID")
    abstract fun getWorkoutById(WorkoutID: Int): Single<Workout>

    @Query("SELECT *, COUNT(*) AS intervalNumber FROM trainings, Interval WHERE trainingID = uid GROUP BY trainingID HAVING intervalNumber != 0")
    abstract fun observeWorkouts(): Flowable<List<Workout>>

    @Query("SELECT MAX(uid) FROM trainings")
    abstract fun getLastWorkoutId(): Maybe<Int>

    @Query("SELECT volumeState FROM trainings WHERE uid = :WorkoutID")
    abstract fun getWorkoutVolumeState(WorkoutID: Int): Single<Boolean>

    @Query("SELECT COUNT(*) FROM Interval WHERE trainingID IS :workoutId")
    abstract fun getNumberOfIntervalForWorkout(workoutId: Int): Single<Int>

    @Insert
    abstract fun addInterval(interval: Interval)

    @Query("DELETE FROM Interval WHERE id IS :id AND trainingID IS :workoutId")
    abstract fun deleteInterval(id: Int, workoutId: Int)

    @Update
    abstract fun updateInterval(interval: Interval)

    @Query("SELECT * FROM Interval WHERE id IS :id AND trainingID IS :workoutId")
    abstract fun getIntervalById(id: Int, workoutId: Int): Single<Interval>

    @Query("SELECT * FROM Interval WHERE trainingID IS :workoutId")
    abstract fun observeIntervals(workoutId: Int): Flowable<List<Interval>>

    @Query("SELECT * FROM Interval WHERE trainingID IS :workoutId")
    abstract fun getIntervals(workoutId: Int): Single<List<Interval>>

    @Query("SELECT MAX(id)  FROM Interval")
    abstract fun getLastIntervalId(): Single<Int>
}
