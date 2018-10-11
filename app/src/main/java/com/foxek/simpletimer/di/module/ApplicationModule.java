package com.foxek.simpletimer.di.module;

import android.app.Application;
import android.content.Context;

import com.foxek.simpletimer.di.ApplicationContext;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application           mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }
}
