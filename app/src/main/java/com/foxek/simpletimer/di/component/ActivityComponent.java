package com.foxek.simpletimer.di.component;

import com.foxek.simpletimer.di.PerActivity;
import com.foxek.simpletimer.di.module.ActivityModule;
import com.foxek.simpletimer.ui.interval.IntervalActivity;
import com.foxek.simpletimer.ui.interval.dialog.IntervalCreateDialog;
import com.foxek.simpletimer.ui.interval.dialog.IntervalEditDialog;
import com.foxek.simpletimer.ui.interval.dialog.WorkoutEditDialog;
import com.foxek.simpletimer.ui.timer.TimerActivity;
import com.foxek.simpletimer.ui.workout.WorkoutActivity;
import com.foxek.simpletimer.ui.workout.dialog.WorkoutCreateDialog;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(WorkoutActivity activity);

    void inject(IntervalActivity activity);

    void inject(TimerActivity activity);

    void inject(WorkoutCreateDialog dialog);

    void inject(WorkoutEditDialog dialog);

    void inject(IntervalCreateDialog dialog);

    void inject(IntervalEditDialog dialog);

}