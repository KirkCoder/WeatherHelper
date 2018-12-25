package ru.kcoder.weatherhelper.data.entity.weather

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import ru.kcoder.weatherhelper.data.network.common.ApiResponse

@Entity(
    tableName = "weather_holder"
)
class WeatherHolder: ApiResponse {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var position: Int = 0
    var lat: Double = 0.0
    var lon: Double = 0.0
    var name: String = ""


    override var cod: String? = null
    var message: Double? = null
    var cnt: Double? = null

    @Ignore
    @SerializedName("list")
    var data: List<Data>? = null
    @Ignore
    var city: City? = null

}