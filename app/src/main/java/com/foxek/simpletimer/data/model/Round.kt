package com.foxek.simpletimer.data.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.foxek.simpletimer.common.utils.Constants.NO_ID_INT
import com.foxek.simpletimer.common.utils.Constants.WITH_REST_TYPE

import com.foxek.simpletimer.data.model.Round.Companion.ROUND_TABLE_NAME

@Entity(
    tableName = ROUND_TABLE_NAME,
    indices = [Index("workoutId")],
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onUpdate = CASCADE,
            onDelete = CASCADE
        )
    ]
)
data class Round(
    var name: String = "",
    var type: Int = WITH_REST_TYPE,
    var workInterval: Int = 1,
    var restInterval: Int = 1,
    var workoutId: Int = NO_ID_INT,
    var positionInWorkout: Int = NO_ID_INT
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        const val ROUND_TABLE_NAME = "Round"
    }
}