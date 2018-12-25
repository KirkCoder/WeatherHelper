package ru.kcoder.weatherhelper.data.entity.weather

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
    tableName = "city"
)
class City {

    var weatherHolderId: Long = 0L

    @PrimaryKey
    var id: Long? = null
    var name: String? = null
    var country: String? = null
    var population: Double? = null

    @Ignore
    var coord: Coord? = null
}
