package com.foxek.simpletimer

import android.app.Application

import com.foxek.simpletimer.di.component.ApplicationComponent
import com.foxek.simpletimer.di.component.DaggerApplicationComponent
import com.foxek.simpletimer.di.module.ApplicationModule


class AndroidApplication : Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

        component.inject(this)
    }
}