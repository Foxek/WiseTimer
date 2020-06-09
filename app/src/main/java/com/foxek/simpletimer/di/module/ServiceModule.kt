package com.foxek.simpletimer.di.module

import com.foxek.simpletimer.di.PerService
import com.foxek.simpletimer.domain.interval.IntervalInteractor
import com.foxek.simpletimer.domain.interval.IntervalInteractorImpl
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.domain.workout.WorkoutInteractorImpl
import com.foxek.simpletimer.presentation.timer.TimerContact
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @Provides
    @PerService
    fun provideWorkoutInteractor(interactor: WorkoutInteractorImpl): WorkoutInteractor = interactor

    @Provides
    @PerService
    fun provideIntervalInteractor(interactor: IntervalInteractorImpl): IntervalInteractor = interactor
}