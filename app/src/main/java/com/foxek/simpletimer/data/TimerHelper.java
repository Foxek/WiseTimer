package com.foxek.simpletimer.data;

import android.os.CountDownTimer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static com.foxek.simpletimer.common.Constants.TIMER_STOPPED;


public class TimerHelper {

    private List<Integer>       mTimeIntervals;
    private int                 mCurrentInterval;
    private long                 mCurrentCount;
    private boolean             mCurrentTimerState = TIMER_STOPPED;
    private CountDownTimer      mCurentTimer;

    private final PublishSubject<Long> onTickSubject = PublishSubject.create();
    private final PublishSubject<Integer> onFinishSubject = PublishSubject.create();

    @Inject
    public TimerHelper(){

    }

    public void loadIntervalList(List<Integer> timeIntervals){
        mTimeIntervals = timeIntervals;
        timerCreate(mTimeIntervals.get(0));
    }

    public Observable<Long> onTimerTickHappened(){
        return onTickSubject;
    }

    public Observable<Integer> onIntervalFinished(){
        return onFinishSubject;
    }

    private void timerCreate(long time){
        time *= 1000;
        mCurentTimer = new CountDownTimer(time, 500){

            @Override
            public void onFinish() {
                timerDelete();
                onFinishSubject.onNext(mCurrentInterval+1);
                if (mCurrentInterval < mTimeIntervals.size()-1){
                    mCurrentInterval++;
                    timerCreate(mTimeIntervals.get(mCurrentInterval));
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentCount = millisUntilFinished / 1000;
                onTickSubject.onNext(millisUntilFinished);
            }
        }.start();
    }

    public void timerRecreate(){
        timerCreate(mCurrentCount);
    }

    public void timerDelete(){
        if (mCurentTimer != null) {
            mCurentTimer.cancel();
            mCurentTimer = null;
        }
    }

    public boolean getTimerState(){
        return mCurrentTimerState;
    }

    public void setTimerState(boolean timerState){
        mCurrentTimerState = timerState;
    }
}
