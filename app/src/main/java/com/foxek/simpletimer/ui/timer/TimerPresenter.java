package com.foxek.simpletimer.ui.timer;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.foxek.simpletimer.utils.Constants.TIMER_PLAYING;
import static com.foxek.simpletimer.utils.Constants.TIMER_STOPPED;
import static com.foxek.simpletimer.utils.IntervalUtils.formatIntervalData;
import static com.foxek.simpletimer.utils.IntervalUtils.formatIntervalNumber;

public class TimerPresenter extends BasePresenter<TimerContact.View> implements TimerContact.Presenter {

    TimerContact.Interactor interactor;

    private int currentInterval;
    @Inject
    public TimerPresenter(TimerContact.Interactor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void detachView() {
        interactor.deleteDependencies();
        super.detachView();
    }

    @Override
    public void prepareIntervals(int workoutId) {

        getDisposable().add(interactor.getVolume(workoutId));


        getDisposable().add(interactor.fetchIntervalList(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intervalSize -> {
                    interactor.setTimerState(TIMER_PLAYING);
                    getView().showCounterType(R.string.timer_prepare);
                    getView().showPlayInterface();
                    interactor.loadIntervalListToTimer();
                }, throwable -> {
                }));
    }

    @Override
    public void viewIsReady() {
        registerTimerCallback();
        registerTickCallback();
    }


    @Override
    public void pauseButtonClicked() {
        if (interactor.getTimerState()) {
            interactor.stopTimer();
            getView().showPauseInterface();
            interactor.setTimerState(TIMER_STOPPED);
        } else {
            interactor.continueTimer();
            getView().showPlayInterface();
            interactor.setTimerState(TIMER_PLAYING);
        }
    }

    @Override
    public void resetButtonClicked() {
        getView().startWorkoutActivity();
    }

    private void registerTimerCallback() {
        getDisposable().add(interactor.intervalFinishedCallback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intervalNumber -> {
                    if (intervalNumber < interactor.getIntervalSize()) {
                        interactor.indicateEndOfInterval();
                        if ((intervalNumber & 1) == 0)
                            getView().showCounterType(R.string.timer_rest_time);
                        else {
                            getView().showCounterType(R.string.timer_work_time);
                            getView().showCounterNumber(formatIntervalNumber(++currentInterval));
                            getView().showCounterName(interactor.getIntervalName(currentInterval));
                        }
                    } else {
                        interactor.indicateEndOfWorkout();
                        getView().startWorkoutActivity();
                    }
                }, throwable -> {
                })
        );
    }

    private void registerTickCallback() {
        getDisposable().add(interactor.tickCallback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time -> getView().showCurrentCounter(formatIntervalData(time)),
                        throwable -> {
                        }
                )
        );
    }

}
