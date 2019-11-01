package com.foxek.simpletimer.utils

import android.os.CountDownTimer

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

import com.foxek.simpletimer.utils.Constants.TIMER_STOPPED


class TimerHelper @Inject constructor() {

    private var intervals: List<Int>? = null
    private var currentInterval: Int = 0
    private var currentCount: Long = 0
    var timerState = TIMER_STOPPED
    private var timer: CountDownTimer? = null

    private val onTickSubject = PublishSubject.create<Long>()
    private val onFinishSubject = PublishSubject.create<Int>()

    fun loadIntervalList(timeIntervals: List<Int>) {
        intervals = timeIntervals
        timerCreate(intervals!![0].toLong())
    }

    fun onTimerTickHappened(): Observable<Long> {
        return onTickSubject
    }

    fun onIntervalFinished(): Observable<Int> {
        return onFinishSubject
    }

    private fun timerCreate(time: Long) {
        timer = object : CountDownTimer(time*1000, 500) {

            override fun onFinish() {
                timerDelete()
                onFinishSubject.onNext(currentInterval + 1)
                if (currentInterval < intervals!!.size - 1) {
                    currentInterval++
                    timerCreate(intervals!![currentInterval].toLong())
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                currentCount = millisUntilFinished / 1000
                onTickSubject.onNext(millisUntilFinished)
            }
        }.start()
    }

    fun timerRecreate() {
        timerCreate(currentCount)
    }

    fun timerDelete() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }
}
