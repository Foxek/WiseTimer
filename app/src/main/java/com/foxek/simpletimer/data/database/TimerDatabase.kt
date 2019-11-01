package com.foxek.simpletimer.data.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.data.model.Workout


@Database(entities = [Workout::class, Interval::class], version = 4, exportSchema = false)
abstract class TimerDatabase : RoomDatabase() {

    abstract val workoutDAO: WorkoutDAO
    abstract val intervalDAO: IntervalDAO

    companion object {

        @Volatile
        private var INSTANCE: TimerDatabase? = null

        fun getInstance(context: Context): TimerDatabase {

            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        TimerDatabase::class.java,
                        "training.db"
                )
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                        .allowMainThreadQueries()
                        .build()
                INSTANCE = instance
            }

            return instance
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE trainings ADD COLUMN intervalNumber INTEGER DEFAULT 1 NOT NULL")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE trainings ADD COLUMN volumeState INTEGER DEFAULT 1 NOT NULL")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Interval ADD COLUMN name TEXT DEFAULT ''")
            }
        }
    }
}

