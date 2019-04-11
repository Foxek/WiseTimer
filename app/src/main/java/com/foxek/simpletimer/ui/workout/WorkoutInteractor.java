package com.foxek.simpletimer.ui.workout;

import com.foxek.simpletimer.data.database.LocalDatabase;
import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.data.model.Workout;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class WorkoutInteractor implements WorkoutContact.Interactor {

    private LocalDatabase database;

    @Inject
    WorkoutInteractor(LocalDatabase database){
        this.database = database;
    }

    @Override
    public Flowable<List<Workout>> fetchWorkoutList(){
        return database.getWorkoutDAO().getAll()
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Disposable createWorkout(String name){
        return database.getWorkoutDAO().getLastId()
                .defaultIfEmpty(0)
                .flatMapCompletable(id -> {

                    Workout workout = new Workout(name, id + 1, 1, true);
                    Interval interval = new Interval(1, 1, id + 1, 0);

                    return Completable.concatArray(
                                Completable.fromAction(() -> database.getWorkoutDAO().add(workout)),
                                Completable.fromAction(() -> database.getIntervalDAO().add(interval))
                    );
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, error -> {});
    }

}
