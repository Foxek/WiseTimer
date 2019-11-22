package com.foxek.simpletimer.ui.timer

import com.foxek.simpletimer.data.model.Time
import com.foxek.simpletimer.ui.base.MvpPresenter
import com.foxek.simpletimer.ui.base.MvpView

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface TimerContact {
    interface View : MvpView {

        fun startWorkoutActivity()

        fun showPauseInterface()

        fun showPlayInterface()

        fun showCurrentCounter(time: String)

        fun showCounterType(type: Int)

        fun showCounterNumber(number: String)

        fun showCounterName(name: String?)
    }

    interface Presenter : MvpPresenter<View> {

        fun prepareIntervals(workoutId: Int)

        fun pauseButtonClicked()

        fun resetButtonClicked()
    }

    interface Interactor {

        fun getTimerState(): Boolean

        fun fetchIntervalList(workoutId: Int): Flowable<Int>

        fun intervalFinishedCallback(): Observable<Time>

        fun tickCallback(): Observable<Int>

        fun getVolume(id: Int): Disposable

        fun loadIntervalListToTimer()

        fun continueTimer()

        fun stopTimer()

        fun indicateEndOfWorkout()

        fun indicateEndOfInterval()

        fun indicateLastSeconds()

        fun deleteDependencies()

        fun setTimerState(timerState: Boolean)
    }
}
