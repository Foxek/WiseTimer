package com.foxek.simpletimer.ui.interval;

import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class IntervalPresenter extends BasePresenter<IntervalContact.View> implements IntervalContact.Presenter {

    IntervalContact.Interactor interactor;
    @Inject
    public IntervalPresenter(IntervalContact.Interactor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void viewIsReady() {
    }

    @Override
    public void viewIsReady(int id) {
        getView().setIntervalList();

        getDisposable().add(interactor
                .getWorkout(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(workout -> {
                    getView().setWorkoutName(workout.getName());
                    getView().setVolumeState(workout.isVolume());
                }, throwable -> {
                })
        );

        getDisposable().add(interactor
                .fetchIntervalList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(intervalList -> getView().renderIntervalList(intervalList),
                        error -> {
                        })
        );
    }

    @Override
    public void editWorkoutButtonClicked() {
        getView().showWorkoutEditDialog();
    }

    @Override
    public void changeVolumeButtonClicked() {
        getDisposable().add(interactor
                .getVolume()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapCompletable(state -> {
                    getView().setVolumeState(!state);
                    return interactor.updateVolume(!state);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, throwable -> {
                }));
    }

    @Override
    public void addIntervalButtonClicked() {
        getView().showIntervalCreateDialog();
    }

    @Override
    public void saveIntervalButtonClicked(String name, int workTime, int restTime) {
        getDisposable().add(interactor.updateInterval(name, workTime, restTime));
    }

    @Override
    public void createIntervalButtonClicked(String name, int workTime, int restTime) {
        getDisposable().add(interactor.addInterval(name, workTime, restTime));
    }

    @Override
    public void deleteIntervalButtonClicked() {
        getDisposable().add(interactor.deleteInterval());
    }

    @Override
    public void saveWorkoutButtonClicked(String name) {
        getDisposable().add(interactor.updateWorkout(name));
        getView().setWorkoutName(name);
    }

    @Override
    public void deleteWorkoutButtonClicked() {
        getDisposable().add(interactor
                .deleteWorkout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getView().startWorkoutActivity(), throwable -> {
                })
        );
    }

    @Override
    public void startWorkoutButtonClicked() {
        getView().startTimerActivity();
    }

    @Override
    public void intervalItemClicked(Interval item) {
        interactor.setCurrentInterval(item.getId());
        getView().showIntervalEditDialog(item.getName(), item.getWorkTime(), item.getRestTime());
    }
}