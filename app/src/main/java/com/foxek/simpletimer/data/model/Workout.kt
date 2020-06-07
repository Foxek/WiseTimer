package com.foxek.simpletimer.data.model

import android.os.Parcel
import android.os.Parcelable
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

) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(uid)
        parcel.writeInt(intervalCount)
        parcel.writeByte(if (isVolume) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Workout> {
        override fun createFromParcel(parcel: Parcel): Workout {
            return Workout(parcel)
        }

        override fun newArray(size: Int): Array<Workout?> {
            return arrayOfNulls(size)
        }
    }

}
