package com.foxek.simpletimer.ui.timer;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.foxek.simpletimer.utils.Constants.TIMER_PLAYING;
import static com.foxek.simpletimer.utils.Constants.TIMER_STOPPED;
import static com.foxek.simpletimer.utils.IntervalUtils.formatIntervalData;
import static com.foxek.simpletimer.utils.IntervalUtils.formatIntervalNumber;

public class TimerPresenter extends BasePresenter<TimerContact.View,TimerContact.Interactor> implements TimerContact.Presenter{

    private int currentInterval;

    public TimerPresenter(TimerContact.Interactor mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }

    @Override
    public void detachView() {
        getInteractor().deleteDependencies();
        super.detachView();
    }

    @Override
    public void prepareIntervals(int workoutId){

        getDisposable().add(getInteractor().getVolume(workoutId));


        getDisposable().add(getInteractor().fetchIntervalList(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intervalSize -> {
                    getInteractor().setTimerState(TIMER_PLAYING);
                    getView().showCounterType(R.string.timer_prepare);
                    getView().showPlayInterface();
                    getInteractor().loadIntervalListToTimer();
                }, throwable -> {}));
    }

    @Override
    public void viewIsReady() {
        registerTimerCallback();
        registerTickCallback();
    }



    @Override
    public void pauseButtonClicked(){
        if (getInteractor().getTimerState()) {
            getInteractor().stopTimer();
            getView().showPauseInterface();
            getInteractor().setTimerState(TIMER_STOPPED);
        }else{
            getInteractor().continueTimer();
            getView().showPlayInterface();
            getInteractor().setTimerState(TIMER_PLAYING);
        }
    }

    @Override
    public void resetButtonClicked(){
        getView().startWorkoutActivity();
    }

    private void registerTimerCallback() {
        getDisposable().add(getInteractor().intervalFinishedCallback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intervalNumber -> {
                    if (intervalNumber < getInteractor().getIntervalSize()) {
                        getInteractor().indicateEndOfInterval();
                        if ((intervalNumber & 1) == 0)
                            getView().showCounterType(R.string.timer_rest_time);
                        else {
                            getView().showCounterType(R.string.timer_work_time);
                            getView().showCounterNumber(formatIntervalNumber(++currentInterval));
                        }
                    }
                    else {
                        getInteractor().indicateEndOfWorkout();
                        getView().startWorkoutActivity();
                    }
                }, throwable -> {})
        );
    }
    private void registerTickCallback() {
        getDisposable().add(getInteractor().tickCallback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time-> getView().showCurrentCounter(formatIntervalData(time)),
                        throwable -> {}
                )
        );
    }

}
