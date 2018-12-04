package ru.kcoder.weatherhelper.data.entity.weather

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "list"
)
class Data {

    var weatherHolderId: Long = 0L
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var dt: Long? = null
    @SerializedName("dt_txt")
    var dtTxt: String? = null

    @Ignore
    var weather: List<Weather>? = null
    @Ignore
    var clouds: Clouds? = null
    @Ignore
    var sys: Sys? = null
    @Ignore
    var wind: Wind? = null
    @Ignore
    var main: Main? = null
    @Ignore
    var rain: Rain? = null

}