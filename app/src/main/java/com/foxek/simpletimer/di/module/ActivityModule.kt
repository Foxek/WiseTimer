package com.foxek.simpletimer.di.module

import com.foxek.simpletimer.di.PerFragment
import com.foxek.simpletimer.domain.interval.IntervalInteractor
import com.foxek.simpletimer.presentation.interval.IntervalAdapter
import com.foxek.simpletimer.presentation.interval.IntervalContact
import com.foxek.simpletimer.domain.interval.IntervalInteractorImpl
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.presentation.interval.IntervalPresenter
import com.foxek.simpletimer.presentation.workout.WorkoutContact
import com.foxek.simpletimer.domain.workout.WorkoutInteractorImpl
import com.foxek.simpletimer.presentation.workout.WorkoutPresenter

import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @Provides
    fun provideWorkoutInteractor(interactor: WorkoutInteractorImpl): WorkoutInteractor = interactor

    @Provides
    @PerFragment
    fun provideWorkoutPresenter(presenter: WorkoutPresenter): WorkoutContact.Presenter = presenter

    @Provides
    fun provideIntervalInteractor(interactor: IntervalInteractorImpl): IntervalInteractor = interactor

    @Provides
    @PerFragment
    fun provideIntervalPresenter(presenter: IntervalPresenter): IntervalContact.Presenter = presenter

}
