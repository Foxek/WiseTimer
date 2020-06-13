package com.foxek.simpletimer.data.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.foxek.simpletimer.data.model.Round
import com.foxek.simpletimer.data.model.Workout

@Database(entities = [Workout::class, Round::class], version = 6, exportSchema = false)
abstract class TimerDatabase : RoomDatabase() {

    abstract val timerDAO: TimerDAO

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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
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

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Interval ADD COLUMN interval_type INTEGER DEFAULT 0 NOT NULL")
            }
        }

        private val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE Round (name TEXT DEFAULT '' NOT NULL, type INTEGER DEFAULT 0 NOT NULL, workInterval INTEGER DEFAULT 1 NOT NULL, restInterval INTEGER DEFAULT 1 NOT NULL, workoutId INTEGER DEFAULT -1 NOT NULL, positionInWorkout INTEGER DEFAULT -1 NOT NULL, id INTEGER DEFAULT 0 NOT NULL, PRIMARY KEY(id), FOREIGN KEY(workoutId) REFERENCES Workout(id) ON DELETE CASCADE ON UPDATE CASCADE)")
                database.execSQL(
                    "INSERT INTO Round (name, type, workInterval, restInterval, workoutId, positionInWorkout, id) SELECT name, interval_type, work_intervals, rest_intervals, trainingID, position_id, id FROM Interval")
                database.execSQL("CREATE INDEX index_workout_id ON Round (workoutId)");
                database.execSQL("DROP TABLE Interval")

                database.execSQL(
                    "CREATE TABLE Workout (id INTEGER NOT NULL, name TEXT DEFAULT '' NOT NULL, numberOfRounds INTEGER DEFAULT 1 NOT NULL, isSilentMode INTEGER DEFAULT 1 NOT NULL, PRIMARY KEY(id))")
                database.execSQL(
                    "INSERT INTO Workout (id, name, numberOfRounds, isSilentMode) SELECT uid, training_name, intervalNumber, volumeState FROM trainings");
                database.execSQL("DROP TABLE trainings")
            }
        }
    }
}

