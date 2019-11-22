package com.foxek.simpletimer.ui.timer

import com.foxek.simpletimer.utils.AlarmHelper
import com.foxek.simpletimer.utils.TimerHelper
import com.foxek.simpletimer.data.database.TimerDatabase
import com.foxek.simpletimer.data.model.Time
import com.foxek.simpletimer.utils.Constants


import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.PREPARE_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.PREPARE_TIME
import com.foxek.simpletimer.utils.Constants.REST_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.WITH_REST_TYPE
import com.foxek.simpletimer.utils.Constants.WORK_TIME_TYPE

class TimerInteractor @Inject constructor(
        private val database: TimerDatabase,
        private val alarmHelper: AlarmHelper,
        private val timerHelper: TimerHelper
) : TimerContact.Interactor {

    private var timeList = ArrayList<Time>()

    override fun deleteDependencies() {
        timerHelper.timerDelete()
    }

    override fun fetchIntervalList(workoutId: Int): Flowable<Int> {

        timeList.add(Time(PREPARE_TIME, PREPARE_TIME_TYPE, EMPTY))

        return database.intervalDAO.getAll(workoutId)
                .map {
                    for (interval in it) {
                        timeList.add(Time(interval.workTime, WORK_TIME_TYPE, interval.name))

                        if (interval.type == WITH_REST_TYPE)
                            timeList.add(Time(interval.restTime, REST_TIME_TYPE, interval.name))
                    }

                    timeList.add(Time(0, Constants.POST_TIME_TYPE, null))
                    return@map timeList.size
                }
    }

    override fun getVolume(id: Int): Disposable {
        return database.workoutDAO.getVolume(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ volume -> alarmHelper.setVolume(volume) }, { })
    }

    override fun loadIntervalListToTimer() {
        timerHelper.loadIntervalList(timeList)
    }

    override fun continueTimer() {
        timerHelper.timerRecreate()
    }

    override fun stopTimer() {
        timerHelper.timerDelete()
    }

    override fun intervalFinishedCallback(): Observable<Time> {
        return timerHelper.onIntervalFinished()

    }

    override fun tickCallback(): Observable<Int> {
        return timerHelper.onTimerTickHappened()
                .map { time ->
                    if (time in 501..999 || time in 1501..1999 || time in 2501..2999) {
                        indicateLastSeconds()
                    }
                    return@map (time / 1000 + 1).toInt()
                }
    }

    override fun getTimerState(): Boolean {
        return timerHelper.timerState
    }

    override fun setTimerState(timerState: Boolean) {
        timerHelper.timerState = timerState
    }

    override fun indicateEndOfWorkout() {
        alarmHelper.patternVibrate()
        alarmHelper.playFinalSound()
    }

    override fun indicateEndOfInterval() {
        alarmHelper.oneShotVibrate()
        alarmHelper.playLongSound()
    }

    override fun indicateLastSeconds() {
        alarmHelper.playSound()
    }
}
