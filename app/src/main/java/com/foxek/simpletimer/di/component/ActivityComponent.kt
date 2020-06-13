package com.foxek.simpletimer.di.component

import com.foxek.simpletimer.di.PerFragment
import com.foxek.simpletimer.di.module.ActivityModule
import com.foxek.simpletimer.presentation.round.RoundFragment
import com.foxek.simpletimer.presentation.round.dialog.RoundCreateDialog
import com.foxek.simpletimer.presentation.round.dialog.RoundEditDialog
import com.foxek.simpletimer.presentation.round.dialog.WorkoutEditDialog
import com.foxek.simpletimer.presentation.workout.WorkoutFragment
import com.foxek.simpletimer.presentation.workout.dialog.WorkoutCreateDialog

import dagger.Component

@PerFragment
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: WorkoutFragment)

    fun inject(activity: RoundFragment)

    fun inject(dialog: WorkoutCreateDialog)

    fun inject(dialog: WorkoutEditDialog)

    fun inject(dialog: RoundCreateDialog)

    fun inject(dialog: RoundEditDialog)
}

