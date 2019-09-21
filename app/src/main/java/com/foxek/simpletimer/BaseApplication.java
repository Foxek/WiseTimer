package com.foxek.simpletimer;

import android.app.Application;

import com.foxek.simpletimer.di.component.ApplicationComponent;
import com.foxek.simpletimer.di.component.DaggerApplicationComponent;
import com.foxek.simpletimer.di.module.ApplicationModule;


public class BaseApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

}