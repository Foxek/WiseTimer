package com.foxek.simpletimer.ui.interval;

import com.foxek.simpletimer.data.model.Interval;
import com.foxek.simpletimer.data.model.Workout;
import com.foxek.simpletimer.ui.base.MvpInteractor;
import com.foxek.simpletimer.ui.base.MvpPresenter;
import com.foxek.simpletimer.ui.base.MvpView;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public interface IntervalContact {

    interface View extends MvpView {

        void setIntervalList();

        void renderIntervalList(List<Interval> intervalList);

        void setVolumeState(boolean state);

        void setWorkoutName(String name);

        void startWorkoutActivity();

        void startTimerActivity();

        void showIntervalEditDialog(String name, int workTime,int restTime);

        void showIntervalCreateDialog();

        void showWorkoutEditDialog();
    }

    interface Presenter extends MvpPresenter<View, Interactor> {

        void viewIsReady(int id);

        void saveIntervalButtonClicked(String name, int workTime, int restTime);

        void createIntervalButtonClicked(String name, int workTime, int restTime);

        void deleteIntervalButtonClicked();

        void saveWorkoutButtonClicked(String name);

        void editWorkoutButtonClicked();

        void deleteWorkoutButtonClicked();

        void startWorkoutButtonClicked();

        void changeVolumeButtonClicked();

        void addIntervalButtonClicked();

        void intervalItemClicked(Interval item);
    }

    interface Interactor extends MvpInteractor {

        Flowable<List<Interval>> fetchIntervalList();

        Disposable addInterval(String name, int work_time, int rest_time);

        Disposable updateInterval(String name, int work_time,int rest_time);

        Disposable deleteInterval();

        Single<Workout> getWorkout(int id);

        Disposable updateWorkout(String workoutName);

        Completable deleteWorkout();

        Completable updateVolume(boolean state);

        Single<Boolean> getVolume();

        void setCurrentInterval(int id);
    }
}