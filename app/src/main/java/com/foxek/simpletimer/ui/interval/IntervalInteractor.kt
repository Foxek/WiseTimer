package com.foxek.simpletimer.ui.interval

import com.foxek.simpletimer.data.database.TimerDatabase
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.data.model.Workout

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class IntervalInteractor @Inject constructor(
        private val database: TimerDatabase
) : IntervalContact.Interactor {

    private var workoutId: Int = 0
    private var intervalId: Int = 0

    override fun getWorkout(id: Int): Single<Workout> {
        workoutId = id
        return database.workoutDAO.getById(workoutId)
                .subscribeOn(Schedulers.io())
    }

    override fun fetchIntervalList(): Flowable<List<Interval>> {
        return database.intervalDAO.getAll(workoutId)
                .subscribeOn(Schedulers.io())
    }

    override fun addInterval(name: String, type: Int, work: Int, rest: Int): Disposable {
        return database.intervalDAO.getLastId()
                .flatMapCompletable {
                    val interval = Interval(name, work, rest, workoutId, type,it + 1)
                    Completable.fromAction { database.intervalDAO.add(interval) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},{})
    }

    override fun updateInterval(name: String, type: Int, work: Int, rest: Int): Disposable {
        return database.intervalDAO.getById(intervalId, workoutId)
                .flatMapCompletable {
                    it.name = name
                    it.type = type
                    it.workTime = work
                    it.restTime = rest
                    Completable.fromAction { database.intervalDAO.update(it) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                },{ error -> })
    }

    override fun deleteInterval(): Disposable {
        return database.intervalDAO.size(workoutId)
                .filter { size -> size != 1 }
                .flatMapCompletable {
                    Completable.fromAction { database.intervalDAO.delete(intervalId, workoutId) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun updateWorkout(workoutName: String): Disposable {
        return Completable.fromAction { database.workoutDAO.update(workoutName, workoutId) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({},{})
    }

    override fun updateVolume(state: Boolean): Completable {
        return Completable.fromAction { database.workoutDAO.update(state, workoutId) }
                .subscribeOn(Schedulers.io())
    }

    override fun deleteWorkout(): Completable {
        return Completable.fromAction { database.workoutDAO.delete(workoutId) }
                .subscribeOn(Schedulers.io())
    }

    override fun getVolume(): Single<Boolean> {
        return database.workoutDAO.getVolume(workoutId)
                .subscribeOn(Schedulers.io())
    }

    override fun setCurrentInterval(id: Int) {
        intervalId = id
    }


}