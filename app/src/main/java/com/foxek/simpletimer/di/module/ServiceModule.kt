package com.foxek.simpletimer.di.module

import com.foxek.simpletimer.di.PerService
import com.foxek.simpletimer.ui.timer.TimerContact
import com.foxek.simpletimer.ui.timer.TimerInteractor
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @Provides
    @PerService
    fun provideTimerInteractor(interactor: TimerInteractor): TimerContact.Interactor = interactor
}