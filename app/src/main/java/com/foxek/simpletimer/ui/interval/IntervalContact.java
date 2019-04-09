package com.foxek.simpletimer.ui.interval;

import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.data.model.Workout;
import com.foxek.simpletimer.ui.base.MvpInteractor;
import com.foxek.simpletimer.ui.base.MvpPresenter;
import com.foxek.simpletimer.ui.base.MvpView;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public interface IntervalContact {

    interface View extends MvpView {

        void setIntervalList(IntervalAdapter adapter);

        void setVolumeState(int state);

        void startWorkoutActivity();

        void startTimerActivity();

        void showIntervalEditDialog(int work_time,int rest_time);

        void showIntervalCreateDialog();

        void showWorkoutEditDialog();

        void setWorkoutName(String name);
    }

    interface Presenter extends MvpPresenter<View, Interactor> {

        void createIntervalListAdapter (int workoutId);

        void onIntervalChanged(int work_time, int rest_time);

        void onIntervalCreated(int work_time, int rest_time);

        void onDeleteInterval();

        void editWorkout(String name);

        void deleteWorkout();

        void startWorkout();

        void editButtonPressed();

        void setVolumeButtonPressed();

        void addIntervalButtonPressed();
    }

    interface Interactor extends MvpInteractor {

        Single<IntervalAdapter> fetchIntervalList(int workoutId);

        Disposable addNewInterval(int work_time,int rest_time);

        Disposable updateInterval(int work_time,int rest_time);

        Disposable deleteInterval();

        Disposable updateWorkout(String workoutName);

        Observable<Interval> onIntervalItemClick();

        Completable updateWorkoutVolume(int state);

        void deleteWorkout();

        Workout getCurrentWorkout();
    }
}
