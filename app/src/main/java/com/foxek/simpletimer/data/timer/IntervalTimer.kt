package com.foxek.simpletimer.data.timer

import android.os.CountDownTimer
import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.data.model.Time
import com.foxek.simpletimer.common.utils.Constants.POST_TIME_TYPE
import com.foxek.simpletimer.common.utils.Constants.REST_TIME_TYPE
import com.foxek.simpletimer.common.utils.Constants.WITH_REST_TYPE
import com.foxek.simpletimer.common.utils.Constants.WORK_TIME_TYPE
import com.foxek.simpletimer.data.model.Round

import javax.inject.Inject

import io.reactivex.subjects.PublishSubject

class IntervalTimer @Inject constructor(private val alarmHelper: AlarmHelper) {

    enum class State {
        STOPPED, STARTED
    }

    private var timer: CountDownTimer? = null
    private var times = ArrayList<Time>()
    private var currentTimeIndex: Int = 0
    private var pastTimeInSeconds: Long = 0

    val onTimerTickHappened = PublishSubject.create<Int>()
    val onIntervalFinished = PublishSubject.create<Time>()
    var state = State.STOPPED

    fun prepare(roundList: List<Round>) {
        times.add(Time(Constants.PREPARE_TIME, Constants.PREPARE_TIME_TYPE, 0, Constants.EMPTY, Constants.EMPTY))

        roundList.forEachIndexed { idx, it ->
            times.add(Time(it.workInterval, WORK_TIME_TYPE, idx + 1, it.name, getNextName(roundList, idx)))

            if (it.type == WITH_REST_TYPE)
                times.add(Time(it.restInterval, REST_TIME_TYPE, idx + 1, it.name, getNextName(roundList, idx)))
        }

        times.add(Time(1, POST_TIME_TYPE, times.lastIndex, Constants.EMPTY, Constants.EMPTY))
        start(times[0].value.toLong())
    }

    private fun getNextName(round: List<Round>, id: Int): String? {
        return if (id + 1 < round.size) {
            round[id + 1].name
        } else {
            "Завершение тренировки"
        }
    }

    fun restart() {
        start(pastTimeInSeconds)
    }

    fun stop() {
        timer?.cancel()
        timer = null
        state = State.STOPPED
    }

    fun enableSound(isEnable: Boolean) {
        alarmHelper.setVolume(isEnable)
    }

    private fun start(time: Long) {
        state = State.STARTED

        timer = object : CountDownTimer((time * 1000) - 1, 500) {

            override fun onFinish() {
                stop()
                handleFinish(times[++currentTimeIndex])
            }

            override fun onTick(millisUntilFinished: Long) {
                handleTick(millisUntilFinished)
            }
        }

        timer?.start()
    }

    private fun handleFinish(time: Time) {

        when (time.type) {
            REST_TIME_TYPE -> indicateEndOfInterval()
            WORK_TIME_TYPE -> indicateEndOfInterval()
            POST_TIME_TYPE -> indicateEndOfWorkout()
        }

        onIntervalFinished.onNext(time)

        if (currentTimeIndex < times.size - 1) {
            start(time.value.toLong())
        }
    }

    private fun handleTick(pastMillis: Long) {

        if ((pastMillis in 501..999) or (pastMillis in 1501..1999) or (pastMillis in 2501..2999))
            indicateLastSeconds()

        pastTimeInSeconds = pastMillis / 1000

        onTimerTickHappened.onNext((pastTimeInSeconds + 1).toInt())
    }

    private fun indicateEndOfWorkout() {
        alarmHelper.patternVibrate()
        alarmHelper.playFinalSound()
    }

    private fun indicateEndOfInterval() {
        alarmHelper.oneShotVibrate()
        alarmHelper.playLongSound()
    }

    private fun indicateLastSeconds() {
        alarmHelper.playSound()
    }
}
