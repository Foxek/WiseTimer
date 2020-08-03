package com.foxek.simpletimer.domain.workout

import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.data.model.Workout
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface WorkoutInteractor {
    fun observeWorkouts(): Flowable<List<Workout>>

    fun getWorkoutById(workoutId: Int): Single<Workout>

    fun createWorkout(workoutName: String): Completable

    fun deleteWorkoutById(workoutId: Int): Completable

    fun updateWorkout(workoutId: Int, workoutName: String, rounds: List<Round>): Completable

    fun getWorkoutVolumeState(workoutId: Int): Single<Boolean>

    fun toggleWorkoutVolumeState(workoutId: Int): Single<Boolean>
}