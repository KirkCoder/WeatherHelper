package ru.kcoder.weatherhelper.data.database.room.weather

import androidx.room.Dao
import androidx.room.Query
import ru.kcoder.weatherhelper.data.database.room.BaseDao
import ru.kcoder.weatherhelper.data.entity.weather.Rain

@Dao
interface RainDao: BaseDao<Rain> {

    @Query("SELECT * FROM rain WHERE weatherHolderId = :id")
    fun getRainByWeatherHolderId(id: Long): Rain?

    @Query("DELETE FROM rain WHERE weatherHolderId = :id")
    fun deleteAllByWeatherHolderId(id: Long)
}