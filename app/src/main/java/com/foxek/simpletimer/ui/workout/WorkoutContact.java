package com.foxek.simpletimer.ui.workout;

import com.foxek.simpletimer.data.model.workout.Workout;
import com.foxek.simpletimer.ui.base.MvpDialog;
import com.foxek.simpletimer.ui.base.MvpMultiPresenter;
import com.foxek.simpletimer.ui.base.MvpView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

interface WorkoutContact {

    interface View extends MvpView {

        void startIntervalActivity(int position, String name);

        void setWorkoutList(WorkoutAdapter adapter);
    }

    interface DialogView extends MvpDialog<WorkoutContact.Presenter> {

    }

    interface Presenter extends MvpMultiPresenter<WorkoutContact.View,WorkoutContact.DialogView> {

        void createNewWorkout(String workoutName);

    }

    interface Interactor{

        WorkoutAdapter createWorkoutListAdapter();

        Disposable scheduleListChanged();

        Observable<Workout> onWorkoutItemClick();

        void createNewWorkout(String workoutName);
    }
}

