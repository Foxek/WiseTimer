package com.foxek.simpletimer.ui.workout;

import com.foxek.simpletimer.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WorkoutPresenter extends BasePresenter<WorkoutContact.View, WorkoutContact.Interactor> implements WorkoutContact.Presenter{

    public WorkoutPresenter(WorkoutContact.Interactor interactor, CompositeDisposable disposable) {
        super(interactor, disposable);
    }

    @Override
    public void viewIsReady() {
        getView().setWorkoutList(getInteractor().createWorkoutListAdapter());
        getDisposable().add(getInteractor().fetchWorkoutList());
        registerItemCallback();
    }

    private void registerItemCallback() {
        getDisposable().add(getInteractor().onWorkoutItemClick()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(workout -> getView().startIntervalActivity(workout.uid, workout.training_name),
                        throwable -> {}));

    }

    @Override
    public void createNewWorkout(String workoutName) {
        getInteractor().createNewWorkout(workoutName);
    }
}
