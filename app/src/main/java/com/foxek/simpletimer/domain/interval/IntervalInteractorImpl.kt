package com.foxek.simpletimer.domain.interval

import com.foxek.simpletimer.data.database.TimerDAO
import com.foxek.simpletimer.data.model.Interval

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class IntervalInteractorImpl @Inject constructor(
    private val timerDAO: TimerDAO
) : IntervalInteractor {

    override fun observeIntervals(workoutId: Int): Flowable<List<Interval>> {
        return timerDAO.observeIntervals(workoutId)
    }

    override fun getIntervals(workoutId: Int): Single<List<Interval>> {
        return timerDAO.getIntervals(workoutId)
    }

    override fun addInterval(interval: Interval): Completable {
        return timerDAO.getLastIntervalId()
            .flatMapCompletable {
                interval.position = it + 1
                Completable.fromAction { timerDAO.addInterval(interval) }
            }
    }

    override fun updateInterval(
        workoutId: Int,
        intervalId: Int,
        intervalName: String,
        intervalType: Int,
        workTime: Int,
        restTime: Int
    ): Completable {
        return timerDAO.getIntervalById(intervalId, workoutId)
            .flatMapCompletable {
                it.name = intervalName
                it.type = intervalType
                it.work = workTime
                it.rest = restTime
                Completable.fromAction { timerDAO.updateInterval(it) }
            }
    }

    override fun deleteInterval(workoutId: Int, intervalId: Int): Completable {
        return timerDAO.getNumberOfIntervalForWorkout(workoutId)
            .filter { it != 1 }
            .flatMapCompletable {
                Completable.fromAction { timerDAO.deleteInterval(intervalId, workoutId) }
            }
    }
}