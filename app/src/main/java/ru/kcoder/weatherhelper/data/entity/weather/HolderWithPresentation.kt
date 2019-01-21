package ru.kcoder.weatherhelper.data.entity.weather

import androidx.room.Embedded
import androidx.room.Relation

data class HolderWithPresentation(
    @Embedded
    val weatherHolder: WeatherHolder,

    @Relation(parentColumn = "id", entityColumn = "holderId")
    val list: List<WeatherPresentation>
)

fun HolderWithPresentation.mapToPresentation(): WeatherHolder {

    val tmpHours = list.filter { it.status == WeatherPresentation.HOURS }

    weatherHolder.hours.addAll(tmpHours)
    weatherHolder.timeNames = tmpHours.map { it.time }
    weatherHolder.days.addAll(list.filter { it.status == WeatherPresentation.DAYS })
    weatherHolder.nights.addAll(list.filter { it.status == WeatherPresentation.NIGHTS })

    return weatherHolder
}
