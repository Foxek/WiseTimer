package com.foxek.simpletimer.ui.interval

import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.ui.base.MvpPresenter
import com.foxek.simpletimer.ui.base.MvpView

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface IntervalContact {

    interface View : MvpView {

        fun setIntervalList()

        fun renderIntervalList(intervalList: List<Interval>)

        fun setVolumeState(state: Boolean)

        fun setWorkoutName(name: String)

        fun startWorkoutActivity()

        fun startTimerActivity()

        fun showIntervalEditDialog(interval: Interval)

        fun showIntervalCreateDialog()

        fun showWorkoutEditDialog()
    }

    interface Presenter : MvpPresenter<View> {

        fun viewIsReady(id: Int)

        fun saveIntervalButtonClicked(name: String, workTime: Int, restTime: Int)

        fun createIntervalButtonClicked(name: String, workTime: Int, restTime: Int)

        fun deleteIntervalButtonClicked()

        fun saveWorkoutButtonClicked(name: String)

        fun editWorkoutButtonClicked()

        fun deleteWorkoutButtonClicked()

        fun startWorkoutButtonClicked()

        fun changeVolumeButtonClicked()

        fun addIntervalButtonClicked()

        fun intervalItemClicked(item: Interval)
    }

    interface Interactor {

        fun getVolume(): Single<Boolean>

        fun fetchIntervalList(): Flowable<List<Interval>>

        fun addInterval(name: String, work: Int, rest: Int): Disposable

        fun updateInterval(name: String, work: Int, rest: Int): Disposable

        fun deleteInterval(): Disposable

        fun getWorkout(id: Int): Single<Workout>

        fun updateWorkout(workoutName: String): Disposable

        fun deleteWorkout(): Completable

        fun updateVolume(state: Boolean): Completable

        fun setCurrentInterval(id: Int)
    }
}