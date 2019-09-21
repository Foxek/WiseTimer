package com.foxek.simpletimer.ui.timer;

import com.foxek.simpletimer.ui.base.MvpInteractor;
import com.foxek.simpletimer.ui.base.MvpPresenter;
import com.foxek.simpletimer.ui.base.MvpView;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public interface TimerContact {
    interface View extends MvpView {

        void startWorkoutActivity();

        void showPauseInterface();

        void showPlayInterface();

        void showCurrentCounter(String time);

        void showCounterType(int type);

        void showCounterNumber(String number);

        void showCounterName(String name);
    }

    interface Presenter extends MvpPresenter<View, Interactor> {

        void prepareIntervals(int workoutId);

        void pauseButtonClicked();

        void resetButtonClicked();
    }

    interface Interactor extends MvpInteractor {

        Flowable<Integer> fetchIntervalList(int workoutId);

        Observable<Integer> intervalFinishedCallback();

        Observable<Integer> tickCallback();

        Disposable getVolume(int id);

        boolean getTimerState();

        int getIntervalSize();

        void setTimerState(boolean timerState);

        void loadIntervalListToTimer();

        void continueTimer();

        void stopTimer();

        void indicateEndOfWorkout();

        void indicateEndOfInterval();

        void indicateLastSeconds();

        void deleteDependencies();

        String getIntervalName(int id);
    }
}
