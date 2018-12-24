package com.foxek.simpletimer.ui.timer;

import com.foxek.simpletimer.R;
import com.foxek.simpletimer.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.foxek.simpletimer.common.Constants.TIMER_PLAYING;
import static com.foxek.simpletimer.common.Constants.TIMER_STOPPED;
import static com.foxek.simpletimer.data.database.model.IntervalUtils.formatIntervalData;
import static com.foxek.simpletimer.data.database.model.IntervalUtils.formatIntervalNumber;

public class TimerPresenter extends BasePresenter<TimerContact.View> implements TimerContact.Presenter{

    private CompositeDisposable         mDisposable;
    private TimerInteractor             mInteractor;
    private int                         mCurrentInterval;

    public TimerPresenter(TimerInteractor interactor){
        mInteractor = interactor;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        super.detachView();
        mInteractor.deleteDependencies();
        mInteractor = null;
        mDisposable.dispose();
    }

    @Override
    public void prepareIntervals(int workoutId){
        mDisposable.add(mInteractor.fetchIntervalList(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intervalSize -> {
                    mInteractor.setTimerState(TIMER_PLAYING);
                    getView().showCounterType(R.string.timer_prepare);
                    getView().showPlayInterface();
                    mInteractor.loadIntervalListToTimer();
                }, throwable -> {}));
    }

    @Override
    public void viewIsReady() {
        registerTimerCallback();
        registerTickCallback();
    }



    @Override
    public void pauseButtonPressed(){
        if (mInteractor.getTimerState()) {
            mInteractor.stopTimer();
            getView().showPauseInterface();
            mInteractor.setTimerState(TIMER_STOPPED);
        }else{
            mInteractor.continueTimer();
            getView().showPlayInterface();
            mInteractor.setTimerState(TIMER_PLAYING);
        }
    }

    @Override
    public void resetButtonPressed(){
        getView().startWorkoutActivity();
    }

    private void registerTimerCallback() {
        mDisposable.add(mInteractor.IntervalFinishedCallback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intervalNumber -> {
                    if (intervalNumber < mInteractor.getIntervalSize()) {
                        mInteractor.indicateEndOfInterval();
                        if ((intervalNumber & 1) == 0)
                            getView().showCounterType(R.string.timer_rest_time);
                        else {
                            getView().showCounterType(R.string.timer_work_time);
                            getView().showCounterNumber(formatIntervalNumber(++mCurrentInterval));
                        }
                    }
                    else {
                        mInteractor.indicateEndOfWorkout();
                        getView().startWorkoutActivity();
                    }
                }, throwable -> {})
        );
    }
    private void registerTickCallback() {
        mDisposable.add(mInteractor.onTickCallback()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(time-> getView().showCurrentCounter(formatIntervalData(time)),
                        throwable -> {}
                )
        );
    }

}
