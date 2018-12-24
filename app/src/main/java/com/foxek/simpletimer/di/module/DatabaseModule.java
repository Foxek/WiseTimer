package com.foxek.simpletimer.di.module;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;

import com.foxek.simpletimer.data.database.TrainingDatabase;
import com.foxek.simpletimer.di.RoomDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private TrainingDatabase mRoomDatabase;

    public DatabaseModule(Application application) {
        mRoomDatabase = Room.databaseBuilder(application, TrainingDatabase.class, "training.db")
                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @RoomDatabase
    TrainingDatabase providesRoomDatabase() {
        return mRoomDatabase;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE trainings ADD COLUMN intervalNumber INTEGER DEFAULT 1 NOT NULL");
        }
    };
}
