package ru.kcoder.weatherhelper.data.entity.weather

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "coord"
)
class Coord {
    var weatherHolderId: Long = 0L
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var lat: Double? = null
    var lon: Double? = null
}