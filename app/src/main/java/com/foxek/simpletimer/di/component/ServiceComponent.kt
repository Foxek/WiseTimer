package com.foxek.simpletimer.di.component

import com.foxek.simpletimer.di.PerService
import com.foxek.simpletimer.di.module.ServiceModule
import com.foxek.simpletimer.ui.timer.TimerService
import dagger.Component

@PerService
@Component(dependencies = [ApplicationComponent::class], modules = [ServiceModule::class])
interface ServiceComponent {

    fun inject(service: TimerService)
}