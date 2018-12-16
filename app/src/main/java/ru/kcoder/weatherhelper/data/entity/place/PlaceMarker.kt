package ru.kcoder.weatherhelper.data.entity.place

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "place"
)
data class PlaceMarker(
    val lat: Double,
    val lon: Double,
    var name: String? = null,
    var address: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}