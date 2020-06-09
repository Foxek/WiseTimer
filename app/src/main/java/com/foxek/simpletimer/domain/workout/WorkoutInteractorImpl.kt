package com.foxek.simpletimer.domain.workout

import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.data.database.WorkoutDAO
import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Single

class WorkoutInteractorImpl @Inject constructor(
    private val workoutDAO: WorkoutDAO
) : WorkoutInteractor {

    override fun getWorkoutById(workoutId: Int): Single<Workout> {
        return workoutDAO.getById(workoutId)
    }

    override fun createWorkout(workoutName: String): Completable {
        return workoutDAO.getLastId()
            .defaultIfEmpty(0)
            .flatMapCompletable {
                Completable.fromAction {
                    workoutDAO.addNewWorkout(
                        Workout(workoutName, it + 1, 1, true),
                        Interval(Constants.EMPTY, 1, 1, it + 1, 0, 0)
                    )
                }
            }
    }

    override fun observeWorkouts(): Flowable<List<Workout>> {
        return workoutDAO.getAll()
    }

    override fun updateWorkoutName(workoutId: Int, workoutName: String): Completable {
        return Completable.fromAction { workoutDAO.update(workoutName, workoutId) }
    }

    override fun deleteWorkoutById(workoutId: Int): Completable {
        return Completable.fromAction { workoutDAO.delete(workoutId) }
    }

    override fun getWorkoutVolumeState(workoutId: Int): Single<Boolean> {
        return workoutDAO.getVolume(workoutId)
    }

    override fun toggleWorkoutVolumeState(workoutId: Int): Single<Boolean> {
        return workoutDAO.getVolume(workoutId)
            .map { isEnabled ->
                workoutDAO.update(!isEnabled, workoutId)
                !isEnabled
            }
    }
}
