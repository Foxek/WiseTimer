package com.foxek.simpletimer.di.component;

import android.content.Context;

import com.foxek.simpletimer.common.MainApplication;
import com.foxek.simpletimer.data.TrainingDatabase;
import com.foxek.simpletimer.di.ApplicationContext;
import com.foxek.simpletimer.di.RoomDatabase;
import com.foxek.simpletimer.di.module.ApplicationModule;
import com.foxek.simpletimer.di.module.DatabaseModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class,DatabaseModule.class})
public interface ApplicationComponent {

    void inject(MainApplication app);

    @ApplicationContext
    Context context();

    @RoomDatabase
    TrainingDatabase database();
}