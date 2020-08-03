package com.foxek.simpletimer.di.module

import com.foxek.simpletimer.di.PerFragment
import com.foxek.simpletimer.domain.round.RoundInteractor
import com.foxek.simpletimer.presentation.round.RoundContact
import com.foxek.simpletimer.domain.round.RoundInteractorImpl
import com.foxek.simpletimer.domain.workout.WorkoutInteractor
import com.foxek.simpletimer.presentation.round.RoundPresenter
import com.foxek.simpletimer.presentation.workout.WorkoutContact
import com.foxek.simpletimer.domain.workout.WorkoutInteractorImpl
import com.foxek.simpletimer.presentation.editworkout.EditWorkoutContract
import com.foxek.simpletimer.presentation.editworkout.EditWorkoutPresenter
import com.foxek.simpletimer.presentation.workout.WorkoutPresenter

import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @Provides
    fun provideWorkoutInteractor(interactor: WorkoutInteractorImpl): WorkoutInteractor = interactor

    @Provides
    @PerFragment
    fun provideWorkoutPresenter(presenter: WorkoutPresenter): WorkoutContact.Presenter = presenter

    @Provides
    fun provideIntervalInteractor(interactor: RoundInteractorImpl): RoundInteractor = interactor

    @Provides
    @PerFragment
    fun provideIntervalPresenter(presenter: RoundPresenter): RoundContact.Presenter = presenter

    @Provides
    @PerFragment
    fun provideEditWorkoutPresenter(presenter: EditWorkoutPresenter): EditWorkoutContract.Presenter = presenter
}
