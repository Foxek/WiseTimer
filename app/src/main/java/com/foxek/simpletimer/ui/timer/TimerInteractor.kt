package com.foxek.simpletimer.ui.timer

import com.foxek.simpletimer.utils.AlarmHelper
import com.foxek.simpletimer.utils.TimerHelper
import com.foxek.simpletimer.data.database.TimerDatabase


import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.PREPARE_INTERVAL

class TimerInteractor @Inject constructor(
        private val database: TimerDatabase,
        private val alarmHelper: AlarmHelper,
        private val timerHelper: TimerHelper
) : TimerContact.Interactor {

    private var intervalList: ArrayList<Int>? = null
    private var nameList: ArrayList<String>? = null

    override fun deleteDependencies() {
        timerHelper.timerDelete()
    }

    override fun getIntervalName(id: Int): String {
        return nameList!![id]
    }

    override fun fetchIntervalList(workoutId: Int): Flowable<Int> {
        intervalList = ArrayList()
        nameList = ArrayList()
        intervalList!!.add(PREPARE_INTERVAL)
        nameList!!.add(EMPTY)
        return database.intervalDAO.getAll(workoutId)
                .map { intervals ->
                    for (interval in intervals) {
                        intervalList!!.add(interval.workTime)
                        intervalList!!.add(interval.restTime)
                        nameList!!.add(interval.name!!)
                    }
                    intervalList!!.size
                }
    }

    override fun getVolume(id: Int): Disposable {
        return database.workoutDAO.getVolume(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ volume -> alarmHelper.setVolume(volume!!) }, { })
    }

    override fun loadIntervalListToTimer() {
        timerHelper.loadIntervalList(intervalList!!)
    }

    override fun continueTimer() {
        timerHelper.timerRecreate()
    }

    override fun stopTimer() {
        timerHelper.timerDelete()
    }

    override fun intervalFinishedCallback(): Observable<Int> {
        return timerHelper.onIntervalFinished()

    }

    override fun tickCallback(): Observable<Int> {
        return timerHelper.onTimerTickHappened()
                .map { time ->
                    if (time in 501..999 || time in 1501..1999 ||
                            time in 2501..2999)
                        indicateLastSeconds()
                    (time / 1000 + 1).toInt()
                }
    }

    override fun getTimerState(): Boolean {
        return timerHelper.timerState
    }

    override fun setTimerState(timerState: Boolean) {
        timerHelper.timerState = timerState
    }

    override fun getIntervalSize(): Int {
        return intervalList!!.size
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
