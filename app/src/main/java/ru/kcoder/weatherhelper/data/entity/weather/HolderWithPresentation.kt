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

    for (item in list) {
        when(item.status){
            WeatherPresentation.HOURS -> {
                weatherHolder.hours.add(item)
                weatherHolder.timeNames.add(item.time)
            }
            WeatherPresentation.DAYS -> weatherHolder.days.add(item)
            WeatherPresentation.NIGHTS -> weatherHolder.nights.add(item)
        }
    }

    return weatherHolder
}
