package com.foxek.simpletimer.di.component;

import android.content.Context;

import com.foxek.simpletimer.common.BaseApplication;
import com.foxek.simpletimer.data.database.TrainingDatabase;
import com.foxek.simpletimer.di.ApplicationContext;
import com.foxek.simpletimer.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(BaseApplication app);

    @ApplicationContext
    Context context();

    TrainingDatabase databaseHelper();
}