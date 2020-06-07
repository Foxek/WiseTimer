package com.foxek.simpletimer.data.model


import androidx.room.*

import androidx.room.ForeignKey.CASCADE

@Entity(foreignKeys = [ForeignKey(entity = Workout::class, parentColumns = ["uid"], childColumns = ["trainingID"], onDelete = CASCADE)])
class Interval(
    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "work_intervals")
    var work: Int,

    @ColumnInfo(name = "rest_intervals")
    var rest: Int,

    @ColumnInfo(name = "trainingID")
    var workoutId: Int,

    @ColumnInfo(name = "interval_type")
    var type: Int,

    @ColumnInfo(name = "position_id")
    var position: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}