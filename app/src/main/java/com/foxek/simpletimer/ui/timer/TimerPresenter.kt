package com.foxek.simpletimer.ui.timer

import com.foxek.simpletimer.R
import com.foxek.simpletimer.ui.base.BasePresenter
import com.foxek.simpletimer.utils.Constants.POST_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.PREPARE_TIME_TYPE
import com.foxek.simpletimer.utils.Constants.REST_TIME_TYPE

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.schedulers.Schedulers

import com.foxek.simpletimer.utils.Constants.TIMER_PLAYING
import com.foxek.simpletimer.utils.Constants.TIMER_STOPPED
import com.foxek.simpletimer.utils.Constants.WORK_TIME_TYPE
import com.foxek.simpletimer.utils.formatIntervalData
import com.foxek.simpletimer.utils.formatIntervalNumber
import java.util.*

class TimerPresenter @Inject constructor(
        private var interactor: TimerContact.Interactor
) : BasePresenter<TimerContact.View>(), TimerContact.Presenter {

    private var currentInterval: Int = 0

    override fun detachView() {
        interactor.deleteDependencies()
        super.detachView()
    }

    override fun prepareIntervals(workoutId: Int) {

        disposable.add(interactor.getVolume(workoutId))

        disposable.add(interactor.fetchIntervalList(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    interactor.setTimerState(TIMER_PLAYING)
                    view?.showCounterType(R.string.timer_prepare)
                    view?.showPlayInterface()
                    interactor.loadIntervalListToTimer()
                }, {

                }))
    }

    override fun viewIsReady() {
        registerTimerCallback()
        registerTickCallback()
    }


    override fun pauseButtonClicked() {
        if (interactor.getTimerState()) {
            interactor.stopTimer()
            view?.showPauseInterface()
            interactor.setTimerState(TIMER_STOPPED)
        } else {
            interactor.continueTimer()
            view?.showPlayInterface()
            interactor.setTimerState(TIMER_PLAYING)
        }
    }

    override fun resetButtonClicked() {
        view?.startWorkoutActivity()
    }

    private fun registerTimerCallback() {
        disposable.add(interactor.intervalFinishedCallback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ time ->
                    when (time.type) {
                        REST_TIME_TYPE -> {
                            interactor.indicateEndOfInterval()
                            view?.showCounterType(R.string.timer_rest_time)
                        }
                        WORK_TIME_TYPE -> {
                            interactor.indicateEndOfInterval()
                            view?.showCounterType(R.string.timer_work_time)
                            view?.showCounterNumber(formatIntervalNumber(++currentInterval))
                            view?.showCounterName(time.name)
                        }
                        POST_TIME_TYPE -> {
                            interactor.indicateEndOfWorkout()
                            view?.startWorkoutActivity()
                        }
                    }
                }, { })
        )
    }

    private fun registerTickCallback() {
        disposable.add(interactor.tickCallback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ time -> view?.showCurrentCounter(formatIntervalData(time)) }, {})
        )
    }

}
