package com.foxek.simpletimer.ui.interval;

import com.foxek.simpletimer.ui.base.BaseMultiPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class IntervalPresenter extends BaseMultiPresenter<IntervalContact.View,IntervalContact.DialogView> implements IntervalContact.Presenter/*, IntervalAdapter.IntervalViewCallback*/ {

    private CompositeDisposable     mDisposable;
    private IntervalInteractor      mInteractor;

    public IntervalPresenter(IntervalInteractor interactor){
        mInteractor = interactor;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        super.detachView();
        mDisposable.dispose();
        mInteractor = null;
    }

    @Override
    public void viewIsReady() {
    }

    @Override
    public void DialogIsReady() {
    }

    @Override
    public void createIntervalListAdapter (int workoutId){
        mDisposable.add(mInteractor.fetchIntervalList(workoutId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter -> {
                    getView().setIntervalList(adapter);
                    getView().setVolumeState(mInteractor.getCurrentWorkout().volumeState);
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

        if (mInteractor.getCurrentWorkout().volumeState == 1)
            state = 0;
        else
            state = 1;

        mDisposable.add(mInteractor.updateWorkoutVolume(state)
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
        mDisposable.add(mInteractor.updateInterval(work_time,rest_time));
    }

    @Override
    public void onIntervalCreated(int work_time, int rest_time) {
        mDisposable.add(mInteractor.addNewInterval(work_time,rest_time));
    }

    @Override
    public void onDeleteInterval() {
        mDisposable.add(mInteractor.deleteInterval());
    }

    @Override
    public void editWorkout(String name) {
        mDisposable.add(mInteractor.updateWorkout(name));
        getView().setWorkoutName(name);
    }

    @Override
    public void deleteWorkout() {
        mInteractor.deleteWorkout();
        getView().startWorkoutActivity();
    }

    @Override
    public void startWorkout() {
        getView().startTimerActivity();
    }

    private void registerItemCallback() {
        mDisposable.add(mInteractor.onIntervalItemClick()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(interval -> getView().showIntervalEditDialog(interval.workInterval,interval.restInterval), throwable -> {}));

    }
}
