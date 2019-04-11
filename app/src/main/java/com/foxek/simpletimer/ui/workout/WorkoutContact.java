package com.foxek.simpletimer.ui.workout;

import com.foxek.simpletimer.data.model.Workout;
import com.foxek.simpletimer.ui.base.MvpInteractor;
import com.foxek.simpletimer.ui.base.MvpPresenter;
import com.foxek.simpletimer.ui.base.MvpView;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public interface WorkoutContact {

    interface View extends MvpView {

        void startIntervalActivity(int position, String name);

        void setWorkoutList();

        void renderWorkoutList(List<Workout> workoutList);

        void showCreateDialog();
    }

    interface Presenter extends MvpPresenter<View, Interactor> {

        void saveButtonClicked(String workoutName);

        void createButtonClicked();

        void onListItemClicked(Workout workout);

    }

    interface Interactor extends MvpInteractor {

        Flowable<List<Workout>> fetchWorkoutList();

        Disposable createWorkout(String workoutName);
    }
}

