package com.foxek.simpletimer.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainings")
data class Workout(
    @ColumnInfo(name = "training_name")
    var name: String?,

    @PrimaryKey
    var uid: Int,

    @ColumnInfo(name = "intervalNumber")
    var intervalCount: Int,

    @ColumnInfo(name = "volumeState")
    var isVolume: Boolean
)
