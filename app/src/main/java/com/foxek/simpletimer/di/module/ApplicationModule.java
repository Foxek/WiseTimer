package com.foxek.simpletimer.di.module;

import android.app.Application;
import android.content.Context;

import com.foxek.simpletimer.data.database.LocalDatabase;
import com.foxek.simpletimer.di.ApplicationContext;

import javax.inject.Singleton;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
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

    @Provides
    @Singleton
    LocalDatabase providesDatabaseHelper() {
        return  Room.databaseBuilder(mApplication, LocalDatabase.class, "training.db")
                .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                .allowMainThreadQueries()
                .build();
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE trainings ADD COLUMN intervalNumber INTEGER DEFAULT 1 NOT NULL");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE trainings ADD COLUMN volumeState INTEGER DEFAULT 1 NOT NULL");
        }
    };
}
