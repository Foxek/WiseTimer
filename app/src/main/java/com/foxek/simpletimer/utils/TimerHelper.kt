package com.foxek.simpletimer.utils

import android.os.CountDownTimer
import com.foxek.simpletimer.data.model.Time
import com.foxek.simpletimer.utils.Constants.POST_TIME_TYPE

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

import com.foxek.simpletimer.utils.Constants.TIMER_STOPPED


class TimerHelper @Inject constructor() {

    private var timeList = ArrayList<Time>()
    private var currentTime: Int = 0
    private var counter: Long = 0
    private var timer: CountDownTimer? = null

    var timerState = TIMER_STOPPED

    private val onTickSubject = PublishSubject.create<Long>()
    private val onFinishSubject = PublishSubject.create<Time>()

    fun loadIntervalList(timeIntervals: ArrayList<Time>) {
        timeList = timeIntervals
        timerCreate(timeList[0].time.toLong())
    }

    fun onTimerTickHappened(): Observable<Long> {
        return onTickSubject
    }

    fun onIntervalFinished(): Observable<Time> {
        return onFinishSubject
    }

    private fun timerCreate(time: Long) {
        timer = object : CountDownTimer(time * 1000, 500) {

            override fun onFinish() {
                timerDelete()
                onFinishSubject.onNext(timeList[currentTime + 1])

                if (currentTime < timeList.size - 2) {
                    timerCreate(timeList[++currentTime].time.toLong())
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                counter = millisUntilFinished / 1000
                onTickSubject.onNext(millisUntilFinished)
            }
        }.start()
    }

    fun timerRecreate() {
        timerCreate(counter)
    }

    fun timerDelete() {
        if (timer != null) {
            timer?.cancel()
            timer = null
        }
    }
}
