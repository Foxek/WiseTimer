package com.foxek.simpletimer.ui.timer

import com.foxek.simpletimer.data.database.TimerDatabase
import com.foxek.simpletimer.data.model.Time
import com.foxek.simpletimer.data.timer.AlarmHelper
import com.foxek.simpletimer.utils.Constants.EMPTY
import com.foxek.simpletimer.utils.Constants.POST_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.PREPARE_TIME
import com.foxek.simpletimer.utils.Constants.PREPARE_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.REST_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.WITH_REST_TYPE
import com.foxek.simpletimer.utils.Constants.WORK_TIME_TYPE
import com.foxek.simpletimer.data.timer.IntervalTimer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

class TimerInteractor @Inject constructor(
        private val database: TimerDatabase,
        private val timer: IntervalTimer
) : TimerContact.Interactor {

    private var timeList = ArrayList<Time>()

    override fun deleteDependencies() {
        timer.stop()
    }

    override fun fetchIntervalList(workoutId: Int): Disposable {
        return database.intervalDAO.getAll(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{
                    timeList.add(Time(PREPARE_TIME, PREPARE_TIME_TYPE, 0, EMPTY))
                }
                .map{
                    for (time in it) {
                        timeList.add(Time(time.work, WORK_TIME_TYPE, it.indexOf(time) + 1, time.name))

                        if (time.type == WITH_REST_TYPE)
                            timeList.add(Time(time.rest, REST_TIME_TYPE, it.indexOf(time) + 1, time.name))
                    }
                    return@map it
                }
                .subscribe({
                    timeList.add(Time(0, POST_TIME_TYPE, timeList.lastIndex, null))
                    timer.prepare(timeList)
                }, {})
    }

    override fun getVolume(id: Int): Disposable {
        return database.workoutDAO.getVolume(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ volume -> timer.setVolume(volume) }, { })
    }

    override fun continueTimer() {
        timer.restart()
    }

    override fun stopTimer() {
        timer.stop()
    }

    override fun intervalFinishedCallback(): Observable<Time> {
        return timer.onIntervalFinished()
    }

    override fun tickCallback(): Observable<Int> {
        return timer.onTimerTickHappened()
    }

    override fun getTimerState(): IntervalTimer.State {
        return timer.state
    }
}
