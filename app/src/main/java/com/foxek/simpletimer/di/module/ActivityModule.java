package com.foxek.simpletimer.di.module;

import com.foxek.simpletimer.di.PerActivity;
import com.foxek.simpletimer.ui.interval.IntervalInteractor;
import com.foxek.simpletimer.ui.interval.IntervalPresenter;
import com.foxek.simpletimer.ui.timer.TimerInteractor;
import com.foxek.simpletimer.ui.timer.TimerPresenter;
import com.foxek.simpletimer.ui.workout.WorkoutInteractor;
import com.foxek.simpletimer.ui.workout.WorkoutPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Provides
    @PerActivity
    WorkoutPresenter provideTrainingPresenter(WorkoutInteractor interactor) {
        return new WorkoutPresenter(interactor);
    }

    @Provides
    @PerActivity
    IntervalPresenter provideIntervalPresenter(IntervalInteractor interactor) {
        return new IntervalPresenter(interactor);
    }

    @Provides
    @PerActivity
    TimerPresenter provideTimerPresenter(TimerInteractor interactor) {
        return new TimerPresenter(interactor);
    }
}
