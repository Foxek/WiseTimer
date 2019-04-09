package com.foxek.simpletimer.ui.workout;

import com.foxek.simpletimer.data.database.model.Workout;
import com.foxek.simpletimer.ui.base.MvpInteractor;
import com.foxek.simpletimer.ui.base.MvpPresenter;
import com.foxek.simpletimer.ui.base.MvpView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

interface WorkoutContact {

    interface View extends MvpView {

        void startIntervalActivity(int position, String name);

        void setWorkoutList(WorkoutAdapter adapter);
    }

    interface Presenter extends MvpPresenter<View, Interactor> {

        void createNewWorkout(String workoutName);

    }

    interface Interactor extends MvpInteractor {

        WorkoutAdapter createWorkoutListAdapter();

        Disposable scheduleListChanged();

        Observable<Workout> onWorkoutItemClick();

        void createNewWorkout(String workoutName);
    }
}

