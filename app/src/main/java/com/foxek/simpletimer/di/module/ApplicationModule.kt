package com.foxek.simpletimer.di.module

import android.content.Context
import com.foxek.simpletimer.data.UserPreferences

import com.foxek.simpletimer.data.database.TimerDatabase
import com.foxek.simpletimer.data.database.TimerDAO

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {

    lateinit var database: TimerDatabase

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    @Provides
    @Singleton
    fun provideTimerDatabase(): TimerDatabase {
        database = TimerDatabase.getInstance(context)
        return database
    }

    @Provides
    @Singleton
    fun provideTimerDao(): TimerDAO = database.timerDAO

    @Provides
    @Singleton
    fun provideUserPreferences(): UserPreferences = UserPreferences(context)
}
