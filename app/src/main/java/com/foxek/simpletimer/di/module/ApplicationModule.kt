package com.foxek.simpletimer.di.module

import android.content.Context

import com.foxek.simpletimer.data.database.TimerDatabase

import javax.inject.Singleton

import com.foxek.dosimeter.utils.SchedulerProvider
import com.foxek.dosimeter.utils.SchedulerProviderImpl

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideAppContext(): Context = context

    @Provides
    @Singleton
    fun provideTimerDatabase(): TimerDatabase = TimerDatabase.getInstance(context)
}
