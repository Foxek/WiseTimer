package com.foxek.simpletimer.utils;

import android.os.CountDownTimer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static com.foxek.simpletimer.utils.Constants.TIMER_STOPPED;


public class TimerHelper {

    private List<Integer> intervalList;
    private int currentInterval;
    private long currentCount;
    private boolean timerState = TIMER_STOPPED;
    private CountDownTimer timer;

    private final PublishSubject<Long> onTickSubject = PublishSubject.create();
    private final PublishSubject<Integer> onFinishSubject = PublishSubject.create();

    @Inject
    TimerHelper() {

    }

    public void loadIntervalList(List<Integer> timeIntervals) {
        this.intervalList = timeIntervals;
        timerCreate(this.intervalList.get(0));
    }

    public Observable<Long> onTimerTickHappened() {
        return onTickSubject;
    }

    public Observable<Integer> onIntervalFinished() {
        return onFinishSubject;
    }

    private void timerCreate(long time) {
        time *= 1000;
        timer = new CountDownTimer(time, 500) {

            @Override
            public void onFinish() {
                timerDelete();
                onFinishSubject.onNext(currentInterval + 1);
                if (currentInterval < intervalList.size() - 1) {
                    currentInterval++;
                    timerCreate(intervalList.get(currentInterval));
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
                currentCount = millisUntilFinished / 1000;
                onTickSubject.onNext(millisUntilFinished);
            }
        }.start();
    }

    public void timerRecreate() {
        timerCreate(currentCount);
    }

    public void timerDelete() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public boolean getTimerState() {
        return timerState;
    }

    public void setTimerState(boolean timerState) {
        this.timerState = timerState;
    }
}
