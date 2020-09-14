package com.foxek.simpletimer.data.database

import androidx.room.*
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.data.model.Workout

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class TimerDAO {

    @Transaction
    open fun addNewWorkout(workout: Workout, round: Round) {
        addWorkout(workout)
        addRound(round)
    }

    @Transaction
    open fun updateWorkout(workoutId: Int, workoutName: String, rounds: List<Round>) {
        updateWorkoutName(workoutName, workoutId)
        rounds.forEach { updatePositionInWorkout(it.id, it.positionInWorkout) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addWorkout(workout: Workout)

    @Query("DELETE FROM workout WHERE id = :workoutID")
    abstract fun deleteWorkout(workoutID: Int)

    @Query("UPDATE workout SET isSilentMode =:volumeState WHERE id = :workoutId")
    abstract fun updateWorkoutVolumeState(volumeState: Boolean, workoutId: Int)

    @Query("UPDATE workout SET name=:workoutName WHERE id = :workoutId")
    abstract fun updateWorkoutName(workoutName: String, workoutId: Int)

    @Query("SELECT * FROM workout WHERE id = :WorkoutID")
    abstract fun getWorkoutById(WorkoutID: Int): Single<Workout>

    @Query("SELECT w.*, COUNT(*) AS numberOfRounds FROM workout w, Round WHERE workoutId = w.id GROUP BY workoutId HAVING numberOfRounds != 0")
    abstract fun observeWorkouts(): Flowable<List<Workout>>

    @Query("SELECT MAX(id) FROM workout")
    abstract fun getLastWorkoutId(): Maybe<Int>

    @Query("SELECT isSilentMode FROM workout WHERE id = :WorkoutID")
    abstract fun getWorkoutVolumeState(WorkoutID: Int): Single<Boolean>

    @Query("SELECT COUNT(*) FROM Round WHERE workoutId IS :workoutId")
    abstract fun getNumberOfRoundForWorkout(workoutId: Int): Single<Int>

    @Insert
    abstract fun addRound(round: Round)

    @Transaction
    open fun deleteRoundAndRearrangeOthers(roundId: Int, workoutId: Int) {
        deleteRound(roundId, workoutId)
        getRounds(workoutId).blockingGet().forEachIndexed { idx, round ->
            round.positionInWorkout = idx
            updatePositionInWorkout(round.id, round.positionInWorkout)
        }
    }

    @Query("DELETE FROM Round WHERE id IS :id AND workoutId IS :workoutId")
    abstract fun deleteRound(id: Int, workoutId: Int)

    @Update
    abstract fun updateRound(round: Round)

    @Query("SELECT * FROM Round WHERE id IS :id AND workoutId IS :workoutId")
    abstract fun getRoundById(id: Int, workoutId: Int): Single<Round>

    @Query("SELECT * FROM Round WHERE workoutId IS :workoutId")
    abstract fun observeRounds(workoutId: Int): Flowable<List<Round>>

    @Query("SELECT * FROM Round WHERE workoutId IS :workoutId")
    abstract fun getRounds(workoutId: Int): Single<List<Round>>

    @Query("UPDATE round SET positionInWorkout = :newPosition WHERE id IS :roundId")
    abstract fun updatePositionInWorkout(roundId: Int, newPosition: Int)

    @Query("SELECT MAX(positionInWorkout) FROM Round WHERE workoutId IS :workoutId")
    abstract fun getLastPositionInWorkout(workoutId: Int): Single<Int>
}
