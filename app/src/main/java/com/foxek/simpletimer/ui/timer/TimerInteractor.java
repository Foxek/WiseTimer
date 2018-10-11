package com.foxek.simpletimer.ui.timer;

import com.foxek.simpletimer.data.model.AlarmHelper;
import com.foxek.simpletimer.data.model.TimerHelper;
import com.foxek.simpletimer.data.model.interval.Interval;
import com.foxek.simpletimer.data.model.interval.IntervalRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

import static com.foxek.simpletimer.common.Constants.PREPARE_INTERVAL;

public class TimerInteractor implements TimerContact.Interactor{

    private IntervalRepository          mIntervalRepository;
    private ArrayList<Integer>          mInterval;
    private TimerHelper                 mTimerHelper;
    private AlarmHelper                 mAlarmHelper;

    @Inject
    TimerInteractor(IntervalRepository repository, AlarmHelper alarmHelper, TimerHelper timer){
        mIntervalRepository = repository;
        mTimerHelper = timer;
        mAlarmHelper = alarmHelper;
    }

    public void deleteDependencies(){
        mTimerHelper.timerDelete();
        mTimerHelper = null;
        mAlarmHelper = null;
    }

    @Override
    public Single<Integer> fetchIntervalList(int workoutId){
        mInterval = new ArrayList<>();
        mInterval.add(PREPARE_INTERVAL);
        return mIntervalRepository.getAllInterval(workoutId)
                .map(intervals -> {
                    for (Interval interval : intervals) {
                        mInterval.add(interval.workInterval);
                        mInterval.add(interval.restInterval);
                    }
                    return mInterval.size();
                });
    }

    @Override
    public void loadIntervalListToTimer(){
        mTimerHelper.loadIntervalList(mInterval);
    }

    @Override
    public void continueTimer(){
        mTimerHelper.timerRecreate();
    }

    @Override
    public void stopTimer(){
        mTimerHelper.timerDelete();
    }

    @Override
    public Observable<Integer> IntervalFinishedCallback() {
        return mTimerHelper.onIntervalFinished();

    }

    @Override
    public Observable<Integer> onTickCallback() {
        return mTimerHelper.onTimerTickHappened()
                .map(time->{
                    if (((time > 500) && (time < 1000)) || ((time > 1500) && (time < 2000)) ||
                            ((time > 2500) && (time < 3000)))
                        indicateLastSeconds();
                    return (int)((time/1000)+1);
                });
    }

    @Override
    public boolean getTimerState(){
        return mTimerHelper.getTimerState();
    }

    @Override
    public void setTimerState(boolean timerState){
        mTimerHelper.setTimerState(timerState);
    }

    @Override
    public int getIntervalSize(){
        return mInterval.size();
    }

    @Override
    public void indicateEndOfWorkout(){
        mAlarmHelper.patternVibrate();
        mAlarmHelper.playFinalSound();
    }

    @Override
    public void indicateEndOfInterval(){
        mAlarmHelper.oneShotVibrate();
        mAlarmHelper.playLongSound();
    }

    @Override
    public void indicateLastSeconds(){
        mAlarmHelper.playSound();
    }
}
