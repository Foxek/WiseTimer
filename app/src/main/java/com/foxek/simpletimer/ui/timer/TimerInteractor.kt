package com.foxek.simpletimer.ui.timer

import com.foxek.simpletimer.data.database.TimerDatabase
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.data.model.Time
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

    private var times = ArrayList<Time>()

    override fun deleteDependencies() {
        timer.stop()
    }

    override fun fetchIntervalList(workoutId: Int): Disposable {
        return database.intervalDAO.getAll(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { fetchTimeList(it) }
                .subscribe({ timer.prepare(times) }, {})
    }

    override fun getVolume(id: Int): Disposable {
        return database.workoutDAO.getVolume(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ volume -> timer.enableSound(volume) }, { })
    }

    override fun continueTimer() {
        timer.restart()
    }

    override fun stopTimer() {
        timer.stop()
    }

    override fun intervalFinishedCallback(): Observable<Time> {
        return timer.onIntervalFinished
    }

    override fun tickCallback(): Observable<Int> {
        return timer.onTimerTickHappened
    }

    override fun getTimerState(): IntervalTimer.State {
        return timer.state
    }

    private fun fetchTimeList(intervalList: List<Interval>) {

        times.add(Time(PREPARE_TIME, PREPARE_TIME_TYPE, 0, EMPTY))

        intervalList.forEachIndexed { idx, it ->
            times.add(Time(it.work, WORK_TIME_TYPE, idx + 1, it.name))

            if (it.type == WITH_REST_TYPE)
                times.add(Time(it.rest, REST_TIME_TYPE, idx + 1, it.name))
        }

        times.add(Time(0, POST_TIME_TYPE, times.lastIndex, null))
    }
}
