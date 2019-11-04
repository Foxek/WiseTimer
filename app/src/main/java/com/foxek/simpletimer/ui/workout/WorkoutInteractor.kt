package com.foxek.simpletimer.ui.workout

import com.foxek.simpletimer.data.database.TimerDatabase
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.data.model.Workout

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import com.foxek.simpletimer.utils.Constants.EMPTY


class WorkoutInteractor @Inject constructor(
        private val database: TimerDatabase
) : WorkoutContact.Interactor {

    override fun fetchWorkoutList(): Flowable<List<Workout>> =
            database.workoutDAO.all
                    .subscribeOn(Schedulers.io())


    override fun createWorkout(name: String): Disposable =
            database.workoutDAO.lastId
                .defaultIfEmpty(0)
                .flatMapCompletable { id ->

                    val workout = Workout(name, id + 1, 1, true)
                    val interval = Interval(EMPTY, 1, 1, id + 1, 0)

                    Completable.concatArray(
                            Completable.fromAction { database.workoutDAO.add(workout) },
                            Completable.fromAction { database.intervalDAO.add(interval) }
                    )
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()


}
