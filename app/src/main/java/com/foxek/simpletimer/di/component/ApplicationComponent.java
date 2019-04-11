package com.foxek.simpletimer.di.component;

import android.content.Context;

import com.foxek.simpletimer.BaseApplication;
import com.foxek.simpletimer.data.database.LocalDatabase;
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

    LocalDatabase databaseHelper();
}