package com.foxek.simpletimer.ui.timer;

import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.utils.AlarmHelper;
import com.foxek.simpletimer.utils.TimerHelper;
import com.foxek.simpletimer.data.database.TimerDatabase;


import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.foxek.simpletimer.utils.Constants.EMPTY;
import static com.foxek.simpletimer.utils.Constants.PREPARE_INTERVAL;

public class TimerInteractor implements TimerContact.Interactor {

    private TimerDatabase database;
    private ArrayList<Integer> intervalList;
    private ArrayList<String> nameList;
    private TimerHelper timerHelper;
    private AlarmHelper alarmHelper;

    @Inject
    TimerInteractor(TimerDatabase database, AlarmHelper alarmHelper, TimerHelper timerHelper) {
        this.database = database;
        this.timerHelper = timerHelper;
        this.alarmHelper = alarmHelper;
    }

    public void deleteDependencies() {
        timerHelper.timerDelete();
    }

    @Override
    public String getIntervalName(int id) {
        return nameList.get(id);
    }

    @Override
    public Flowable<Integer> fetchIntervalList(int workoutId) {
        intervalList = new ArrayList<>();
        nameList = new ArrayList<>();
        intervalList.add(PREPARE_INTERVAL);
        nameList.add(EMPTY);
        return database.getIntervalDAO().getAll(workoutId)
                .map(intervals -> {
                    for (Interval interval : intervals) {
                        intervalList.add(interval.getWorkTime());
                        intervalList.add(interval.getRestTime());
                        nameList.add((interval.getName()));
                    }
                    return intervalList.size();
                });
    }

    @Override
    public Disposable getVolume(int id) {
        return database.getWorkoutDAO().getVolume(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(volume -> alarmHelper.setVolume(volume), throwable -> {
                });
    }

    @Override
    public void loadIntervalListToTimer() {
        timerHelper.loadIntervalList(intervalList);
    }

    @Override
    public void continueTimer() {
        timerHelper.timerRecreate();
    }

    @Override
    public void stopTimer() {
        timerHelper.timerDelete();
    }

    @Override
    public Observable<Integer> intervalFinishedCallback() {
        return timerHelper.onIntervalFinished();

    }

    @Override
    public Observable<Integer> tickCallback() {
        return timerHelper.onTimerTickHappened()
                .map(time -> {
                    if (((time > 500) && (time < 1000)) || ((time > 1500) && (time < 2000)) ||
                            ((time > 2500) && (time < 3000)))
                        indicateLastSeconds();
                    return (int) ((time / 1000) + 1);
                });
    }

    @Override
    public boolean getTimerState() {
        return timerHelper.getTimerState();
    }

    @Override
    public void setTimerState(boolean timerState) {
        timerHelper.setTimerState(timerState);
    }

    @Override
    public int getIntervalSize() {
        return intervalList.size();
    }

    @Override
    public void indicateEndOfWorkout() {
        alarmHelper.patternVibrate();
        alarmHelper.playFinalSound();
    }

    @Override
    public void indicateEndOfInterval() {
        alarmHelper.oneShotVibrate();
        alarmHelper.playLongSound();
    }

    @Override
    public void indicateLastSeconds() {
        alarmHelper.playSound();
    }
}
