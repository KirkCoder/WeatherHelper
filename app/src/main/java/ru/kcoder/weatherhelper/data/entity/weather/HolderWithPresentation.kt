package ru.kcoder.weatherhelper.data.entity.weather

import androidx.room.Embedded
import androidx.room.Relation

class HolderWithPresentation{
    @Embedded
    var weatherHolder: WeatherHolder = WeatherHolder()

    @Relation(parentColumn = "id", entityColumn = "holderId")
    var list: List<WeatherPresentation> = emptyList()
}

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
