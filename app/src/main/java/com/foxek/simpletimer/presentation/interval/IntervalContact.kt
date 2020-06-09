package com.foxek.simpletimer.presentation.interval

import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.data.model.Workout
import com.foxek.simpletimer.presentation.base.MvpPresenter
import com.foxek.simpletimer.presentation.base.MvpView

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

        var workoutId: Int

        fun saveIntervalButtonClicked(name: String, type: Int, workTime: Int, restTime: Int)

        fun createIntervalButtonClicked(name: String, type: Int, workTime: Int, restTime: Int)

        fun deleteIntervalButtonClicked()

        fun saveWorkoutButtonClicked(name: String)

        fun editWorkoutButtonClicked()

        fun deleteWorkoutButtonClicked()

        fun startWorkoutButtonClicked()

        fun changeVolumeButtonClicked()

        fun addIntervalButtonClicked()

        fun intervalItemClicked(item: Interval)
    }
}