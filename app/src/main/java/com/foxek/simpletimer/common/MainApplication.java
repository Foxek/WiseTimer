package com.foxek.simpletimer.common;

import android.app.Application;

import com.foxek.simpletimer.di.component.ApplicationComponent;
import com.foxek.simpletimer.di.component.DaggerApplicationComponent;
import com.foxek.simpletimer.di.module.ApplicationModule;
import com.foxek.simpletimer.di.module.DatabaseModule;


public class MainApplication extends Application {

    private ApplicationComponent        mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
//
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//
//            return;
//        }
//        LeakCanary.install(this);

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(this))
                .build();

        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

}