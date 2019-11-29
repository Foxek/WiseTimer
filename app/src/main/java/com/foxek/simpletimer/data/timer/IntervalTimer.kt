package com.foxek.simpletimer.data.timer

import android.os.CountDownTimer
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Time
import com.foxek.simpletimer.utils.Constants
import com.foxek.simpletimer.utils.Constants.POST_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.REST_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.WORK_TIME_TYPE
import com.foxek.simpletimer.utils.formatIntervalNumber

import javax.inject.Inject

import io.reactivex.subjects.PublishSubject

class IntervalTimer @Inject constructor(
        private val alarmHelper: AlarmHelper
) {
    enum class State {
        STOPPED, STARTED
    }

    private var timeList = ArrayList<Time>()
    private var currentIdx: Int = 0
    private var counter: Long = 0

    private var timer: CountDownTimer? = null
    var state = State.STOPPED

    private val onTickSubject = PublishSubject.create<Int>()
    private val onFinishSubject = PublishSubject.create<Time>()

    fun prepare(timeIntervals: ArrayList<Time>) {
        timeList = timeIntervals
        start(timeList[0].time.toLong())
    }

    fun onTimerTickHappened() = onTickSubject
    fun onIntervalFinished() = onFinishSubject

    fun restart() {
        start(counter)
    }

    fun stop() {
        timer?.cancel()
        timer = null
        state = State.STOPPED
    }

    fun setVolume(volume: Boolean){
        alarmHelper.setVolume(volume)
    }

    private fun start(time: Long) {
        state = State.STARTED

        timer = object : CountDownTimer(time * 1000, 500) {

            override fun onFinish() {
                handleFinish(timeList[++currentIdx])
            }

            override fun onTick(millisUntilFinished: Long) {
                handleTick(millisUntilFinished)
            }
        }

        timer?.start()
    }

    private fun handleFinish(time: Time){
        stop()

        when (time.type) {
            REST_TIME_TYPE -> indicateEndOfInterval()
            WORK_TIME_TYPE -> indicateEndOfInterval()
            POST_TIME_TYPE -> indicateEndOfWorkout()
        }

        onFinishSubject.onNext(time)

        if (currentIdx < timeList.size - 1) {
            start(time.time.toLong())
        }
    }

    private fun handleTick(timeInMillis: Long){
        indicateLastSeconds(timeInMillis)
        counter = timeInMillis / 1000
        onTickSubject.onNext((counter + 1).toInt())
    }

    private fun indicateEndOfWorkout() {
        alarmHelper.patternVibrate()
        alarmHelper.playFinalSound()
    }

    private fun indicateEndOfInterval() {
        alarmHelper.oneShotVibrate()
        alarmHelper.playLongSound()
    }

    private fun indicateLastSeconds(time: Long) {
        if ((time in 501..999) or (time in 1501..1999) or (time in 2501..2999))
           alarmHelper.playSound()
    }
}
