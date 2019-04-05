package com.foxek.simpletimer.di.module;

import android.app.Application;

import com.foxek.simpletimer.data.database.TrainingDatabase;
import com.foxek.simpletimer.di.RoomDatabase;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
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
