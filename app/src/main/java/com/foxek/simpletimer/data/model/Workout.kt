package com.foxek.simpletimer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.foxek.simpletimer.common.utils.Constants.NO_ID_INT
import com.foxek.simpletimer.data.model.Workout.Companion.WORKOUT_TABLE_NAME

@Entity(tableName = WORKOUT_TABLE_NAME)
data class Workout(
    @PrimaryKey
    var id: Int = NO_ID_INT,
    var name: String = "",
    var numberOfRounds: Int = 1,
    var isSilentMode: Boolean = false
) {
    companion object {
        const val WORKOUT_TABLE_NAME = "Workout"
    }
}