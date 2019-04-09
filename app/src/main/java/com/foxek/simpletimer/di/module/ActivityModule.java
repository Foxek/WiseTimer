package com.foxek.simpletimer.di.module;

import android.os.Bundle;

import com.foxek.simpletimer.data.model.Workout;
import com.foxek.simpletimer.di.PerActivity;
import com.foxek.simpletimer.ui.interval.IntervalContact;
import com.foxek.simpletimer.ui.interval.IntervalInteractor;
import com.foxek.simpletimer.ui.interval.IntervalPresenter;
import com.foxek.simpletimer.ui.timer.TimerContact;
import com.foxek.simpletimer.ui.timer.TimerInteractor;
import com.foxek.simpletimer.ui.timer.TimerPresenter;
import com.foxek.simpletimer.ui.workout.WorkoutContact;
import com.foxek.simpletimer.ui.workout.WorkoutInteractor;
import com.foxek.simpletimer.ui.workout.WorkoutPresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private final Bundle        args;

    public ActivityModule(Bundle args) {
        this.args = args;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerActivity
    WorkoutContact.Interactor provideWorkoutInteractor(WorkoutInteractor interactor) {
        return interactor;
    }

    @Provides
    @PerActivity
    WorkoutContact.Presenter provideWorkoutPresenter(WorkoutContact.Interactor interactor, CompositeDisposable disposable) {
        return new WorkoutPresenter(interactor, disposable);
    }

    @Provides
    @PerActivity
    IntervalContact.Interactor provideIntervalInteractor(IntervalInteractor interactor) {
        return interactor;
    }

    @Provides
    @PerActivity
    IntervalContact.Presenter provideIntervalPresenter(IntervalContact.Interactor interactor, CompositeDisposable disposable) {
        return new IntervalPresenter(interactor, disposable);
    }

    @Provides
    @PerActivity
    TimerContact.Interactor provideTimerInteractor(TimerInteractor interactor) {
        return interactor;
    }

    @Provides
    @PerActivity
    TimerContact.Presenter provideTimerPresenter(TimerContact.Interactor interactor, CompositeDisposable disposable) {
        return new TimerPresenter(interactor, disposable);
    }
}
