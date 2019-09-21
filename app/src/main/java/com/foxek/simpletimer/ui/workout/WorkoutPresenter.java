package com.foxek.simpletimer.ui.workout;

import com.foxek.simpletimer.data.model.Workout;
import com.foxek.simpletimer.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class WorkoutPresenter extends BasePresenter<WorkoutContact.View, WorkoutContact.Interactor> implements WorkoutContact.Presenter {

    public WorkoutPresenter(WorkoutContact.Interactor interactor, CompositeDisposable disposable) {
        super(interactor, disposable);
    }

    @Override
    public void viewIsReady() {
        getView().setWorkoutList();

        getDisposable().add(getInteractor()
                .fetchWorkoutList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(workoutList -> getView().renderWorkoutList(workoutList),
                        error -> {
                        })
        );
    }

    @Override
    public void saveButtonClicked(String workoutName) {
        getDisposable().add(getInteractor().createWorkout(workoutName));
    }

    @Override
    public void createButtonClicked() {
        getView().showCreateDialog();
    }

    @Override
    public void onListItemClicked(Workout workout) {
        getView().startIntervalActivity(workout.getUid(), workout.getName());
    }
}
