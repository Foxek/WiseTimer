package com.foxek.simpletimer.di.component

import com.foxek.simpletimer.di.PerFragment
import com.foxek.simpletimer.di.module.ActivityModule
import com.foxek.simpletimer.ui.interval.IntervalFragment
import com.foxek.simpletimer.ui.interval.dialog.IntervalCreateDialog
import com.foxek.simpletimer.ui.interval.dialog.IntervalEditDialog
import com.foxek.simpletimer.ui.interval.dialog.WorkoutEditDialog
import com.foxek.simpletimer.ui.workout.WorkoutFragment
import com.foxek.simpletimer.ui.workout.dialog.WorkoutCreateDialog

import dagger.Component

@PerFragment
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: WorkoutFragment)

    fun inject(activity: IntervalFragment)

    fun inject(dialog: WorkoutCreateDialog)

    fun inject(dialog: WorkoutEditDialog)

    fun inject(dialog: IntervalCreateDialog)

    fun inject(dialog: IntervalEditDialog)
}

