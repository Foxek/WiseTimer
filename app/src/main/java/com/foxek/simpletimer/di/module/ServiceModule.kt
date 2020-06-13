package com.foxek.simpletimer.di.module

import com.foxek.simpletimer.di.PerService
import com.foxek.simpletimer.domain.round.RoundInteractor
import com.foxek.simpletimer.domain.round.RoundInteractorImpl
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.domain.workout.WorkoutInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @Provides
    @PerService
    fun provideWorkoutInteractor(interactor: WorkoutInteractorImpl): WorkoutInteractor = interactor

    @Provides
    @PerService
    fun provideIntervalInteractor(interactor: RoundInteractorImpl): RoundInteractor = interactor
}