package com.foxek.simpletimer.domain.workout

import com.foxek.simpletimer.data.model.Workout
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface WorkoutInteractor {
    fun observeWorkouts(): Flowable<List<Workout>>

    fun getWorkoutById(workoutId: Int): Single<Workout>

    fun createWorkout(workoutName: String): Completable

    fun deleteWorkoutById(workoutId: Int): Completable

    fun updateWorkoutName(workoutId: Int, workoutName: String): Completable

    fun getWorkoutVolumeState(workoutId: Int): Single<Boolean>

    fun toggleWorkoutVolumeState(workoutId: Int): Single<Boolean>
}