package ru.kcoder.weatherhelper.data.entity.weather

import androidx.room.PrimaryKey
import ru.kcoder.weatherhelper.data.network.common.ApiResponse

class WeatherHolder: ApiResponse {

    override var cod: Int? = null

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0


}