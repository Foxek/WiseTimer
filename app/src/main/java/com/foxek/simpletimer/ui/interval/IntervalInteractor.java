package com.foxek.simpletimer.ui.interval;

import com.foxek.simpletimer.data.database.model.Interval;
import com.foxek.simpletimer.data.database.repository.IntervalRepository;
import com.foxek.simpletimer.data.database.repository.WorkoutRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IntervalInteractor implements IntervalContact.Interactor {

    private WorkoutRepository mWorkoutRepository;
    private IntervalRepository      mIntervalRepository;
    private IntervalAdapter         mIntervalAdapter;
    private int                     mWorkoutId;
    private int                     mClickedIntervalPosition;

    @Inject
    public IntervalInteractor(WorkoutRepository workoutRepository, IntervalRepository intervalRepository){
        mWorkoutRepository = workoutRepository;
        mIntervalRepository = intervalRepository;
    }

    @Override
    public Single<IntervalAdapter> fetchIntervalList(int workoutId){
        mWorkoutId = workoutId;
        return mIntervalRepository.getAllInterval(workoutId)
                .map(intervals -> {
                    mIntervalAdapter = new IntervalAdapter(intervals);
                    return mIntervalAdapter;
                });
    }

    @Override
    public Disposable addNewInterval(int work_time,int rest_time){
        int position = mIntervalAdapter.getItemCount();
        mIntervalRepository.createNewInterval(new Interval(work_time,rest_time,mWorkoutId,position));
        return mIntervalRepository.getLastInterval()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(interval -> mIntervalAdapter.addInterval(interval), throwable -> {});
    }

    @Override
    public Disposable updateInterval(int work_time,int rest_time) {
        return mIntervalRepository.getIntervalById(mIntervalAdapter.getInterval(mClickedIntervalPosition).getID(), mWorkoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(interval -> {
                    interval.workInterval = work_time;
                    interval.restInterval = rest_time;
                    mIntervalAdapter.updateInterval(mClickedIntervalPosition,work_time,rest_time);
                    mIntervalRepository.updateInterval(interval);
                }, throwable -> {});
    }

    @Override
    public Disposable deleteInterval() {
        return mIntervalRepository.getIntervalById(mIntervalAdapter.getInterval(mClickedIntervalPosition).getID(), mWorkoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(interval -> {
                    if (mIntervalAdapter.getItemCount() != 1) {
                        mIntervalAdapter.deleteInterval(mClickedIntervalPosition);
                        mIntervalRepository.deleteInterval(interval);
                    }
                }, throwable -> {});
    }

    @Override
    public Disposable updateWorkout(String workoutName) {
        return mWorkoutRepository.getWorkoutById(mWorkoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(training -> {
                    training.training_name = workoutName;
                    mWorkoutRepository.updateWorkout(training);
                }, throwable -> {});
    }

    @Override
    public Observable<Interval> onIntervalItemClick() {
        return mIntervalAdapter.getPositionClicks()
                .map(position->{
                    mClickedIntervalPosition = position;
                    return mIntervalAdapter.getInterval(position);
                });
    }

    @Override
    public void deleteWorkout() {
        mWorkoutRepository.deleteWorkout(mWorkoutId);
    }
}
