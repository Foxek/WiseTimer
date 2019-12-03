package com.foxek.simpletimer.ui.timer

import com.foxek.simpletimer.data.model.Time
import com.foxek.simpletimer.data.timer.IntervalTimer

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface TimerContact {

    interface ServiceCallback {

        fun startWorkoutActivity()

        fun showPauseInterface()

        fun showPlayInterface()

        fun showCurrentCounter(time: String)

        fun showCounterType(type: Int)

        fun showCounterNumber(number: String)

        fun showCounterName(name: String?)
    }

    interface Interactor {

        fun getTimerState(): IntervalTimer.State

        fun fetchIntervalList(workoutId: Int): Disposable

        fun intervalFinishedCallback(): Observable<Time>

        fun tickCallback(): Observable<Int>

        fun getVolume(id: Int): Disposable

        fun continueTimer()

        fun stopTimer()

        fun deleteDependencies()
    }
}