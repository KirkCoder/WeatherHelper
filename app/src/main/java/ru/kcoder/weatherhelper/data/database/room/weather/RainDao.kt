package ru.kcoder.weatherhelper.data.database.room.weather

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Rain

@Dao
interface RainDao: BaseDao<Rain> {

    @Query("SELECT * FROM rain WHERE weatherHolderId = :id")
    fun getRainByWeatherHolderId(id: Long): Rain?
}