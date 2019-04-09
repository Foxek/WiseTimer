package com.foxek.simpletimer.ui.interval;

import com.foxek.simpletimer.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class IntervalPresenter extends BasePresenter<IntervalContact.View,IntervalContact.Interactor> implements IntervalContact.Presenter{

    public IntervalPresenter(IntervalContact.Interactor mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }

    @Override
    public void viewIsReady() {
    }

    @Override
    public void createIntervalListAdapter (int workoutId){
        getDisposable().add(getInteractor().fetchIntervalList(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter -> {
                    getView().setIntervalList(adapter);
                    getView().setVolumeState(getInteractor().getCurrentWorkout().volumeState);
                    registerItemCallback();
                }, throwable -> {}));
    }


    @Override
    public void editButtonPressed(){
        getView().showWorkoutEditDialog();
    }

    @Override
    public void setVolumeButtonPressed() {
        int state;

        if (getInteractor().getCurrentWorkout().volumeState == 1)
            state = 0;
        else
            state = 1;

        getDisposable().add(getInteractor().updateWorkoutVolume(state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    getView().setVolumeState(state);
                }, throwable -> {}));
    }

    @Override
    public void addIntervalButtonPressed(){
        getView().showIntervalCreateDialog();
    }

    @Override
    public void onIntervalChanged(int work_time, int rest_time) {
        getDisposable().add(getInteractor().updateInterval(work_time,rest_time));
    }

    @Override
    public void onIntervalCreated(int work_time, int rest_time) {
        getDisposable().add(getInteractor().addNewInterval(work_time,rest_time));
    }

    @Override
    public void onDeleteInterval() {
        getDisposable().add(getInteractor().deleteInterval());
    }

    @Override
    public void editWorkout(String name) {
        getDisposable().add(getInteractor().updateWorkout(name));
        getView().setWorkoutName(name);
    }

    @Override
    public void deleteWorkout() {
        getInteractor().deleteWorkout();
        getView().startWorkoutActivity();
    }

    @Override
    public void startWorkout() {
        getView().startTimerActivity();
    }

    private void registerItemCallback() {
        getDisposable().add(getInteractor().onIntervalItemClick()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(interval -> getView().showIntervalEditDialog(interval.workInterval,interval.restInterval), throwable -> {}));

    }
}
