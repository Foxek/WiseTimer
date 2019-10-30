package com.foxek.simpletimer.ui.interval;

import com.foxek.simpletimer.data.database.TimerDatabase;
import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.data.model.Workout;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IntervalInteractor implements IntervalContact.Interactor {

    private TimerDatabase database;
    private int workoutId;
    private int intervalId;

    @Inject
    IntervalInteractor(TimerDatabase database) {
        this.database = database;
    }

    @Override
    public Single<Workout> getWorkout(int id) {
        workoutId = id;
        return database.getWorkoutDAO().getById(workoutId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<Interval>> fetchIntervalList() {
        return database.getIntervalDAO().getAll(workoutId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Disposable addInterval(String name, int work, int rest) {
        return database.getIntervalDAO().getLastId()
                .flatMapCompletable(id -> {
                    Interval interval = new Interval(name, work, rest, workoutId, id + 1);
                    return Completable.fromAction(() -> database.getIntervalDAO().add(interval));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable -> {
                });
    }

    @Override
    public Disposable updateInterval(String name, int work, int rest) {
        return database.getIntervalDAO().getById(intervalId, workoutId)
                .flatMapCompletable(interval -> {
                    interval.setName(name);
                    interval.setWorkTime(work);
                    interval.setRestTime(rest);
                    return Completable.fromAction(() -> database.getIntervalDAO().update(interval));
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable -> {
                });
    }

    @Override
    public Disposable deleteInterval() {
        return database.getIntervalDAO().size(workoutId)
                .filter(size -> size != 1)
                .flatMapCompletable(size ->
                        Completable.fromAction(() -> database.getIntervalDAO().delete(intervalId, workoutId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable -> {
                });
    }

    @Override
    public Disposable updateWorkout(String workoutName) {
        return Completable.fromAction(() -> database.getWorkoutDAO().update(workoutName, workoutId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable -> {
                });
    }

    @Override
    public Completable updateVolume(boolean state) {
        return Completable.fromAction(() -> database.getWorkoutDAO().update(state, workoutId))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteWorkout() {
        return Completable.fromAction(() -> database.getWorkoutDAO().delete(workoutId))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> getVolume() {
        return database.getWorkoutDAO().getVolume(workoutId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void setCurrentInterval(int id) {
        intervalId = id;
    }


}