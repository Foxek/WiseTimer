package com.foxek.simpletimer

import android.app.Application

import com.foxek.simpletimer.di.component.ApplicationComponent
import com.foxek.simpletimer.di.component.DaggerApplicationComponent
import com.foxek.simpletimer.di.module.ApplicationModule


class AndroidApplication : Application() {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        appComponent.inject(this)
    }

    fun getComponent(): ApplicationComponent = appComponent
}