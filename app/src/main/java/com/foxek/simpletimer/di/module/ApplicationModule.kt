package com.foxek.simpletimer.di.module

import android.content.Context

import com.foxek.simpletimer.data.database.TimerDatabase

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    @Provides
    @Singleton
    fun provideTimerDatabase(): TimerDatabase = TimerDatabase.getInstance(context)
}
