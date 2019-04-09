package com.foxek.simpletimer.ui.workout;

import com.foxek.simpletimer.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WorkoutPresenter extends BasePresenter<WorkoutContact.View, WorkoutContact.Interactor> implements WorkoutContact.Presenter{

    public WorkoutPresenter(WorkoutContact.Interactor mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }

    @Override
    public void viewIsReady() {
        createTrainingListAdapter();
    }

    private void createTrainingListAdapter (){
        getView().setWorkoutList(getInteractor().createWorkoutListAdapter());
        registerItemCallback();
        registerListUpdateCallback();
    }

    private void registerListUpdateCallback(){
        getDisposable().add(getInteractor().scheduleListChanged());
    }

    private void registerItemCallback() {
        getDisposable().add(getInteractor().onWorkoutItemClick()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(workout -> getView().startIntervalActivity(workout.uid, workout.training_name), throwable -> {}));

    }

    @Override
    public void createNewWorkout(String workoutName) {
        getInteractor().createNewWorkout(workoutName);
    }
}
