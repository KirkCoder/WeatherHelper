package ru.kcoder.weatherhelper.data.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "clouds"
)
class Clouds {

    var weatherHolderId: Long = 0L
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var all: Double? = null
}
