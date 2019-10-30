package com.foxek.simpletimer.di.module

import com.foxek.simpletimer.di.PerActivity
import com.foxek.simpletimer.ui.interval.IntervalAdapter
import com.foxek.simpletimer.ui.interval.IntervalContact
import com.foxek.simpletimer.ui.interval.IntervalInteractor
import com.foxek.simpletimer.ui.interval.IntervalPresenter
import com.foxek.simpletimer.ui.timer.TimerContact
import com.foxek.simpletimer.ui.timer.TimerInteractor
import com.foxek.simpletimer.ui.timer.TimerPresenter
import com.foxek.simpletimer.ui.workout.WorkoutAdapter
import com.foxek.simpletimer.ui.workout.WorkoutContact
import com.foxek.simpletimer.ui.workout.WorkoutInteractor
import com.foxek.simpletimer.ui.workout.WorkoutPresenter

import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    /* Workout Activity */
    @Provides
    @PerActivity
    fun provideWorkoutAdapter(): WorkoutAdapter = WorkoutAdapter()

    @Provides
    @PerActivity
    fun provideWorkoutInteractor(interactor: WorkoutInteractor): WorkoutContact.Interactor = interactor

    @Provides
    @PerActivity
    fun provideWorkoutPresenter(presenter: WorkoutPresenter): WorkoutContact.Presenter = presenter


    /* Interval Activity */
    @Provides
    @PerActivity
    fun provideIntervalAdapter(): IntervalAdapter = IntervalAdapter()

    @Provides
    @PerActivity
    fun provideIntervalInteractor(interactor: IntervalInteractor): IntervalContact.Interactor = interactor


    @Provides
    @PerActivity
    fun provideIntervalPresenter(presenter: IntervalPresenter): IntervalContact.Presenter = presenter

    /* Timer Activity */
    @Provides
    @PerActivity
    fun provideTimerInteractor(interactor: TimerInteractor): TimerContact.Interactor = interactor


    @Provides
    @PerActivity
    fun provideTimerPresenter(presenter: TimerPresenter): TimerContact.Presenter = presenter
}
