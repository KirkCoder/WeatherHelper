package ru.kcoder.weatherhelper.data.entity.weather

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "clouds"
)
class Clouds {

    var weatherHolderId: Long = 0L
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var all: Double? = null
}
