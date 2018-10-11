package com.foxek.simpletimer.di.component;

import com.foxek.simpletimer.di.PerActivity;
import com.foxek.simpletimer.di.module.ActivityModule;
import com.foxek.simpletimer.ui.interval.IntervalActivity;
import com.foxek.simpletimer.ui.timer.TimerActivity;
import com.foxek.simpletimer.ui.workout.WorkoutActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(WorkoutActivity activity);
    void inject(IntervalActivity activity);
    void inject(TimerActivity activity);
}