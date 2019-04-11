package com.foxek.simpletimer.di.module;

import android.os.Bundle;

import com.foxek.simpletimer.data.database.LocalDatabase;
import com.foxek.simpletimer.data.model.Workout;
import com.foxek.simpletimer.di.PerActivity;
import com.foxek.simpletimer.ui.interval.IntervalAdapter;
import com.foxek.simpletimer.ui.interval.IntervalContact;
import com.foxek.simpletimer.ui.interval.IntervalInteractor;
import com.foxek.simpletimer.ui.interval.IntervalPresenter;
import com.foxek.simpletimer.ui.timer.TimerContact;
import com.foxek.simpletimer.ui.timer.TimerInteractor;
import com.foxek.simpletimer.ui.timer.TimerPresenter;
import com.foxek.simpletimer.ui.workout.WorkoutAdapter;
import com.foxek.simpletimer.ui.workout.WorkoutContact;
import com.foxek.simpletimer.ui.workout.WorkoutInteractor;
import com.foxek.simpletimer.ui.workout.WorkoutPresenter;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private final Bundle        args;
    private AppCompatActivity   activity;

    public ActivityModule(AppCompatActivity activity, Bundle args) {
        this.args = args;
        this.activity = activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }


    /* Workout Activity */
    @Provides
    @PerActivity
    WorkoutAdapter provideWorkoutAdapter(){
        return new WorkoutAdapter();
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

    /* Interval Activity */
    @Provides
    @PerActivity
    IntervalAdapter provideIntervalAdapter(){
        return new IntervalAdapter();
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

    /* Timer Activity */
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
