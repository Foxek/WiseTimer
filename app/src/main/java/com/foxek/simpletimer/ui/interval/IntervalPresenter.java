package com.foxek.simpletimer.ui.interval;

import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.ui.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class IntervalPresenter extends BasePresenter<IntervalContact.View,IntervalContact.Interactor> implements IntervalContact.Presenter{

    public IntervalPresenter(IntervalContact.Interactor interactor, CompositeDisposable disposable) {
        super(interactor, disposable);
    }

    @Override
    public void viewIsReady() {
    }

    @Override
    public void viewIsReady(int id) {
        getView().setIntervalList();

        getDisposable().add(getInteractor()
                .getWorkout(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(workout -> {
                    getView().setWorkoutName(workout.getName());
                    getView().setVolumeState(workout.isVolume());
                }, throwable -> {})
        );

        getDisposable().add(getInteractor()
                .fetchIntervalList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intervalList -> getView().renderIntervalList(intervalList),
                        error -> {})
        );
    }

    @Override
    public void editWorkoutButtonClicked(){
        getView().showWorkoutEditDialog();
    }

    @Override
    public void changeVolumeButtonClicked() {
        getDisposable().add(getInteractor()
                .getVolume()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapCompletable(state ->{
                    getView().setVolumeState(!state);
                    return getInteractor().updateVolume(!state);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {}, throwable -> {}));
    }

    @Override
    public void addIntervalButtonClicked(){
        getView().showIntervalCreateDialog();
    }

    @Override
    public void saveIntervalButtonClicked(int workTime, int restTime) {
        getDisposable().add(getInteractor().updateInterval(workTime, restTime));
    }

    @Override
    public void createIntervalButtonClicked(int workTime, int restTime) {
        getDisposable().add(getInteractor().addInterval(workTime, restTime));
    }

    @Override
    public void deleteIntervalButtonClicked() {
        getDisposable().add(getInteractor().deleteInterval());
    }

    @Override
    public void saveWorkoutButtonClicked(String name) {
        getDisposable().add(getInteractor().updateWorkout(name));
        getView().setWorkoutName(name);
    }

    @Override
    public void deleteWorkoutButtonClicked() {
        getDisposable().add(getInteractor()
                .deleteWorkout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getView().startWorkoutActivity(), throwable -> {})
        );
    }

    @Override
    public void startWorkoutButtonClicked() {
        getView().startTimerActivity();
    }

    @Override
    public void intervalItemClicked(Interval item) {
        getInteractor().setCurrentInterval(item.getId());
        getView().showIntervalEditDialog(item.getWorkTime(), item.getRestTime());
    }
}