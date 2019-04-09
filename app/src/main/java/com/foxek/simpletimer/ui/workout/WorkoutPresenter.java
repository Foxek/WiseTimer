package com.foxek.simpletimer.ui.workout;

import com.foxek.simpletimer.ui.base.BaseMultiPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WorkoutPresenter extends BaseMultiPresenter<WorkoutContact.View, WorkoutContact.DialogView> implements WorkoutContact.Presenter{

    private CompositeDisposable         mDisposable;
    private WorkoutInteractor           mInteractor;

    public WorkoutPresenter(WorkoutInteractor interactor){
        mInteractor = interactor;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void viewIsReady() {
        createTrainingListAdapter();
    }

    @Override
    public void detachView() {
        super.detachView();
        mDisposable.dispose();
        mInteractor = null;
    }

    @Override
    public void DialogIsReady(){
    }

    private void createTrainingListAdapter (){
        getView().setWorkoutList(mInteractor.createWorkoutListAdapter());
        registerItemCallback();
        registerListUpdateCallback();
    }

    private void registerListUpdateCallback(){
        mDisposable.add(mInteractor.scheduleListChanged());
    }

    private void registerItemCallback() {
        mDisposable.add(mInteractor.onWorkoutItemClick()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(workout -> getView().startIntervalActivity(workout.uid, workout.training_name), throwable -> {}));

    }

    @Override
    public void createNewWorkout(String workoutName) {
        mInteractor.createNewWorkout(workoutName);
    }
}
