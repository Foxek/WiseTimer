package com.foxek.simpletimer.di.module;

import android.os.Bundle;

import com.foxek.simpletimer.di.PerActivity;
import com.foxek.simpletimer.ui.interval.IntervalInteractor;
import com.foxek.simpletimer.ui.interval.IntervalPresenter;
import com.foxek.simpletimer.ui.timer.TimerInteractor;
import com.foxek.simpletimer.ui.timer.TimerPresenter;
import com.foxek.simpletimer.ui.workout.WorkoutInteractor;
import com.foxek.simpletimer.ui.workout.WorkoutPresenter;

import androidx.appcompat.app.AppCompatActivity;
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
    WorkoutPresenter provideTrainingPresenter(WorkoutInteractor interactor, CompositeDisposable disposable) {
        return new WorkoutPresenter(interactor, disposable);
    }

    @Provides
    @PerActivity
    IntervalPresenter provideIntervalPresenter(IntervalInteractor interactor, CompositeDisposable disposable) {
        return new IntervalPresenter(interactor, disposable);
    }

    @Provides
    @PerActivity
    TimerPresenter provideTimerPresenter(TimerInteractor interactor, CompositeDisposable disposable) {
        return new TimerPresenter(interactor, disposable);
    }
}
