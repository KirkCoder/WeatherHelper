package ru.kcoder.weatherhelper.data.entity.weather

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "wind"
)
class Wind {

    var weatherHolderId: Long = 0L
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var speed: Double? = null
    var deg: Double? = null
}