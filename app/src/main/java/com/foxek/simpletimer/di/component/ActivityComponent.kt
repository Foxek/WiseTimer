package com.foxek.simpletimer.di.component

import com.foxek.simpletimer.di.PerActivity
import com.foxek.simpletimer.di.module.ActivityModule
import com.foxek.simpletimer.ui.interval.IntervalActivity
import com.foxek.simpletimer.ui.interval.dialog.IntervalCreateDialog
import com.foxek.simpletimer.ui.interval.dialog.IntervalEditDialog
import com.foxek.simpletimer.ui.interval.dialog.WorkoutEditDialog
import com.foxek.simpletimer.ui.timer.TimerActivity
import com.foxek.simpletimer.ui.workout.WorkoutActivity
import com.foxek.simpletimer.ui.workout.dialog.WorkoutCreateDialog

import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: WorkoutActivity)

    fun inject(activity: IntervalActivity)

    fun inject(activity: TimerActivity)

    fun inject(dialog: WorkoutCreateDialog)

    fun inject(dialog: WorkoutEditDialog)

    fun inject(dialog: IntervalCreateDialog)

    fun inject(dialog: IntervalEditDialog)

}