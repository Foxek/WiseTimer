package com.foxek.simpletimer.domain.interval

import com.foxek.simpletimer.data.model.Interval
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface IntervalInteractor {

    fun observeIntervals(workoutId: Int): Flowable<List<Interval>>

    fun getIntervals(workoutId: Int): Single<List<Interval>>

    fun addInterval(interval: Interval): Completable

    fun updateInterval(
        workoutId: Int,
        intervalId: Int,
        intervalName: String,
        intervalType: Int,
        workTime: Int,
        restTime: Int
    ): Completable

    fun deleteInterval(workoutId: Int, intervalId: Int): Completable
}