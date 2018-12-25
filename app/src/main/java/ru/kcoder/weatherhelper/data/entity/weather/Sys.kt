package ru.kcoder.weatherhelper.data.entity.weather

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "sys"
)
class Sys {

    var weatherHolderId: Long = 0L
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var pod: String? = null
}