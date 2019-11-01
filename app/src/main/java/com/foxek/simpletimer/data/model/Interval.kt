package com.foxek.simpletimer.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import androidx.room.ForeignKey.CASCADE

@Entity(foreignKeys = [ForeignKey(entity = Workout::class, parentColumns = ["uid"], childColumns = ["trainingID"], onDelete = CASCADE)])
class Interval(
        @ColumnInfo(name = "name")
        var name: String?,

        @ColumnInfo(name = "work_intervals")
        var workTime: Int,

        @ColumnInfo(name = "rest_intervals")
        var restTime: Int,

        @ColumnInfo(name = "trainingID")
        var workoutId: Int,

        @ColumnInfo(name = "position_id")
        var position: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}