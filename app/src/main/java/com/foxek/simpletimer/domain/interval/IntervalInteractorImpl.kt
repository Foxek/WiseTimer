package com.foxek.simpletimer.domain.interval

import com.foxek.simpletimer.data.database.IntervalDAO
import com.foxek.simpletimer.data.model.Interval

import javax.inject.Inject

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class IntervalInteractorImpl @Inject constructor(
    private val intervalDAO: IntervalDAO
) : IntervalInteractor {

    override fun observeIntervals(workoutId: Int): Flowable<List<Interval>> {
        return intervalDAO.observeAll(workoutId)
    }

    override fun getIntervals(workoutId: Int): Single<List<Interval>> {
        return intervalDAO.getAll(workoutId)
    }

    override fun addInterval(interval: Interval): Completable {
        return intervalDAO.getLastId()
            .flatMapCompletable {
                interval.position = it + 1
                Completable.fromAction { intervalDAO.add(interval) }
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
        return intervalDAO.getById(intervalId, workoutId)
            .flatMapCompletable {
                it.name = intervalName
                it.type = intervalType
                it.work = workTime
                it.rest = restTime
                Completable.fromAction { intervalDAO.update(it) }
            }
    }

    override fun deleteInterval(workoutId: Int, intervalId: Int): Completable {
        return intervalDAO.size(workoutId)
            .filter { it != 1 }
            .flatMapCompletable {
                Completable.fromAction { intervalDAO.delete(intervalId, workoutId) }
            }
    }
}