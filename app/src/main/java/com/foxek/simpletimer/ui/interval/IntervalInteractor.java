package com.foxek.simpletimer.ui.interval;

import com.foxek.simpletimer.data.database.LocalDatabase;
import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.data.model.Workout;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IntervalInteractor implements IntervalContact.Interactor {

    private LocalDatabase           mDatabase;
    private IntervalAdapter         mIntervalAdapter;
    private Workout                 mWorkout;
    private int                     mClickedIntervalPosition;

    @Inject
    IntervalInteractor(LocalDatabase database){
        mDatabase = database;
    }

    @Override
    public Single<IntervalAdapter> fetchIntervalList(int workoutId){
        return mDatabase.getWorkoutDAO().getById(workoutId)
                .flatMap(workout -> {
                    mWorkout = workout;
                    return mDatabase.getIntervalDAO().getAll(workoutId);
                })
                .map(intervals -> {
                    mIntervalAdapter = new IntervalAdapter(intervals);
                    return mIntervalAdapter;
                });
    }

    @Override
    public Disposable addNewInterval(int work_time,int rest_time){
        int position = mIntervalAdapter.getItemCount();
        mDatabase.getIntervalDAO().add(new Interval(work_time,rest_time,mWorkout.uid,position));
        return mDatabase.getIntervalDAO().getLast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(interval -> mIntervalAdapter.addInterval(interval), throwable -> {});
    }

    @Override
    public Disposable updateInterval(int work_time,int rest_time) {
        return mDatabase.getIntervalDAO().getById(mIntervalAdapter.getInterval(mClickedIntervalPosition).getID(), mWorkout.uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(interval -> {
                    interval.workInterval = work_time;
                    interval.restInterval = rest_time;
                    mIntervalAdapter.updateInterval(mClickedIntervalPosition,work_time,rest_time);
                    mDatabase.getIntervalDAO().update(interval);
                }, throwable -> {});
    }

    @Override
    public Disposable deleteInterval() {
        return mDatabase.getIntervalDAO().getById(mIntervalAdapter.getInterval(mClickedIntervalPosition).getID(), mWorkout.uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(interval -> {
                    if (mIntervalAdapter.getItemCount() != 1) {
                        mIntervalAdapter.deleteInterval(mClickedIntervalPosition);
                        mDatabase.getIntervalDAO().delete(interval);
                    }
                }, throwable -> {});
    }

    @Override
    public Disposable updateWorkout(String workoutName) {
        return Completable.fromAction(() -> mDatabase.getWorkoutDAO().update(workoutName, mWorkout.uid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> {});
    }

    @Override
    public Completable updateWorkoutVolume(int state) {
        return Completable.fromAction(() -> mDatabase.getWorkoutDAO().update(state, mWorkout.uid))
                .subscribeOn(Schedulers.io());
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
        mDatabase.getWorkoutDAO().delete(mWorkout.uid);
    }

    @Override
    public Workout getCurrentWorkout() {
        return mWorkout;
    }
}
