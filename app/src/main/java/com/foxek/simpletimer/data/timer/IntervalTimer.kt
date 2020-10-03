package com.foxek.simpletimer.data.timer

import android.os.CountDownTimer
import com.foxek.simpletimer.R
import com.foxek.simpletimer.common.utils.Constants
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.common.utils.Constants.POST_TIME_TYPE
import com.foxek.simpletimer.common.utils.Constants.REST_TIME_TYPE
import com.foxek.simpletimer.common.utils.Constants.WITH_REST_TYPE
import com.foxek.simpletimer.common.utils.Constants.WORK_TIME_TYPE
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.presentation.base.StringProvider

import javax.inject.Inject

import io.reactivex.subjects.PublishSubject

class IntervalTimer @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val stringProvider: StringProvider
) {

    enum class State (val buttonText: Int, val isRestartAllowed: Boolean) {
        PAUSED(R.string.timer_continue_button, true),
        STARTED(R.string.timer_pause_button, false)
    }

    private var timer: CountDownTimer? = null
    private var intervals = ArrayList<Interval>()
    private var currentTimeIndex: Int = 0
    private var pastTimeInSeconds: Long = 0

    val onTimerTickHappened = PublishSubject.create<Int>()
    val onIntervalFinished = PublishSubject.create<Interval>()
    var state = State.PAUSED

    fun prepare(roundList: List<Round>) {
        intervals.add(Interval(Constants.PREPARE_TIME, Constants.PREPARE_TIME_TYPE, "", 0))

        roundList.forEachIndexed { idx, it ->
            intervals.add(Interval(it.workInterval, WORK_TIME_TYPE, it.name,idx + 1))

            if (it.type == WITH_REST_TYPE)
                intervals.add(Interval(it.restInterval, REST_TIME_TYPE, it.name, idx + 1))
        }

        intervals.add(
            Interval(1, POST_TIME_TYPE, stringProvider.getString(R.string.timer_end_of_workout_hint), intervals.lastIndex))
        start(intervals[0].value.toLong())
    }

    fun getNextIntervalName(): String? {
        val nextInterval = intervals.find {
            ((it.roundId > intervals[currentTimeIndex].roundId)
               && (it.roundId != intervals[currentTimeIndex].roundId))
        }
        return nextInterval?.name
    }

    fun restart() {
        start(pastTimeInSeconds)
    }

    fun stop() {
        timer?.cancel()
        timer = null
        state = State.PAUSED
    }

    fun setSilentMode(isEnable: Boolean) {
        alarmHelper.setVolume(!isEnable)
    }

    private fun start(time: Long) {
        state = State.STARTED

        timer = object : CountDownTimer((time * 1000) - 1, 500) {

            override fun onFinish() {
                stop()
                handleFinish(intervals[++currentTimeIndex])
            }

            override fun onTick(millisUntilFinished: Long) {
                handleTick(millisUntilFinished)
            }
        }

        timer?.start()
    }

    private fun handleFinish(interval: Interval) {

        when (interval.type) {
            REST_TIME_TYPE -> indicateEndOfInterval()
            WORK_TIME_TYPE -> indicateEndOfInterval()
            POST_TIME_TYPE -> indicateEndOfWorkout()
        }

        onIntervalFinished.onNext(interval)

        if (currentTimeIndex < intervals.size - 1) {
            start(interval.value.toLong())
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
