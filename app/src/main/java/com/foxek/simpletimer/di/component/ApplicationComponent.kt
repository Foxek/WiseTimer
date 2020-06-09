package com.foxek.simpletimer.di.component

import android.content.Context

import com.foxek.simpletimer.AndroidApplication
import com.foxek.simpletimer.data.database.IntervalDAO
import com.foxek.simpletimer.data.database.TimerDatabase
import com.foxek.simpletimer.data.database.WorkoutDAO
import com.foxek.simpletimer.di.module.ApplicationModule

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: AndroidApplication)

    fun context(): Context

    fun database(): TimerDatabase

    fun workoutDao(): WorkoutDAO

    fun intervalDao(): IntervalDAO
}