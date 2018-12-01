package ru.kcoder.weatherhelper.data.entity.weather

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "weather"
)
class Weather {

    var weatherHolderId: Long = 0L
    @PrimaryKey(autoGenerate = true)
    var idPk: Long = 0

    var id: Int? = null
    var main: String? = null
    var description: String? = null
    var icon: String? = null

}