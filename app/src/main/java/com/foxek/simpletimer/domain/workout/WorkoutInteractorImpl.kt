package com.foxek.simpletimer.domain.workout

import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.data.database.TimerDAO
import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class WorkoutInteractorImpl @Inject constructor(
    private val timerDAO: TimerDAO
) : WorkoutInteractor {

    override fun getWorkoutById(workoutId: Int): Single<Workout> {
        return timerDAO.getWorkoutById(workoutId)
    }

    override fun createWorkout(workoutName: String): Completable {
        return timerDAO.getLastWorkoutId()
            .defaultIfEmpty(0)
            .flatMapCompletable {
                Completable.fromAction {
                    timerDAO.addNewWorkout(
                        Workout(it + 1, workoutName, 1, true),
                        Round(Constants.EMPTY, 0,1, 1, it + 1, 0)
                    )
                }
            }
    }

    override fun observeWorkouts(): Flowable<List<Workout>> {
        return timerDAO.observeWorkouts()
    }

    override fun deleteWorkoutById(workoutId: Int): Completable {
        return Completable.fromAction { timerDAO.deleteWorkout(workoutId) }
    }

    override fun updateWorkout(workoutId: Int, workoutName: String, rounds: List<Round>): Completable {
        return Completable.fromRunnable { timerDAO.updateWorkout(workoutId, workoutName, rounds) }
    }

    override fun getWorkoutVolumeState(workoutId: Int): Single<Boolean> {
        return timerDAO.getWorkoutVolumeState(workoutId)
    }

    override fun toggleWorkoutVolumeState(workoutId: Int): Single<Boolean> {
        return timerDAO.getWorkoutVolumeState(workoutId)
            .map { isEnabled ->
                timerDAO.updateWorkoutVolumeState(!isEnabled, workoutId)
                !isEnabled
            }
    }
}
